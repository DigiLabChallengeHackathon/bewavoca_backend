package com.mosabulgyeo.bewavoca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageClearRequest {
	private String deviceId;
	private int region;
	private int stage;
}