package com.sptrans.mobilidade_urbana.gtfs;

import java.util.Map;

public interface GTFSImporter<T> {
	
	String fileName();
	
	T mapRow(Map<String, String> row);

}
