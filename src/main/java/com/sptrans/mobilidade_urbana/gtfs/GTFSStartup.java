package com.sptrans.mobilidade_urbana.gtfs;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.services.GTFSImportService;

import jakarta.annotation.PostConstruct;

@Component
public class GTFSStartup {
	
	private final GTFSImportService importService;

	public GTFSStartup(GTFSImportService importService) {
		this.importService = importService;
	}
	
	@PostConstruct
	public void init() {
		try {
		importService.importZip("/data/sptrans_gtfs.zip");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println("Falha ao importar GTFS"+e.getMessage());
			}

	}
	

}
