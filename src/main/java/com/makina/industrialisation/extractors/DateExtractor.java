package com.makina.industrialisation.extractors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.formatters.DateFormatter;

@Service
public class DateExtractor {

	@Autowired
	DateFormatter dateFormatter;
	
	Logger logger = LogManager.getLogger(DateExtractor.class);
	
	/**
	 * Extrait la date de création du fichier fourni en paramètre 
	 * @param File f
	 * @return FileTime
	 */
	public FileTime extract(File f) {
		try {
			return (FileTime) Files.getAttribute(f.toPath(), "creationTime");
		} catch (IOException ex) {
			logger.error("Imposible de récupérer la date decréation du fichier {}", ex.getMessage());
		}
		return null;
	}
}
