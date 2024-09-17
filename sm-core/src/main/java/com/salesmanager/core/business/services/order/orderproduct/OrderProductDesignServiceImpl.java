package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductDesignRepository;
import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class OrderProductDesignServiceImpl implements OrderProductDesignService {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderProductDesignServiceImpl.class);

    @Inject
    private OrderProductDesignRepository orderProductDesignRepository;

    @Override
    public OrderProductDesign getByOrderProductId(Long id) {
        return orderProductDesignRepository.getByOrderProductId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(OrderProductDesign orderProductDesign) {
        LOGGER.info("OrderProductDesign saved. OrderProductDesign:{}", orderProductDesign);
        orderProductDesignRepository.save(orderProductDesign);
        return true;
    }

}
