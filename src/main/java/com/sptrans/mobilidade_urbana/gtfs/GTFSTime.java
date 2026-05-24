package com.sptrans.mobilidade_urbana.gtfs;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;

@Embeddable
public class GTFSTime implements Comparable<GTFSTime> {
	
	private int secondsFromMidnight;
	
	protected GTFSTime() {}
	
	public GTFSTime(int secondsFromMidnight) {
		this.secondsFromMidnight = secondsFromMidnight;
	}
	
	public static GTFSTime parse(String value) {
		
		if(value == null || value.isBlank()) {
			return null;
		}
		
		String [] parts = value.split(":");
		
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		int seconds = Integer.parseInt(parts[2]);
		
		return new GTFSTime((hours*3600)+(minutes*60)+seconds);
	}
	
	public int getSecondsFromMidnight() {
		return secondsFromMidnight;
	}
	
	@JsonValue
	@Override
	public String toString() {
		
		int total = secondsFromMidnight;
		
		int hours = total/3600;
		int minutes = (total%3600) / 60;
		int seconds = total % 60;
		
		return String.format(
				"%02d:%02d:%02d",
				hours,
				minutes,
				seconds);
	}
	
	@Override
	public int compareTo(GTFSTime other) {
		return Integer.compare(this.secondsFromMidnight, other.secondsFromMidnight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(secondsFromMidnight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GTFSTime other = (GTFSTime) obj;
		return secondsFromMidnight == other.secondsFromMidnight;
	}
	
	

}
