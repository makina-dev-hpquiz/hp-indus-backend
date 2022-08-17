package com.makina.industrialisation.models;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;



@Entity
public class Incident{

	@Id
	@GeneratedValue(generator = "UUID")
	    @GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
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
	private Timestamp updatedAt;
	@Column
	private String type;
	@Column
	private String status;
		
	
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

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String date) {
		this.updatedAt= Timestamp.from(Instant.parse(date));
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
