package com.salesmanager.shop.store.controller.purchaseorder.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.alibaba.param.*;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.alibaba.payment.AlibabaPaymentService;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.alibaba.product.impl.AlibabaProductServiceImpl;
import com.salesmanager.core.business.services.alibaba.trade.AlibabaTradeOrderService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.business.services.purchaseorder.PurchaseOrderService;
import com.salesmanager.core.business.services.purchaseorder.supplier.PurchaseSupplierOrderService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.crossorder.*;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.purchaseorder.*;
import com.salesmanager.shop.mapper.purchasorder.ReadablePurchaseOrderMapper;
import com.salesmanager.shop.model.purchaseorder.PersistablePurchaseOrder;
import com.salesmanager.shop.model.purchaseorder.PurchaseOrderProductInfo;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrder;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrderList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderFacadeImpl implements PurchaseOrderFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderFacadeImpl.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseSupplierOrderService purchaseSupplierOrderService;

    @Autowired
    private AlibabaTradeOrderService alibabaTradeOrderService;

    @Autowired
    private AlibabaPaymentService alibabaPaymentService;

    @Autowired
    private ReadablePurchaseOrderMapper readablePurchaseOrderMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ProductVariantService productVariantService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReadablePurchaseOrder createPurchaseOrder(PersistablePurchaseOrder persistablePurchaseOrder) throws ServiceException {
        final PurchaseOrder purchaseOrder = new PurchaseOrder();

        Set<PurchaseSupplierOrder> purchaseSupplierOrders = new HashSet<>();
        groupBySellerOpenId(persistablePurchaseOrder)
                .entrySet()
                .stream()
                .forEach(stringListEntry -> {
                    String sellerOpenId = stringListEntry.getKey();
                    List<PurchaseOrderProductInfo> purchaseOrderProductInfos = stringListEntry.getValue();

                    PurchaseSupplierOrder purchaseSupplierOrder = createPurchaseSupplierOrder(sellerOpenId, purchaseOrderProductInfos);
                    purchaseSupplierOrder.setPurchaseOrder(purchaseOrder);
                    purchaseSupplierOrders.add(purchaseSupplierOrder);
                });

        purchaseOrder.setPurchaseSupplierOrders(purchaseSupplierOrders);
        PurchaseOrder newPurchaseOrder = purchaseOrderService.saveAndUpdate(purchaseOrder);
        ReadablePurchaseOrder readablePurchaseOrder =  readablePurchaseOrderMapper.convert(newPurchaseOrder, null, null);
        return readablePurchaseOrder;
    }

    @Override
    public ReadablePurchaseOrder confirmPurchaseOrder(Long purchaseOrderId) throws ServiceException {
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(purchaseOrderId);

        if (purchaseOrder == null) {
            throw new ServiceException("purchase order [" + purchaseOrderId + "] not found");
        }

        purchaseOrder.getPurchaseSupplierOrders().forEach(purchaseSupplierOrder -> {
            try {
                // 创建批量跨境供货单
                purchaseSupplierOrder.setCrossOrders(createSupplierCrossOrders(purchaseSupplierOrder));

                // 创建支付链接
                purchaseSupplierOrder.setPayUrl(getPayUrl(purchaseSupplierOrder));

                purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);
            } catch (ServiceException e) {
                LOGGER.error("create purchase supplier cross orders exception", e);
            }
        });

        return readablePurchaseOrderMapper.convert(purchaseOrder, null, null);
    }

    @Override
    public ReadablePurchaseOrderList getReadablePurchaseOrders(int page, int count) {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageRequest = PageRequest.of(page, count, sort);

        Page<PurchaseOrder> returnList = purchaseOrderService.getPurchaseOrders(pageRequest);

        ReadablePurchaseOrderList readablePurchaseOrderList = new ReadablePurchaseOrderList();

        List<ReadablePurchaseOrder> content = returnList.getContent().stream().map(purchaseOrder -> readablePurchaseOrderMapper.convert(purchaseOrder, null, null))
                .collect(Collectors.toList());

        readablePurchaseOrderList.setNumber(page);
        readablePurchaseOrderList.setRecordsTotal(returnList.getTotalElements());
        readablePurchaseOrderList.setPurchaseOrders(content);

        return readablePurchaseOrderList;
    }

    private String getPayUrl(PurchaseSupplierOrder purchaseSupplierOrder) {
        List<Long> orderIdList = purchaseSupplierOrder.getCrossOrders().stream().map(SupplierCrossOrder::getOrderIdStr).map(Long::valueOf).collect(Collectors.toList());
        AlibabaAlipayUrlGetParam getParam = new AlibabaAlipayUrlGetParam();
        getParam.setOrderIdList(getOrderIds(orderIdList));
        AlibabaAlipayUrlGetResult alipayUrlGetResult = alibabaPaymentService.getAlipayUrl(getParam);

        return alipayUrlGetResult.getPayUrl();
    }

    private long[] getOrderIds(List<Long> orderIdList) {
        Long[] ids = orderIdList.toArray(new Long[0]);
        long[] orderIds = new long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            orderIds[i] = ids[i];
        }

        return orderIds;
    }


    public Set<SupplierCrossOrder> createSupplierCrossOrders(PurchaseSupplierOrder purchaseSupplierOrder) throws ServiceException {
        AlibabaTradeCreateCrossOrderParam param = getAlibabaTradeCreateCrossOrderParam(purchaseSupplierOrder);

        AlibabaTradeCrossResult result = alibabaTradeOrderService.createCrossOrder(param);

        List<Long> orderIds = new ArrayList<>();

        if (!StringUtils.isEmpty(result.getOrderId())) {
            orderIds.add(Long.valueOf(result.getOrderId()));
        } else {
            orderIds.addAll(Arrays.stream(result.getOrderList())
                    .map(AlibabaTradeResultBizSimpleOrder::getOrderId)
                    .map(Long::valueOf)
                    .collect(Collectors.toList()));
        }

        Set<SupplierCrossOrder> supplierCrossOrders = orderIds.stream()
                .map(orderId -> getBuyerViewCrossOrder(purchaseSupplierOrder, orderId))
                .collect(Collectors.toSet());

        return supplierCrossOrders;
    }

    private SupplierCrossOrder getBuyerViewCrossOrder(PurchaseSupplierOrder purchaseSupplierOrder, Long orderId) {
        AlibabaTradeGetBuyerViewParam buyerViewParam = new AlibabaTradeGetBuyerViewParam();
        buyerViewParam.setOrderId(orderId);
        buyerViewParam.setWebSite("1688");

        AlibabaOpenplatformTradeModelTradeInfo tradeInfo = alibabaTradeOrderService.getCrossOrderBuyerView(buyerViewParam);

        SupplierCrossOrder supplierCrossOrder = new SupplierCrossOrder();
        supplierCrossOrder.setPsoOrder(purchaseSupplierOrder);

        AlibabaOpenplatformTradeModelOrderBaseInfo baseInfo = tradeInfo.getBaseInfo();

        supplierCrossOrder.setOrderIdStr(baseInfo.getIdOfStr());
        supplierCrossOrder.setStatus(baseInfo.getStatus());
        supplierCrossOrder.setTotalAmount(baseInfo.getTotalAmount());
        supplierCrossOrder.setDiscount(baseInfo.getDiscount());
        supplierCrossOrder.setRefund(baseInfo.getRefund());
        supplierCrossOrder.setShippingFee(baseInfo.getShippingFee());
        supplierCrossOrder.setSellerId(baseInfo.getSellerID());
        supplierCrossOrder.setPayTime(baseInfo.getPayTime());

        BuyerContact buyerContact = new BuyerContact();
        buyerContact.setPhone(baseInfo.getBuyerContact().getPhone());
        buyerContact.setEmail(baseInfo.getBuyerContact().getEmail());
        buyerContact.setName(baseInfo.getBuyerContact().getName());
        buyerContact.setMobile(baseInfo.getBuyerContact().getMobile());
        buyerContact.setCompany(baseInfo.getBuyerContact().getCompanyName());

        supplierCrossOrder.setBuyerContact(buyerContact);

        SellerContact sellerContact = new SellerContact();
        sellerContact.setPhone(baseInfo.getSellerContact().getPhone());
        sellerContact.setEmail(baseInfo.getSellerContact().getEmail());
        sellerContact.setName(baseInfo.getSellerContact().getName());
        sellerContact.setMobile(baseInfo.getSellerContact().getMobile());
        sellerContact.setCompanyName(baseInfo.getSellerContact().getCompanyName());

        supplierCrossOrder.setSellerContact(sellerContact);

        ReceiverInfo receiverInfo = new ReceiverInfo();

        receiverInfo.setFullName(baseInfo.getReceiverInfo().getToFullName());
        receiverInfo.setDivisionCode(baseInfo.getReceiverInfo().getToDivisionCode());
        receiverInfo.setPostCode(baseInfo.getReceiverInfo().getToPost());
        receiverInfo.setTownCode(baseInfo.getReceiverInfo().getToTownCode());
        receiverInfo.setArea(baseInfo.getReceiverInfo().getToArea());
        receiverInfo.setMobile(baseInfo.getReceiverInfo().getToMobile());
        receiverInfo.setPhone(baseInfo.getReceiverInfo().getToPhone());

        supplierCrossOrder.setReceiverInfo(receiverInfo);

        supplierCrossOrder.setCreatedTime(new Date());
        supplierCrossOrder.setUpdatedTime(new Date());
        supplierCrossOrder.setSellerUserId(baseInfo.getSellerUserId());
        supplierCrossOrder.setSellerLoginId(baseInfo.getSellerLoginId());
        supplierCrossOrder.setBuyerUserId(baseInfo.getBuyerUserId());
        supplierCrossOrder.setBuyerLoginId(baseInfo.getBuyerLoginId());
        supplierCrossOrder.setTradeTypeCode(baseInfo.getTradeTypeCode());

        AlibabaOpenplatformTradeModelProductItemInfo[] productItemInfos = tradeInfo.getProductItems();

        supplierCrossOrder.setProducts(Arrays.stream(productItemInfos)
                .map(itemInfo -> this.convertToSupplierCrossOrderProduct(purchaseSupplierOrder, supplierCrossOrder, itemInfo))
                .collect(Collectors.toSet()));

        return supplierCrossOrder;
    }

    private SupplierCrossOrderProduct convertToSupplierCrossOrderProduct(PurchaseSupplierOrder purchaseSupplierOrder, SupplierCrossOrder supplierCrossOrder, AlibabaOpenplatformTradeModelProductItemInfo itemInfo) {
        SupplierCrossOrderProduct supplierCrossOrderProduct = new SupplierCrossOrderProduct();

        supplierCrossOrderProduct.setSupplierCrossOrder(supplierCrossOrder);
        supplierCrossOrderProduct.setName(itemInfo.getName());
        supplierCrossOrderProduct.setProductId(itemInfo.getProductID());
        supplierCrossOrderProduct.setProductSnapshotUrl(itemInfo.getProductSnapshotUrl());
        supplierCrossOrderProduct.setItemAmount(itemInfo.getItemAmount());
        supplierCrossOrderProduct.setQuantity(itemInfo.getQuantity().intValue());
        supplierCrossOrderProduct.setRefund(itemInfo.getRefund());
        supplierCrossOrderProduct.setSkuId(itemInfo.getSkuID());
        supplierCrossOrderProduct.setStatus(itemInfo.getStatus());
        supplierCrossOrderProduct.setSubItemId(itemInfo.getSubItemID());
        supplierCrossOrderProduct.setType(itemInfo.getType());
        supplierCrossOrderProduct.setUnit(itemInfo.getUnit());
        try {
            supplierCrossOrderProduct.setSkuInfos(objectMapper.writeValueAsString(itemInfo.getSkuInfos()));
        } catch (Exception e) {
        }

        supplierCrossOrderProduct.setSpecId(itemInfo.getSpecId());
        supplierCrossOrderProduct.setStatusStr(itemInfo.getStatusStr());
        supplierCrossOrderProduct.setRefundStatus(itemInfo.getRefundStatus());
        supplierCrossOrderProduct.setLogisticsStatus(itemInfo.getLogisticsStatus());
        supplierCrossOrderProduct.setGmtCreated(itemInfo.getGmtCreate());
        supplierCrossOrderProduct.setGmtModified(itemInfo.getGmtModified());
        supplierCrossOrderProduct.setGmtPayExpireTime(itemInfo.getGmtPayExpireTime());
        supplierCrossOrderProduct.setRefundId(itemInfo.getRefundId());
        supplierCrossOrderProduct.setSubItemIdStr(itemInfo.getSubItemIDString());
        PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = getPurchaseSupplierOrderProduct(purchaseSupplierOrder, itemInfo);
        supplierCrossOrderProduct.setPsoOrderProduct(purchaseSupplierOrderProduct);

//        purchaseSupplierOrderProduct.setCrossOrderProduct(supplierCrossOrderProduct);

        return supplierCrossOrderProduct;
    }

    private PurchaseSupplierOrderProduct getPurchaseSupplierOrderProduct(PurchaseSupplierOrder purchaseSupplierOrder, AlibabaOpenplatformTradeModelProductItemInfo itemInfo) {
        return
                purchaseSupplierOrder.getOrderProducts().stream()
                        .filter(purchaseSupplierOrderProduct -> purchaseSupplierOrderProduct.getSpecId().equals(String.valueOf(itemInfo.getSpecId())))
                        .findFirst().orElse(null);
    }


    private AlibabaTradeCreateCrossOrderParam getAlibabaTradeCreateCrossOrderParam(PurchaseSupplierOrder purchaseSupplierOrder) throws ServiceException {
        AlibabaTradeCreateCrossOrderParam param = new AlibabaTradeCreateCrossOrderParam();

        param.setFlow("general");
        param.setMessage("测试留言");
        param.setIsvBizType("cross");

        AlibabaTradeFastAddress address = new AlibabaTradeFastAddress();
        address.setFullName("Harry");
        address.setMobile("18506830971");
        address.setPostCode("310000");
        address.setCityText("杭州市");
        address.setProvinceText("浙江省");
        address.setAreaText("余杭区");
        address.setAddress("余杭塘路与景腾路交叉口西100米精诚电力科技园1号楼405");
        param.setAddressParam(address);

        List<AlibabaTradeFastCargo> cargoList = new ArrayList<>();

        for (PurchaseSupplierOrderProduct purchaseSupplierOrderProduct : purchaseSupplierOrder.getOrderProducts()) {
            Product product = productService.getBySku(purchaseSupplierOrderProduct.getSku());
            if (product == null) {
                throw new ServiceException("product get by sku [" + purchaseSupplierOrderProduct.getSku() + "] not found");
            }

            ProductVariant productVariant = getProductVariantBySku(product, purchaseSupplierOrderProduct.getSku());

            if (productVariant == null) {
                throw new ServiceException("product get by sku [" + purchaseSupplierOrderProduct.getSku() + "] not found");
            }

            AlibabaTradeFastCargo cargo = new AlibabaTradeFastCargo();
            cargo.setOfferId(Long.valueOf(product.getOutProductId()));
            cargo.setSpecId(productVariant.getSpecId());
            cargo.setQuantity(Double.valueOf(purchaseSupplierOrderProduct.getQuantity()));

            cargoList.add(cargo);
        }

        param.setCargoParamList(cargoList.toArray(new AlibabaTradeFastCargo[cargoList.size()]));

        param.setTradeType("assureTrade");
        param.setAnonymousBuyer(true);
        param.setFenxiaoChannel("kuajing");
        param.setInventoryMode("JIT");
        param.setOutOrderId(purchaseSupplierOrder.getId().toString());
        param.setPickupService("n");
        param.setWarehouseCode("any");

        return param;
    }

    private ProductVariant getProductVariantBySku(Product product, String sku) {
        return product.getVariants().stream().filter(productVariant -> productVariant.getSku().equals(sku)).findFirst().orElse(null);
    }

    private PurchaseSupplierOrder createPurchaseSupplierOrder(String sellerOpenId, List<PurchaseOrderProductInfo> purchaseOrderProductInfos) {
        final PurchaseSupplierOrder purchaseSupplierOrder = new PurchaseSupplierOrder();
        purchaseSupplierOrder.setSupplierNo(sellerOpenId);
        purchaseSupplierOrder.setReceiverArea("余杭区");
        purchaseSupplierOrder.setReceiverCity("杭州市");
        purchaseSupplierOrder.setReceiverProvince("浙江省");
        purchaseSupplierOrder.setReceiverMobile("18506830971");
        purchaseSupplierOrder.setReceiverFullName("Harry");
        purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.NEW);
        purchaseSupplierOrder.setPayStatus(PurchaseSupplierOrderPayStatus.PENDING_PAY);

        purchaseSupplierOrder.setOrderProducts(
                purchaseOrderProductInfos.stream().map(purchaseOrderProductInfo -> this.createPurchaseSupplierOrderProduct(purchaseSupplierOrder, purchaseOrderProductInfo)).collect(Collectors.toSet())
        );

        return purchaseSupplierOrder;
    }


    private PurchaseSupplierOrderProduct createPurchaseSupplierOrderProduct(PurchaseSupplierOrder purchaseSupplierOrder, PurchaseOrderProductInfo purchaseOrderProductInfo) {
        PurchaseSupplierOrderProduct product = new PurchaseSupplierOrderProduct();

        ProductDescription productDescription = purchaseOrderProductInfo.getProduct().getProductDescription();
        ProductImage productImage = purchaseOrderProductInfo.getProduct().getProductImage();

        ProductVariant productVariant = getProductVariantBySku(purchaseOrderProductInfo.getProduct(), purchaseOrderProductInfo.getOrderProduct().getSku());

        product.setProductName(productDescription.getName());
        product.setProductImage(productImage.getProductImage());
        product.setQuantity(purchaseOrderProductInfo.getOrderProduct().getProductQuantity());
        product.setProductId(purchaseOrderProductInfo.getProduct().getId());
//        product.setAmount(purchaseOrderProductInfo.getOrderProduct().getOneTimeCharge().multiply(new BigDecimal(purchaseOrderProductInfo.getOrderProduct().getProductQuantity())));
//        product.setLogisticsStatus("0");
        product.setShip(false);
        product.setSku(purchaseOrderProductInfo.getOrderProduct().getSku());
        product.setSpecId(productVariant.getSpecId());

        product.setStatus(PurchaseSupplierOrderProductStatus.PENDING);
        product.setPsoOrder(purchaseSupplierOrder);
        product.setOrderProduct(purchaseOrderProductInfo.getOrderProduct());

        return product;
    }

    public Map<String, List<PurchaseOrderProductInfo>> groupBySellerOpenId(PersistablePurchaseOrder persistablePurchaseOrder) throws ServiceException {
        List<OrderProduct> orderProducts =
                Arrays.stream(persistablePurchaseOrder.getOrderProducts()).map(persistableOrderProduct -> orderProductService.getOrderProduct(persistableOrderProduct.getId()))
                        .collect(Collectors.toList());

        List<PurchaseOrderProductInfo> purchaseOrderProductInfos = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            Product product = productService.getBySku(orderProduct.getSku());

            PurchaseOrderProductInfo purchaseOrderProductInfo = new PurchaseOrderProductInfo();
            purchaseOrderProductInfo.setOrderProduct(orderProduct);
            purchaseOrderProductInfo.setProduct(product);

            purchaseOrderProductInfos.add(purchaseOrderProductInfo);
        }

        return purchaseOrderProductInfos.stream()
                .collect(Collectors.groupingBy(purchaseOrderProductInfo -> purchaseOrderProductInfo.getProduct().getSellerOpenId()));
    }




}
