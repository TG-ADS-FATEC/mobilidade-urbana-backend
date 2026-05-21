package com.sptrans.mobilidade_urbana.gtfs;

public class KeyNormalizer {
	
	public static String normalize(String key) {
		
		String [] parts = key.split("_");
		
		StringBuilder sb = new StringBuilder(parts[0]);
		
		for(int i = 1; i < parts.length; i++) {
			sb.append(parts[i].substring(0, 1).toUpperCase());
			sb.append(parts[i].substring(1));
		}
		
		return sb.toString();
	}

}
