package com.recap.recapsystemmanager.dto;

public enum ServerStatusConstants {
	SERVER_PAUSE(2),
	SERVER_START(1);

	private int value;

	ServerStatusConstants(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
