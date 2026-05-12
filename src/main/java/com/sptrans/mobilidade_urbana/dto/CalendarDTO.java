package com.sptrans.mobilidade_urbana.dto;

import java.util.Date;

import com.sptrans.mobilidade_urbana.entities.Calendar;

public class CalendarDTO {
	
	private String serviceId;
	private Boolean monday;
	private Boolean tuesday;
	private Boolean wednesday;
	private Boolean thursday;
	private Boolean friday;
	private Boolean saturday;
	private Boolean sunday;
	private Date startDate;
	private Date endDate;
	
	public CalendarDTO() {}

	public CalendarDTO(String serviceId, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday,
			Boolean friday, Boolean saturday, Boolean sunday, Date startDate, Date endDate) {
		super();
		this.serviceId = serviceId;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public CalendarDTO(Calendar entity) {
		serviceId = entity.getServiceId();
		monday = entity.getMonday();
		tuesday = entity.getTuesday();
		wednesday = entity.getWednesday();
		thursday = entity.getThursday();
		friday = entity.getFriday();
		saturday = entity.getSaturday();
		sunday = entity.getSunday();
		startDate = entity.getStartDate();
		endDate = entity.getEndDate();
	}

	public String getServiceId() {
		return serviceId;
	}

	public Boolean getMonday() {
		return monday;
	}

	public Boolean getTuesday() {
		return tuesday;
	}

	public Boolean getWednesday() {
		return wednesday;
	}

	public Boolean getThursday() {
		return thursday;
	}

	public Boolean getFriday() {
		return friday;
	}

	public Boolean getSaturday() {
		return saturday;
	}

	public Boolean getSunday() {
		return sunday;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	

}
