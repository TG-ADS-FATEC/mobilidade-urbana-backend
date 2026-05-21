package com.sptrans.mobilidade_urbana.gtfs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GTFSImporterRegistry {
	
	private final Map<String, GTFSImporter<?>> importers = new HashMap<>();
	
	public GTFSImporterRegistry(List<GTFSImporter<?>> importerList) {
		importerList.forEach(importer -> importers.put(importer.fileName(), importer));
	}
	
	public GTFSImporter<?> get(String fileName){
		return importers.get(fileName);
	}

}
