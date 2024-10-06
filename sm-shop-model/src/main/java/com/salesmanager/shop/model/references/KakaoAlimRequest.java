package com.salesmanager.shop.model.references;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class KakaoAlimRequest {
    String to;
    KakaoTemplateCode templateCode;
    Map<String, String> values;
    String ref;
}
