package com.salesmanager.shop.model.references;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class KakaoAlim implements Serializable {
    String to;
    Map<String, String> values;
    String ref;
}
