package com.sptrans.mobilidade_urbana.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.ShapeRawDTO;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporterRegistry;
import com.sptrans.mobilidade_urbana.gtfs.RepositoryRegistry;
import com.sptrans.mobilidade_urbana.parsers.GTFSParser;

import jakarta.transaction.Transactional;

@Service
public class GTFSImportService {
	
	private final GTFSParser parser;
	private final GTFSImporterRegistry importerRegistry;
	private final RepositoryRegistry repositoryRegistry;
	private final ShapeService shapeService;
	private final ObjectMapper objectMapper;
	
	public GTFSImportService(GTFSParser parser, GTFSImporterRegistry importerRegistry, RepositoryRegistry repositoryRegistry,
			ShapeService shapeService, ObjectMapper objectMapper) {
		this.parser = parser;
		this.importerRegistry = importerRegistry;
		this.repositoryRegistry = repositoryRegistry;
		this.shapeService = shapeService;
		this.objectMapper = objectMapper;
	}
	
	@Transactional
	public void importZip(String zipPath) throws Exception{
		
		List<String> importOrder = List.of(
				"agency.txt",
				"calendar.txt",
				"routes.txt",
				"stops.txt",
				"shapes.txt",
				"trips.txt",
				"frequencies.txt",
				"stop_times.txt");
		
		try (ZipFile zipFile = new ZipFile(zipPath)){
			
			for(String fileName : importOrder) {
				
				ZipEntry entry = zipFile.getEntry(fileName);
				
				if(entry==null) {
					System.out.println("Arquivo não encontrado" + fileName);
					continue;
				}
				
				processEntry(zipFile, entry);
			}
			
			/*zipFile.stream().forEach(entry -> {
				
				try {
					processEntry(zipFile, entry);
				}
				catch(Exception e) {
					throw new RuntimeException("Erro processando entry: " + entry.getName(), e );
				}
			});*/
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	private void processEntry(ZipFile zipFile, ZipEntry entry) throws Exception {
		
		String fileName = entry.getName();
		
		System.out.println("PROCESSANDO FILE:"+fileName);
		
		if(fileName.equals("shapes.txt")) {
			processShapes(zipFile, entry);
			return;
		}
		
		GTFSImporter<?> importer = importerRegistry.get(fileName);
		
		if(importer == null) {
			System.out.println("Nenhum importer encontrado para: "+fileName);
			return;
		}
		
		
		System.out.println("[GTFS] Importando: "+fileName);
		
		try(InputStream inputStream = zipFile.getInputStream(entry)) {
			
			List<Object> batch = new ArrayList<>();
			
			parser.parse(inputStream, row -> {
				
				Object entity = importer.mapRow(row);
				
				if(entity==null) {
					return;
				}
				
				batch.add(entity);
				
				if(batch.size() >= 500) {
					saveBatch(batch);
					batch.clear();
				}
			}
			);
			
			if(!batch.isEmpty()) {
				saveBatch(batch);
			}
		}
		
	}
	
	private void processShapes(ZipFile zipFile, ZipEntry entry) throws Exception {
		
		try(InputStream inputStream = zipFile.getInputStream(entry)){
			
			List<ShapeRawDTO> rows = new ArrayList<>();
			
			parser.parse(inputStream, row -> {
				
				ShapeRawDTO dto = objectMapper.convertValue(row, ShapeRawDTO.class);
				
				rows.add(dto);
			});
			
			shapeService.importShapes(rows);
		}
		
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Transactional
	private void saveBatch(List<?> batch) {
		
		System.out.println("Chegou no save batch");
		
		if(batch.isEmpty()) return;
		
		Class<?> clas = batch.get(0).getClass();
		
		var repository = repositoryRegistry.get(clas);
		
		if(repository==null) {
			throw new RuntimeException("Repository não encontrado para: "+ clas.getSimpleName());
		}
		
		System.out.println("Salvando " + batch.size() + "registros de " + clas.getSimpleName());
		
		repository.saveAll((List)batch);
	}

}
