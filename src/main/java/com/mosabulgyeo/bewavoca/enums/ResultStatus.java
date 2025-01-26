package com.mosabulgyeo.bewavoca.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResultStatus {
	GREAT_SUCCESS("great_success"),
	SUCCESS("success"),
	FAIL("fail");

	private final String value;

	ResultStatus(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static ResultStatus fromValue(String value) {
		for (ResultStatus status : ResultStatus.values()) {
			if (status.value.equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid ResultStatus: " + value);
	}
}