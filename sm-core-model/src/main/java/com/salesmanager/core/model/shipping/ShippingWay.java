package com.salesmanager.core.model.shipping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShippingWay {
	
	CONSIGNMENT, DIRECT;


	public static List<ShippingWay> convertStringsToShippingWays(List<String> stringTypes) {
		if (stringTypes == null || stringTypes.isEmpty()) {
			return null;
		}
		return stringTypes.stream()
				.map(ShippingWay::valueOf)
				.collect(Collectors.toList());
	}

	public static List<String> convertShippingWaysToStrings(List<ShippingWay> shippingTypes) {
		if (shippingTypes == null || shippingTypes.isEmpty()) {
			return null;
		}
		return shippingTypes.stream()
				.map(ShippingWay::name)
				.collect(Collectors.toList());
	}

	public static List<ShippingWay> convertStringToShippingWays(String types) {
		if (types == null || types.isEmpty()) {
			return null;
		}
		return Arrays.stream(types.split(","))
				.map(ShippingWay::valueOf)
				.collect(Collectors.toList());
	}

	public static String convertShippingWaysToString(List<String> shippingTypes) {
		if (shippingTypes == null || shippingTypes.isEmpty()) {
			return null;
		}
		return shippingTypes.stream()
				.collect(Collectors.joining(","));
	}

}
