package com.salesmanager.core.model.shipping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShippingType {
	
	NATIONAL, INTERNATIONAL;


	public static List<ShippingType> convertStringsToShippingTypes(List<String> stringTypes) {
		if (stringTypes == null || stringTypes.isEmpty()) {
			return null;
		}
		return stringTypes.stream()
				.map(ShippingType::valueOf)
				.collect(Collectors.toList());
	}

	public static List<String> convertShippingTypesToStrings(List<ShippingType> shippingTypes) {
		if (shippingTypes == null || shippingTypes.isEmpty()) {
			return null;
		}
		return shippingTypes.stream()
				.map(ShippingType::name)
				.collect(Collectors.toList());
	}

	public static List<ShippingType> convertStringToShippingTypes(String types) {
		if (types == null || types.isEmpty()) {
			return null;
		}
		return Arrays.stream(types.split(","))
				.map(ShippingType::valueOf)
				.collect(Collectors.toList());
	}

	public static String convertShippingTypesToString(List<ShippingType> shippingTypes) {
		if (shippingTypes == null || shippingTypes.isEmpty()) {
			return null;
		}
		return shippingTypes.stream()
				.map(ShippingType::name)
				.collect(Collectors.joining(","));
	}

}
