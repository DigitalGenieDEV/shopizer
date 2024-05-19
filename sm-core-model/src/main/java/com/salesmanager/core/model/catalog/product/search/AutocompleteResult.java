package com.salesmanager.core.model.catalog.product.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class AutocompleteResult {

    @JsonProperty("suggest_num")
    private Integer suggestNum;

    @JsonProperty("suggest")
    private Map<String, Integer> suggest = new HashMap<>();

    public Integer getSuggestNum() {
        return suggestNum;
    }

    public void setSuggestNum(Integer suggestNum) {
        this.suggestNum = suggestNum;
    }

    public Map<String, Integer> getSuggest() {
        return suggest;
    }

    public void setSuggest(Map<String, Integer> suggest) {
        this.suggest = suggest;
    }
}
