package com.makina.industrialisation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tomcat")
public class TomcatConfiguration {

	
	private String port;

	private String protocol;
	
	@Value("img-path")
	private String imgPath;
	
	@Value("img-folder")
	private String imgFolder;
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getImgFolder() {
		return imgFolder;
	}
	public void setImgFolder(String imgFolder) {
		this.imgFolder = imgFolder;
	}
	
	
	
	
}
