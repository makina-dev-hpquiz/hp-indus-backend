package com.makina.industrialisation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan 
@ConfigurationProperties(prefix = "tomcat.apk")
public class AndroidPackageManagerConfiguration {

	
	private String path;
	
	@Value("hp-core-latest")
	private String hpCoreLatest;
	@Value("hp-core-partial-name")
	private String hpCorePartialName;

	@Value("hp-quiz-latest")
	private String hpQuizLatest;
	@Value("hp-quiz-partial-name")
	private String hpQuizPartialName;
	
	@Value("folder-name")
	private String folderName;
	
	AndroidPackageManagerConfiguration(){
		super();
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHpCoreLatest() {
		return hpCoreLatest;
	}
	public void setHpCoreLatest(String hpCoreLatest) {
		this.hpCoreLatest = hpCoreLatest;
	}
	public String getHpCorePartialName() {
		return hpCorePartialName;
	}
	public void setHpCorePartialName(String hpCorePartialName) {
		this.hpCorePartialName = hpCorePartialName;
	}
	public String getHpQuizLatest() {
		return hpQuizLatest;
	}
	public void setHpQuizLatest(String hpQuizLatest) {
		this.hpQuizLatest = hpQuizLatest;
	}
	public String getHpQuizPartialName() {
		return hpQuizPartialName;
	}
	public void setHpQuizPartialName(String hpQuizPartialName) {
		this.hpQuizPartialName = hpQuizPartialName;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	

}
