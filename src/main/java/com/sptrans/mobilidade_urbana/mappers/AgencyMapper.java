package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.AgencyDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;

@Component
public class AgencyMapper {
	
	public Agency toEntity(AgencyDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		Agency agency = new Agency();
		
		agency.setAgencyId(dto.getAgencyId());
		agency.setAgencyName(dto.getAgencyName());
		agency.setAgencyUrl(dto.getAgencyUrl());
		agency.setAgencyTimezone(dto.getAgencyTimezone());
		agency.setAgencyLang(dto.getAgencyLang());
		
		return agency;
	}
	
	public AgencyDTO toDTO(Agency entity) {
		
		if(entity==null) {
			return null;
		}
		
		return new AgencyDTO(
				entity.getAgencyId(),
				entity.getAgencyName(),
				entity.getAgencyUrl(),
				entity.getAgencyTimezone(),
				entity.getAgencyLang()
				);
	}

}
