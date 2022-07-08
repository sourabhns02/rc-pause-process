package com.recap.recapsystemmanager.dto;

public enum Constants {
	PROCESSING("2"),
	PAUSE_SUCCESS("SUCCESS"),
	PAUSE_FAILED("FAILED"),
	PAUSED_ALREADY("PAUSED_ALREADY");
	
	private String value;

	Constants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
