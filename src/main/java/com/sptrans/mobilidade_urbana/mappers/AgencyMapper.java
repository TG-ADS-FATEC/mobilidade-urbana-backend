package com.sptrans.mobilidade_urbana.mappers;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.AgencyDTO;
import com.sptrans.mobilidade_urbana.dto.AgencyRawDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;

@Component
public class AgencyMapper {
	
	public Agency toEntity(AgencyRawDTO rawDto) {
		
		if(rawDto==null) {
			return null;
		}
		
		Agency agency = new Agency();
		
		agency.setAgencyId(rawDto.getAgencyId());
		agency.setAgencyName(rawDto.getAgencyName());
		agency.setAgencyUrl(rawDto.getAgencyUrl());
		agency.setAgencyTimezone(rawDto.getAgencyTimezone()!=null
				? TimeZone.getTimeZone(rawDto.getAgencyTimezone()): null);
		agency.setAgencyLang(rawDto.getAgencyLang()!=null
				? Locale.forLanguageTag(rawDto.getAgencyLang()):null);
		
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
