package com.makina.industrialisation.formatters;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.TomcatConfiguration;

@Service
public abstract class AbstractWebPathFormatter implements Formatter<String>{
	
	@Autowired
	TomcatConfiguration tomcatConfiguration;
	
	Logger logger = LogManager.getLogger(AbstractWebPathFormatter.class);
			
	/** 
	 * Formate le chemin web pour qu'il soit comphréensif par les applications clientes
	 * @return String
	 */
	@Override
	public String format(String nameFile) {
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
		sb.append(this.getFolderName());
		sb.append("/");
		sb.append(nameFile);

		return sb.toString();
	}

	/**
	 * Retourne le nom du dossier nécessaire à la fonction Format()
	 * @return String
	 */
	protected abstract String getFolderName();
}
