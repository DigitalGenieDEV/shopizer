package com.salesmanager.core.business.services.catalog.pricing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.repositories.exchangeRate.ExchangeRateRepository;
import com.salesmanager.core.model.catalog.product.ExchangeRate;
import com.salesmanager.core.model.catalog.product.ExchangeRatePOJO;
import com.salesmanager.core.model.catalog.product.price.PriceRange;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.utils.ProductPriceUtils;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.currency.Currency;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains all the logic required to calculate product price
 * @author Carl Samson
 *
 */
@Service("pricingService")
public class PricingServiceImpl implements PricingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PricingServiceImpl.class);

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Inject
	private ProductPriceUtils priceUtil;
	
	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(Product product, Boolean isShowProductPriceCurrencyCode) throws ServiceException {
		return priceUtil.getFinalPrice(product, isShowProductPriceCurrencyCode);
	}

	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPriceByCurrencyCode(Product product, String currencyCode) throws ServiceException {
		FinalPrice finalPrice = priceUtil.getFinalPrice(product, false);

		MerchantStore merchantStore = product.getMerchantStore();
		java.util.Currency currency = Constants.DEFAULT_CURRENCY;
		Locale locale = Constants.DEFAULT_LOCALE;

		try {
			currency = merchantStore.getCurrency().getCurrency();
			locale = new Locale(merchantStore.getDefaultLanguage().getCode(), merchantStore.getCountry().getIsoCode());
		} catch (Exception e) {
			LOGGER.error("Cannot create currency or locale instance for store " + merchantStore.getCode());
		}

		if (StringUtils.isNotEmpty(currencyCode) && !currency.getCurrencyCode().equals(currencyCode)){
			ExchangeRatePOJO exchangeRate = exchangeRateRepository.findExchangeRate(currency.getCurrencyCode(), currencyCode);
			BigDecimal rate = exchangeRate.getRate();
			finalPrice.setPriceRanges(finalPrice.getPriceRanges().stream().map(priceRange -> {
				PriceRange result = priceRange;
				String price = priceRange.getPrice();
				result.setPrice(new BigDecimal(price).multiply(rate).toString());
				return result;
			}).collect(Collectors.toList()));
			finalPrice.setFinalPrice(rate.multiply(finalPrice.getFinalPrice()));
			finalPrice.setOriginalPrice(rate.multiply(finalPrice.getOriginalPrice()));
			finalPrice.setDiscountedPrice(rate.multiply(finalPrice.getDiscountedPrice()));
			return finalPrice;
		}
		return finalPrice;
	}

	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(Product product, Customer customer) throws ServiceException {
		/** TODO add rules for price calculation **/
		return priceUtil.getFinalPrice(product, false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes) throws ServiceException {
		return priceUtil.getFinalPrice(product, attributes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes, Customer customer) throws ServiceException {
		/** TODO add rules for price calculation **/
		return priceUtil.getFinalPrice(product, attributes);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculatePriceQuantity(BigDecimal price, int quantity) {
		return price.multiply(new BigDecimal(quantity));
	}

	@Override
	@Transactional(readOnly = true)
	public String getDisplayAmount(BigDecimal amount, MerchantStore store) throws ServiceException {
		try {
			return priceUtil.getStoreFormatedAmountWithCurrency(store,amount);
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getDisplayAmount(BigDecimal amount, MerchantStore store, String currency) throws ServiceException {
		try {
			return priceUtil.getStoreFormatedAmountWithCurrency(store, amount, currency);
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getDisplayAmount(BigDecimal amount, Locale locale,
			Currency currency, MerchantStore store) throws ServiceException {
		try {
			return priceUtil.getFormatedAmountWithCurrency(locale, currency, amount);
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amunt " + amount.toString() + " using locale " + locale.toString() + " and currency " + currency.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getStringAmount(BigDecimal amount, MerchantStore store)
			throws ServiceException {
		try {
			return priceUtil.getAdminFormatedAmount(store, amount);
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getAmount(String amount) throws ServiceException {

		try {
			return priceUtil.getAmount(amount);
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount);
			throw new ServiceException(e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(ProductAvailability availability, Boolean isShowProductPriceCurrencyCode) throws ServiceException {

		return priceUtil.getFinalPrice(availability, isShowProductPriceCurrencyCode);
	}

	@Override
	@Transactional(readOnly = true)
	public FinalPrice calculateProductPrice(ProductVariant variant) throws ServiceException {
		return priceUtil.getFinalPrice(variant);
	}

	
}
