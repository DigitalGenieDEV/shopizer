package com.salesmanager.core.model.shipping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TransportationMethod {
	EXPRESS ,FREIGHT , EXPRESS_SERVICE , DIRECT_DELIVERY;


	public static List<TransportationMethod> convertStringsToTransportationMethods(List<String> stringMethods) {
		if (stringMethods == null || stringMethods.isEmpty()) {
			return null;
		}
		return stringMethods.stream()
				.map(TransportationMethod::valueOf)
				.collect(Collectors.toList());
	}

	public static List<String> convertTransportationMethodsToStrings(List<TransportationMethod> methods) {
		if (methods == null || methods.isEmpty()) {
			return null;
		}
		return methods.stream()
				.map(TransportationMethod::name)
				.collect(Collectors.toList());
	}

	public static List<TransportationMethod> convertStringToTransportationMethods(String methods) {
		if (methods == null || methods.isEmpty()) {
			return null;
		}
		return Arrays.stream(methods.split(","))
				.map(TransportationMethod::valueOf)
				.collect(Collectors.toList());
	}

	public static String convertTransportationMethodsToString(List<String> methods) {
		if (methods == null || methods.isEmpty()) {
			return null;
		}
		return methods.stream()
				.collect(Collectors.joining(","));
	}
}
