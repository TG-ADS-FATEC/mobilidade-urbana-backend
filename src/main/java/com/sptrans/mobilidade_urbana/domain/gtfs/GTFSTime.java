package com.sptrans.mobilidade_urbana.domain.gtfs;

import jakarta.persistence.Embeddable;

@Embeddable
public class GTFSTime {
	
	private int secondsFromMidnight;
	
	protected GTFSTime() {}
	
	public GTFSTime(int secondsFromMidnight) {
		this.secondsFromMidnight = secondsFromMidnight;
	}
	
	public static GTFSTime parse(String value) {
		String [] parts = value.split(":");
		
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		int seconds = Integer.parseInt(parts[2]);
		
		return new GTFSTime((hours*3600)+(minutes*60)+seconds);
	}
	
	public int getSecondsFromMidnight() {
		return secondsFromMidnight;
	}

}
