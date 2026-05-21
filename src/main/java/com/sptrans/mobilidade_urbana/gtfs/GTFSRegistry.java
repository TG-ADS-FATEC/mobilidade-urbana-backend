package com.sptrans.mobilidade_urbana.gtfs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.entities.Agency;

@Component
public class GTFSRegistry {
	
	private final Map<String, Class<?>> registry = new HashMap<>();
	
	public GTFSRegistry() {
		load();
	}
	
	private void load() {
		
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		
		scanner.addIncludeFilter(new AnnotationTypeFilter(GTFSFile.class));
		
		Set<BeanDefinition> candidates = scanner.findCandidateComponents("com.sptrans.mobilidade_urbana.entities");
		
		for (BeanDefinition beanDefinition : candidates) {
			try {
				
				Class<?> clas = Class.forName(beanDefinition.getBeanClassName());
				
				GTFSFile annotation = clas.getAnnotation(GTFSFile.class);
				
				if (annotation != null) {
					registry.put(annotation.value(), clas);
					
					System.out.println("[GTFS]"+annotation.value() + "->" + clas.getSimpleName());
				}
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public Class<?> get(String fileName){
		
		return registry.get(fileName);
	}

}
