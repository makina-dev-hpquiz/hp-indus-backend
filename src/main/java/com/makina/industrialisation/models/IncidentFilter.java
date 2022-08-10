package com.makina.industrialisation.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.makina.industrialisation.constants.IncidentControllerConstants;

public class IncidentFilter {
	
	private static final String NEGATIVE = "-";
	
	private String sortBy;
	private String searchBy;
	private List<String> status;
	private String priorityLevel;
	private String incidentType;
	
	public IncidentFilter(String sortBy, String searchBy, String[] status, String priorityLevel, String incidentType){
		this.sortBy = sortBy;
		this.searchBy = searchBy;
		this.status = (status != null) ? new ArrayList<>(Arrays.asList(status)) : new ArrayList<>();
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
	
	public boolean hasValidSortBy() {
		return isNotEmpty(this.sortBy) && (this.sortBy.equals(IncidentControllerConstants.DEFAULT_SORT_BY) || this.sortBy.equals(NEGATIVE+IncidentControllerConstants.DEFAULT_SORT_BY));		
	}
	
	public boolean hasValidSearchBy() {
		return isNotEmpty(this.searchBy);
	}
	
	public boolean hasValidStatus() {
		if(this.status == null) {
			return false;
		} else {
			if(!this.status.isEmpty()){
				for(String statusToValidate : this.status) {
					if(!IncidentStatus.get().contains(statusToValidate)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}
	public boolean hasValidPriority() {
		return (isNotEmpty(this.priorityLevel) && IncidentPriority.get().contains(this.priorityLevel));
	}
	
	public boolean hasValidIncidentType() {
		return (isNotEmpty(this.incidentType) && IncidentType.get().contains(this.incidentType));
	}
	
	private boolean isNotEmpty(String str) {
		return (str != null && !str.equals(""));
	}
	

}
