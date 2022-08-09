package com.makina.industrialisation.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IncidentFilter {
	private String sortBy;
	private String searchBy;
	private List<String> status;
	private String priorityLevel;
	private String incidentType;
	public IncidentFilter(String sortBy, String searchBy, String[] status, String priorityLevel, String incidentType){
		this.sortBy = sortBy;
		this.searchBy = searchBy;
		this.status = (status != null) ? new ArrayList<String>(Arrays.asList(status)) : new ArrayList<String>();
//		status != null ? this.status = new ArrayList<String>(Arrays.asList(statusArray)) : this.status = new ArrayList<String>();
//		if(status != null ) {
//			status = new ArrayList<String>(Arrays.asList(statusArray)); 
//		} else {
//			status = new ArrayList<String>();
//		}
		this.priorityLevel = priorityLevel;
		this.incidentType = incidentType;
		
	}
	
	
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public String getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	
	

}
