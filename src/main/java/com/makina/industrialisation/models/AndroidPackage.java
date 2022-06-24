package com.makina.industrialisation.models;

import java.nio.file.attribute.FileTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AndroidPackage {
	private String name;
	private String version;

	private String path;
	private String buildDateFormatted;
	
	@JsonIgnore
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
	@JsonGetter("buildDate")
	public String getBuildDateFormatted() {
		return buildDateFormatted;
	}
	public void setBuildDateFormatted(String buildDateFormatted) {
		this.buildDateFormatted = buildDateFormatted;
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
	
	/**
	 * Indique si le nom de l'apk contient le mot clef latest
	 * @return boolean
	 */
	public boolean isLatest() {
		return getName().contains("latest");
	}
}
