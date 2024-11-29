package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {
	@NotBlank(message = "Device ID is required.")
	private String deviceId;
}