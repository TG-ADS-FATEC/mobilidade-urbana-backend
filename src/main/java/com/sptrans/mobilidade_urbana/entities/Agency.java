package com.sptrans.mobilidade_urbana.entities;

import java.net.URL;
import java.util.Locale;
import java.util.TimeZone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Agency {
	
	@Id
	private String agencyId;
	private String agencyName;
	private URL agencyUrl;
	private TimeZone agencyTimezone;
	private Locale agencyLang;
	
	public Agency() {}

	public Agency(String agencyId, String agencyName, URL agencyUrl, TimeZone agencyTimezone, Locale agencyLang) {
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

	public URL getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(URL agencyUrl) {
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
