package com.sptrans.mobilidade_urbana.entities;

public enum DirectionId {
	OUTBOUND(0), INBOUND(1);
	
	private final int value;
	
	DirectionId(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static DirectionId from(int value) {
		return value == 0 ? OUTBOUND: INBOUND;
	}

}
