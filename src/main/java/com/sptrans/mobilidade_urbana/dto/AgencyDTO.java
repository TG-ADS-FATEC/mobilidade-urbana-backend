package com.sptrans.mobilidade_urbana.dto;

import java.util.Locale;
import java.util.TimeZone;

public class AgencyDTO {
	
	private String agencyId;
	private String agencyName;
	private String agencyUrl;
	private TimeZone agencyTimezone;
	private Locale agencyLang;
	
	public AgencyDTO() {}

	public AgencyDTO(String agencyId, String agencyName, String agencyUrl, TimeZone agencyTimezone, Locale agencyLang) {
		super();
		this.agencyId = agencyId;
		this.agencyName = agencyName;
		this.agencyUrl = agencyUrl;
		this.agencyTimezone = agencyTimezone;
		this.agencyLang = agencyLang;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public TimeZone getAgencyTimezone() {
		return agencyTimezone;
	}

	public void setAgencyTimezone(TimeZone agencyTimezone) {
		this.agencyTimezone = agencyTimezone;
	}

	public Locale getAgencyLang() {
		return agencyLang;
	}

	public void setAgencyLang(Locale agencyLang) {
		this.agencyLang = agencyLang;
	}
	

}
