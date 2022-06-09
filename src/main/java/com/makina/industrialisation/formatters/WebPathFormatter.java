package com.makina.industrialisation.formatters;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class WebPathFormatter extends AbstractFormatter<String>{

	Logger logger = LogManager.getLogger(WebPathFormatter.class);
	
	private final String HTTP = "http://";
	private final String TOMCAT_PORT = "8080";
	private final String APK = "APK";
	
		
	/** 
	 * Formate le chemin web pour qu'il soit comphréensif par les applications clientes
	 * @return String
	 */
	@Override
	public String format(String nameAPK) {
		String webPath = HTTP; 

		try {
			webPath += Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		webPath += ":"+TOMCAT_PORT;
		webPath += "/"+APK+"/"+nameAPK;

		return webPath;
	}

}
