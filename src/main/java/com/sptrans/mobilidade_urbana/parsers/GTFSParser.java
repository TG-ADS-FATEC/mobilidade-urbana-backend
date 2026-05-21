package com.sptrans.mobilidade_urbana.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class GTFSParser {
	
	private final CsvMapper mapper;
	
	public GTFSParser() {
		
		mapper = new CsvMapper();
		
		mapper.findAndRegisterModules();
		
		mapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		mapper.enable(CsvParser.Feature.TRIM_SPACES);
		
		mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
		
		mapper.enable(CsvParser.Feature.INSERT_NULLS_FOR_MISSING_COLUMNS);
		
		mapper.enable(CsvParser.Feature.ALLOW_TRAILING_COMMA);
		
	}
	
	public <T> void parse(InputStream inputStream, Consumer<Map<String, String>> consumer) throws Exception {
		
		//System.out.println("PARSER CHAMADO");
		
		CsvSchema schema = CsvSchema.builder().setUseHeader(true).build();
		
		Reader reader = new InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8);
		
		MappingIterator<Map<String,String>> iterator = mapper.readerFor(Map.class).with(schema).readValues(reader);
			
			while (iterator.hasNext()) {
				Map<String, String> row = iterator.next();
				
				//System.out.println("Debug GTFS ROW: "+ row);
				
				consumer.accept(row);
			}
		}

}
