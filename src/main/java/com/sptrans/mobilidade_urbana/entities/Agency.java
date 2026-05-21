package com.sptrans.mobilidade_urbana.entities;

import java.util.Locale;
import java.util.TimeZone;

import com.sptrans.mobilidade_urbana.gtfs.GTFSFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@GTFSFile("agency.txt")
public class Agency {
	
	@Id
	private String agencyId;
	private String agencyName;
	private String agencyUrl;
	private TimeZone agencyTimezone;
	private Locale agencyLang;
	
	public Agency() {}

	public Agency(String agencyId, String agencyName, String agencyUrl, TimeZone agencyTimezone, Locale agencyLang) {
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
