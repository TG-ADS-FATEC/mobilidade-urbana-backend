package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.PreferenceDTO;
import com.sptrans.mobilidade_urbana.entities.Preference;

@Component
public class PreferenceMapper {
	
	public Preference toEntity(PreferenceDTO dto) {
		
		Preference preference = new Preference();
		
		preference.setTransportTypes(dto.getTransportTypes());
		preference.setRoutePreference(dto.getRoutePreference());
		preference.setSlowPace(dto.getSlowPace());
		preference.setMaxWalkingTime(dto.getMaxWalkingTime());
		preference.setCreatedAt(dto.getCreatedAt());
		preference.setUpdatedAt(dto.getUpdatedAt());
		
		return preference;
	}
	
	public PreferenceDTO toDTO(Preference entity) {
		
		if(entity==null) return null;
		
		return new PreferenceDTO(
				entity.getTransportTypes(),
				entity.getRoutePreference(),
				entity.getSlowPace(),
				entity.getMaxWalkingTime(),
				entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

}
