package com.makina.industrialisation.models;

import java.nio.file.attribute.FileTime;

public class AndroidPackage {
	private String name;
	private String version;

	private String path;
	private String buildDateStr;
	private FileTime buildDate;
	
	
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
	public String getBuildDateStr() {
		return buildDateStr;
	}
	public void setBuildDateStr(String buildDateStr) {
		this.buildDateStr = buildDateStr;
	}
	public FileTime getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(FileTime buildDate) {
		this.buildDate = buildDate;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String string) {
		this.size = string;
	}

}
