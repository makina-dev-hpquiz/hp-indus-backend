package com.makina.industrialisation.models;

import java.util.List;

public class IncidentProperty {
	
	private List<String> properties;
	private List<String> searchProperties;
	private String defaultProperty;

	/**
	 * @param List<String> properties
	 * @param List<String> searchProperties
	 * @param String defaultProperty
	 */
	public IncidentProperty(List<String> properties, List<String> searchProperties, String defaultProperty){
		this.properties = properties;
		this.searchProperties = searchProperties;
		this.defaultProperty = defaultProperty;
	}

	public List<String> getProperties() {
		return properties;
	}

	public void setProperties(List<String> properties) {
		this.properties = properties;
	}

	public List<String> getSearchProperties() {
		return searchProperties;
	}

	public void setSearchProperties(List<String> searchProperties) {
		this.searchProperties = searchProperties;
	}

	public String getDefaultProperty() {
		return defaultProperty;
	}

	public void setDefaultProperty(String defaultProperty) {
		this.defaultProperty = defaultProperty;
	}
	
	
}
