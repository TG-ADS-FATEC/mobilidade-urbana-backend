package com.sptrans.mobilidade_urbana.pagination;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
	
	public static <T> PageResponseDTO<T> from(Page<T> page){
		
		return new PageResponseDTO<>(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.hasNext(),
				page.hasPrevious());
	}

}
