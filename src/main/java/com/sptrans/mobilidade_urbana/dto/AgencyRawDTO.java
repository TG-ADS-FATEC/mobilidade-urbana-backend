package com.sptrans.mobilidade_urbana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgencyRawDTO {
	
	@JsonProperty("agency_id")
	private String agencyId;
	@JsonProperty("agency_name")
	private String agencyName;
	@JsonProperty("agency_url")
	private String agencyUrl;
	@JsonProperty("agency_timezone")
	private String agencyTimezone;
	@JsonProperty("agency_lang")
	private String agencyLang;
	
	public AgencyRawDTO() {}

	public AgencyRawDTO(String agencyId, String agencyName, String agencyUrl, String agencyTimezone, String agencyLang) {
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

	public String getAgencyTimezone() {
		return agencyTimezone;
	}

	public void setAgencyTimezone(String agencyTimezone) {
		this.agencyTimezone = agencyTimezone;
	}

	public String getAgencyLang() {
		return agencyLang;
	}

	public void setAgencyLang(String agencyLang) {
		this.agencyLang = agencyLang;
	}

}
