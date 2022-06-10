package com.makina.industrialisation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.formatters.DateFormatter;
import com.makina.industrialisation.formatters.SizeFormatter;
import com.makina.industrialisation.formatters.WebPathFormatter;
import com.makina.industrialisation.models.AndroidPackage;

/**
 * AndroidPackageManager récupère des fichiers type APK et en extrait les informations
 * @author Makina dev
 *
 */
@Service
public class AndroidPackageManager {

	Logger logger = LogManager.getLogger(AndroidPackageManager.class);

	// TODO A mettre dans l'application.properties
	private final String ANDROID_PACKAGE_PATH = "C:/bin/apache-tomcat-9.0.55/webapps/APK/";

	private static final String LAST_HP_CORE_APK = "hp-core.apk";
	private static final String LAST_HP_QUIZ_APK = "hp-game.apk";

	private static final String HP_CORE = "hp-core";
	private static final String HP_QUIZ = "hp-quiz";


	@Autowired
	private SizeFormatter sizeFormatter;

	@Autowired
	private WebPathFormatter webPathFormatter;

	@Autowired
	private DateFormatter dateFormatter;

	/**
	 * Récupère la liste AndroidPackage pour l'APK Hp-Core
	 * @return List<AndroidPackage>
	 */
	public List<AndroidPackage> getAllHPCoreAPK() {
		return this.getListAndroidPackageInformation(this.getAllAPK(HP_CORE));
	}

	/**
	 * Récupère la liste AndroidPackage pour l'APK Hp-Quiz
	 * @return List<AndroidPackage>
	 */
	public List<AndroidPackage> getAllHPQuizAPK() {
		return this.getListAndroidPackageInformation(this.getAllAPK(HP_QUIZ));		
	}

	/**
	 * Récupère les informations du dernier HP-Core APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPCoreLatestAPK() {
		return this.getAndroidPackageInformation(LAST_HP_CORE_APK);
	}

	/**
	 * Récupère les informations du dernier HP-Quiz APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPQuizLatestAPK() {
		return this.getAndroidPackageInformation(LAST_HP_QUIZ_APK);		
	}

	/**
	 * Récupère les informations AndroidPackage d'une liste de fichier
	 * @param filesList
	 * @return List<AndroidPackage>
	 */
	private List<AndroidPackage> getListAndroidPackageInformation(List<File> filesList) {

		List<AndroidPackage> apksList = new ArrayList<>();

		for(File file : filesList) {
			apksList.add(this.getAndroidPackageInformation(file.getName()));
		}
		// TODO Ordonnancement? Plus récent au plus ancien

		return apksList;				
	}


	/**
	 * Récupère les informations AndroidPackage d'un fichier
	 * @param fileName
	 * @return AndroidPackage
	 */
	private AndroidPackage getAndroidPackageInformation(String fileName) {
		AndroidPackage apk = new AndroidPackage();
		File file = new File(this.ANDROID_PACKAGE_PATH+fileName);

		logger.debug("Récupération des informations liées au fichier {0}",file.getAbsolutePath());
		if(file.exists()) {
			apk.setName(fileName);
			apk.setPath(this.webPathFormatter.format(fileName));
			//			apk.setVersion(nameApk); TODO
			try {
				FileTime ft = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
				apk.setBuildDate(this.dateFormatter.format(ft));

				apk.setSize(this.sizeFormatter.format((double) Files.size(file.toPath())));
			} catch (IOException ex) {
				logger.error(ex.getMessage());
			}

		} else {
			logger.error("Le fichier {0} demandé n'existe pas.", file.getAbsolutePath());
		}

		return apk;
	}


	/**
	 * Récupère la liste de fichiers présents dans le répertoire this.ANDROID_PACKAGE_PATH 
	 * et retourne ceux correspondant au nom de fichier fourni en paramètre
	 * @param partialFileName 
	 * @return List<File>
	 */
	private List<File> getAllAPK(String partialFileName) {
		File folder = new File(this.ANDROID_PACKAGE_PATH);
		List<File> filesList = new ArrayList<>();

		if(folder.exists()) {
			File[] completeFilesList = folder.listFiles();

			for(File file : completeFilesList){
				if (file.isFile() && file.getName().contains(partialFileName)) {
					filesList.add(file);
				}
			}
		} else {
			logger.error("Le dossier {0} n'existe pas.", this.ANDROID_PACKAGE_PATH);
		}
		logger.debug("Nombre d'APK à transmettre : {0}", filesList.size());
		return filesList;
	}



}
