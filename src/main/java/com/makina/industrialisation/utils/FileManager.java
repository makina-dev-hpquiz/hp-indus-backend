package com.makina.industrialisation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileManager {
	
	static Logger logger = LogManager.getLogger(FileManager.class);
			
	private FileManager() {
	    throw new IllegalStateException("FileManager Utility class");
	}
	
	/**
	 * Sauvegarde en dur le fichier en fourni en paramètre
	 * 
	 * @param MultipartFile screenshot
	 */
	public static void saveFile(MultipartFile screenshot, String path) {
		try {
			StringBuilder filePath = new StringBuilder(path);
			filePath.append(screenshot.getOriginalFilename());
			logger.debug("Enregistrement de l'image {}", filePath);
			screenshot.transferTo(new File(filePath.toString()));
		} catch (IllegalStateException e) {
			logger.error("Erreur d'état lors de l'enregistrement de l'image {}", e.getMessage());
		} catch (IOException e) {
			logger.error("Erreur lors de l'enregistrement de l'image {}", e.getMessage());
		}
	}
	
	/**
	 * Supprime un fichier existant à partir de chaîne fourni en paramètre
	 * 
	 * @param String path
	 */
	public static void deleteFile(String path) {
		try {
			Files.delete(Path.of(path));
		} catch (IOException e) {
			logger.error("Erreur lors de la suppression du fichier {}, {}", path, e.getMessage());
		}
	}
	
	/**
	 * Instancie le fichier à partir du path fourni en paramètre afin de récupérer le nom du fichier.
	 * 
	 * @param String path
	 * @return String
	 */
	public static String getName(String path) {
		File f = new File(path).getAbsoluteFile();
		return f.exists() ?  f.getName() : "";
	}
}
