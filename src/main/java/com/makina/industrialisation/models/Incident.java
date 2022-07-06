package com.makina.industrialisation.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Incident{

	@Id
	private UUID id;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String screenshotPath;
	@Column
	private String screenshotWebPath;
	@Column
	private String priority;
	@Column
	private String date;
	@Column
	private String type;
	
	public Incident() {}
		
//	public Incident(UUID id, String title, String description, String screenshotPath,
//			 String screenshotWebPath, String priority, String date, String type){
//		
//		this.id = id;
//		this.title = title;
//		this.description = description;
//		this.screenshotPath = screenshotPath;
//		this.screenshotWebPath = screenshotWebPath;
//		this.priority = priority;
//		this.date = date;
//		this.type = type;
//	}
	
	
	public void setId(UUID id) {
		this.id = id;
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
