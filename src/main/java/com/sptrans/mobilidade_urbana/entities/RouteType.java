package com.sptrans.mobilidade_urbana.entities;

public enum RouteType {
	TRAM(0), SUBWAY(1), RAIL(2), BUS(3), FERRY(4),
	CABLE_TRAM(5), AERIAL_LIFT(6), FUNICULAR(7), TROLLEYBUS(11),
	MONORAIL(12);
	
	private final int code;
	
	RouteType(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static RouteType fromCode(int code) {
		for(RouteType type : values()) {
			if(type.code == code) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("Tipo de rota de GTFS inválida"+code);
	}

}
