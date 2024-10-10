package com.salesmanager.shop.utils;

public class UrlGenerator {


    private static final String BASE_URL = "https://detail.1688.com/offer/%s.html";

    public static String generateUrl(Long productId) {
        if (productId ==null){
            return null;
        }
        return String.format(BASE_URL, productId);
    }


}
