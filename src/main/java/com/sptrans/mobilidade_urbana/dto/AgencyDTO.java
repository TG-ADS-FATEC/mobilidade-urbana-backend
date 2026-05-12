package com.sptrans.mobilidade_urbana.dto;

import java.net.URL;
import java.util.Locale;
import java.util.TimeZone;

import com.sptrans.mobilidade_urbana.entities.Agency;

public class AgencyDTO {
	
	private Long agencyId;
	private String agencyName;
	private URL agencyUrl;
	private TimeZone agencyTimezone;
	private Locale agencyLang;
	
	public AgencyDTO() {}

	public AgencyDTO(Long agencyId, String agencyName, URL agencyUrl, TimeZone agencyTimezone, Locale agencyLang) {
		super();
		this.agencyId = agencyId;
		this.agencyName = agencyName;
		this.agencyUrl = agencyUrl;
		this.agencyTimezone = agencyTimezone;
		this.agencyLang = agencyLang;
	}
	
	public AgencyDTO(Agency entity) {
		agencyId = entity.getAgencyId();
		agencyName = entity.getAgencyName();
		agencyUrl = entity.getAgencyUrl();
		agencyTimezone = entity.getAgencyTimezone();
		agencyLang = entity.getAgencyLang();
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public URL getAgencyUrl() {
		return agencyUrl;
	}

	public TimeZone getAgencyTimezone() {
		return agencyTimezone;
	}

	public Locale getAgencyLang() {
		return agencyLang;
	}
	

}
