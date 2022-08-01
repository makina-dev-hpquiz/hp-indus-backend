package com.makina.industrialisation.dto;

import java.util.UUID;

public class IncidentDTO {
	
	private UUID id;
	private String title;
	private String description;
	private String screenshotPath;
	private String screenshotWebPath;
	private String priority;
	private String date;
	private String type;
	
	public IncidentDTO() {
	}
	
	public IncidentDTO(String title, String description, String screenshotPath,
			 String screenshotWebPath, String priority, String date, String type) {
		
		this.title = title;
		this.description = description;
		this.screenshotPath = screenshotPath;
		this.screenshotWebPath = screenshotWebPath;
		this.priority = priority;
		this.date = date;
		this.type = type;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public String getScreenshotWebPath() {
		return screenshotWebPath;
	}

	public void setScreenshotWebPath(String screenshotWebPath) {
		this.screenshotWebPath = screenshotWebPath;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}