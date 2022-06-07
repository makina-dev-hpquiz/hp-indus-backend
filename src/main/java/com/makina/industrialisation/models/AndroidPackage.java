package com.makina.industrialisation.models;

import java.util.Date;

public class AndroidPackage {
	private String name;
	private String version;

	private String path;
	private String buildDate;
	private String size;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String string) {
		this.size = string;
	}

}
