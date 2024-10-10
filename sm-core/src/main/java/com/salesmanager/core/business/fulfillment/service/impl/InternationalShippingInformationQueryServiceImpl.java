package com.salesmanager.core.business.fulfillment.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import com.kuaidi100.sdk.api.AutoNum;
import com.kuaidi100.sdk.api.QueryTrack;
import com.kuaidi100.sdk.request.AutoNumReq;
import com.kuaidi100.sdk.request.QueryTrackParam;
import com.kuaidi100.sdk.request.QueryTrackReq;
import com.kuaidi100.sdk.response.QueryTrackData;
import com.kuaidi100.sdk.response.QueryTrackResp;
import com.kuaidi100.sdk.utils.SignUtils;
import com.salesmanager.core.business.fulfillment.service.InternationalShippingInformationQueryService;
import com.salesmanager.core.model.fulfillment.outer.LogisticsTrackInformation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class InternationalShippingInformationQueryServiceImpl implements InternationalShippingInformationQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternationalShippingInformationQueryServiceImpl.class);

    private static final String KUAIDI_100_KEY = "TWCkOxoU3534";
    private static final String KUAIDI_100_CUSTOM = "525E26BE4D97BB76DC91FA340365BC96";

    private final AutoNum autoNum = new AutoNum();
    private final QueryTrack queryTrack = new QueryTrack();

    private final Cache<String, List<LogisticsTrackInformation>> cache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    @Override
    public List<LogisticsTrackInformation> queryByLogisticsNumber(String logisticsNumber, String phone) {
        AutoNumReq request = new AutoNumReq();
        request.setKey(KUAIDI_100_KEY);
        request.setNum(logisticsNumber);
        String apiUrl = autoNum.getApiUrl(request);
        request.setUrl(apiUrl);

        String firstComByNum;
        try {
            firstComByNum = autoNum.getFirstComByNum(request);
        } catch (Exception e) {
            LOGGER.error("Error while autoNum get first company code", e);
            throw new RuntimeException(e);
        }
        return queryByLogisticsNumber(firstComByNum, logisticsNumber, phone);
    }

    @Override
    public List<LogisticsTrackInformation> queryByLogisticsNumber(String logisticsCompany, String logisticsNumber, String phone) {

        String cacheKey = logisticsCompany + ":" + logisticsNumber + ":" + phone;
        List<LogisticsTrackInformation> result = cache.getIfPresent(cacheKey);
        if (result != null) {
            return result;
        }

        QueryTrackReq queryTrackReq = new QueryTrackReq();

        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(logisticsCompany);
        queryTrackParam.setNum(logisticsNumber);
        queryTrackParam.setPhone(phone);
        String param = new Gson().toJson(queryTrackParam);

        queryTrackReq.setParam(param);
        queryTrackReq.setCustomer(KUAIDI_100_CUSTOM);
        queryTrackReq.setSign(SignUtils.querySign(param, KUAIDI_100_KEY, KUAIDI_100_CUSTOM));
        queryTrackReq.setUrl(queryTrack.getApiUrl(queryTrackReq));

        QueryTrackResp queryTrackResp;
        try {
            queryTrackResp = queryTrack.queryTrack(queryTrackReq);
        } catch (Exception e) {
            LOGGER.error("Error while queryTrack", e);
            throw new RuntimeException(e);
        }

        if (queryTrackResp != null && CollectionUtils.isNotEmpty(queryTrackResp.getData())) {
            List<QueryTrackData> data = queryTrackResp.getData();
            List<LogisticsTrackInformation> list = data.stream()
                    .map(this::mapQueryTrackData2LogisticsTrackInformation)
                    .collect(Collectors.toList());
            cache.put(cacheKey, list);
            return list;
        }

        return null;
    }

    private LogisticsTrackInformation mapQueryTrackData2LogisticsTrackInformation(QueryTrackData queryTrackData) {
        LogisticsTrackInformation logisticsTrackInformation = new LogisticsTrackInformation();
        logisticsTrackInformation.setTime(queryTrackData.getTime());
        logisticsTrackInformation.setContext(queryTrackData.getContext());
        logisticsTrackInformation.setFormatTime(queryTrackData.getFtime());
        logisticsTrackInformation.setAreaCode(queryTrackData.getAreaCode());
        logisticsTrackInformation.setAreaName(queryTrackData.getAreaName());
        logisticsTrackInformation.setStatus(queryTrackData.getStatus());
        logisticsTrackInformation.setStatusCode(queryTrackData.getStatusCode());

        return logisticsTrackInformation;
    }

}
