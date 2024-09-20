package com.salesmanager.shop.store.controller.dataservice.facade;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.salesmanager.core.business.services.search.DataService;
import com.salesmanager.core.model.catalog.product.dataservice.*;

import com.salesmanager.shop.model.catalog.BIProductInfoRequestV2;
import com.salesmanager.shop.model.catalog.BIMemberInfoRequestV2;
import com.salesmanager.shop.model.catalog.BIBuyerInfoRequestV2;
import com.salesmanager.shop.model.catalog.BISaleInfoRequestV2;
import com.salesmanager.shop.model.catalog.BITrafficInfoRequestV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.salesmanager.core.model.reference.language.Language;

import java.math.BigDecimal;

@Service("dataServiceFacade")
public class DataServiceFacadeImpl implements DataServiceFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceFacadeImpl.class);

	@Inject
	private DataService dataService;

	@Override
	public Map<String, Integer> BIProductInfoV2(BIProductInfoRequestV2 biProductInfoRequestV2, Language language) {

		BIProductInfoRequest request = new BIProductInfoRequest();
		request.setLastNDays(biProductInfoRequestV2.getLastNDays());
		request.setStartDate(biProductInfoRequestV2.getBeginDate());
		request.setEndData(biProductInfoRequestV2.getEndData());

		BIProductInfoResult result = dataService.biproductinfo(request);

//		ValueList valueList = new ValueList();
//		List<String> tmpList = new ArrayList<>();
//		tmpList.add(result.getCategoryLevel1Count().toString());
//		tmpList.add(result.getCategoryLevel2Count().toString());
//		tmpList.add(result.getCategoryLevel3Count().toString());
//		tmpList.add(result.getLeafCategoryCount().toString());
//		tmpList.add(result.getProductNumForSale().toString());
//		tmpList.add(result.getProductSkuNumForSale().toString());
//		tmpList.add(result.getLowStockProductSkuNum().toString());
//		tmpList.add(result.getDownShelfProductSkuNum().toString());
//		valueList.setValues(tmpList);

		Map<String, Integer> result_map = new HashMap<>();
		result_map.put("category_level_1_count", result.getCategoryLevel1Count());
		result_map.put("category_level_2_count", result.getCategoryLevel2Count());
		result_map.put("category_level_3_count", result.getCategoryLevel3Count());
		result_map.put("leaf_category_count", result.getLeafCategoryCount());
		result_map.put("product_num_for_sale", result.getProductNumForSale());
		result_map.put("product_sku_num_for_sale", result.getProductSkuNumForSale());
		result_map.put("low_stock_product_sku_num", result.getLowStockProductSkuNum());
		result_map.put("down_shelf_product_sku_num", result.getDownShelfProductSkuNum());

		return result_map;
	}

	@Override
	public Map<String, Integer> BIMemberInfoV2(BIMemberInfoRequestV2 biMemberInfoRequestV2, Language language) {

		BIMemberInfoRequest request = new BIMemberInfoRequest();
		request.setLastNDays(biMemberInfoRequestV2.getLastNDays());
		request.setStartDate(biMemberInfoRequestV2.getBeginDate());
		request.setEndData(biMemberInfoRequestV2.getEndData());

		BIMemberInfoResult result = dataService.bimemberinfo(request);

		Map<String, Integer> result_map = new HashMap<>();
		result_map.put("all_members_num", result.getAllMembersNum());
		result_map.put("personal_members_num", result.getPersonalMembersNum());
		result_map.put("corp_members_num", result.getCorpMembersNum());
		result_map.put("corp_purchase_members_num", result.getCorpPurchaseMembersNum());
		result_map.put("corp_sale_members_num", result.getCorpSaleMembersNum());
		result_map.put("biz_partners_num", result.getBizPartnersNum());

		return result_map;
	}

	@Override
	public Map<String, Object> BIBuyerInfoV2(BIBuyerInfoRequestV2 biBuyerInfoRequestV2, Language language) {

		BIBuyerInfoRequest request = new BIBuyerInfoRequest();
		request.setLastNDays(biBuyerInfoRequestV2.getLastNDays());
		request.setBeginDate(biBuyerInfoRequestV2.getBeginData());
		request.setEndData(biBuyerInfoRequestV2.getEndData());
		request.setUserTag(biBuyerInfoRequestV2.getUserTag());

		BIBuyerInfoResult result = dataService.bibuyerinfo(request);

		Map<String, Object> result_map = new HashMap<>();
		result_map.put("purchase_amount", BigDecimal.valueOf(result.getPurchaseAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("member_count", result.getMemberCount());
		result_map.put("buyer_count", result.getBuyerCount());
		result_map.put("repurchase_buyer_count", result.getRePurchaseBuyerCount());
		result_map.put("repurchase_ratio", BigDecimal.valueOf(result.getRePurchaseRatio()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("average_repurchase_days", BigDecimal.valueOf(result.getAvgRePurchaseDays()).setScale(2, RoundingMode.HALF_UP).doubleValue());

		return result_map;
	}

	@Override
	public Map<String, Object> BISaleInfoV2(BISaleInfoRequestV2 biSaleInfoRequestV2, Language language) {

		BISaleInfoRequest request = new BISaleInfoRequest();
		request.setLastNDays(biSaleInfoRequestV2.getLastNDays());
		request.setBeginDate(biSaleInfoRequestV2.getBeginDate());
		request.setEndData(biSaleInfoRequestV2.getEndData());

		BISaleInfoResult result = dataService.bisaleinfo(request);

		Map<String, Object> result_map = new HashMap<>();
		result_map.put("total_amount", BigDecimal.valueOf(result.getTotalAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("product_price_amount", BigDecimal.valueOf(result.getProductPriceAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("additional_service_fee_amount", BigDecimal.valueOf(result.getAdditionalServiceFeeAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("shipping_fee_amount", BigDecimal.valueOf(result.getShippingFeeAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("handling_fee_amount", BigDecimal.valueOf(result.getHandlingFeeAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("product_quantity", result.getProductQuantity());
		result_map.put("paid_order_num", result.getPaidOrderNum());
		result_map.put("refund_amount", BigDecimal.valueOf(result.getRefundAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
		result_map.put("canel_order_num", result.getCancelOrderNum());
		result_map.put("return_goods_order_num", result.getReturnGoodsOrderNum());
		result_map.put("refund_and_return_ratio", BigDecimal.valueOf(result.getRefundAndReturnRatio()).setScale(2, RoundingMode.HALF_UP).doubleValue());

		return result_map;
	}

	@Override
	public Map<String, Object> BITrafficInfoV2(BITrafficInfoRequestV2 biTrafficInfoRequestV2, Language language) {

		BITrafficInfoRequest request = new BITrafficInfoRequest();
		request.setLastNDays(biTrafficInfoRequestV2.getLastNDays());
		request.setBeginDate(biTrafficInfoRequestV2.getBeginDate());
		request.setEndData(biTrafficInfoRequestV2.getEndData());

		BITrafficInfoResult result = dataService.bitrafficinfo(request);

		Map<String, Object> result_map = new HashMap<>();
		result_map.put("all_uv", result.getAllUV());
		result_map.put("new_uv", result.getNewUV());
		result_map.put("old_uv", result.getOldUV());
		result_map.put("pc_uv", result.getPcUV());
		result_map.put("mobile_uv", result.getMobileUV());

		return result_map;
	}
}
