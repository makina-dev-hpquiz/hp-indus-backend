package com.makina.industrialisation.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Incident extends Object{

	@Id
	private UUID id;
	private String title;
	private String description;
	private String screenshotPath;
	private String screenshotWebPath;
	private String priority;
	private String date;
	private String type;
	
	public Incident() {
		this.id = UUID.randomUUID();
	}
		
	public Incident(String title, String description, String screenshotPath,
			 String screenshotWebPath, String priority, String date, String type){
		
		this.id = UUID.randomUUID();
		
		this.title = title;
		this.description = description;
		this.screenshotPath = screenshotPath;
		this.setScreenshotWebPath(screenshotWebPath);
		this.priority = priority;
		this.date = date;
		this.type = type;
	}

	public UUID getId() {
		return id;
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

	public String getScreenshotWebPath() {
		return screenshotWebPath;
	}

	public void setScreenshotWebPath(String screenshotWebPath) {
		this.screenshotWebPath = screenshotWebPath;
	}
	

	
	
	
}
