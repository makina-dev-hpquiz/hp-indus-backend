package com.makina.industrialisation.formatters;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.configuration.TomcatConfiguration;

@Service
public class WebPathFormatter implements Formatter<String>{

	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
	@Autowired
	TomcatConfiguration tomcatConfiguration;
	
	Logger logger = LogManager.getLogger(WebPathFormatter.class);
			
	/** 
	 * Formate le chemin web pour qu'il soit comphréensif par les applications clientes
	 * @return String
	 */
	@Override
	public String format(String nameAPK) {
		StringBuilder sb = new StringBuilder();
				
		sb.append(tomcatConfiguration.getProtocol());
		try {
			sb.append(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		sb.append(":");
		sb.append(tomcatConfiguration.getPort());
		sb.append("/");
		sb.append(configuration.getFolderName());
		sb.append("/");
		sb.append(nameAPK);

		return sb.toString();
	}

}
