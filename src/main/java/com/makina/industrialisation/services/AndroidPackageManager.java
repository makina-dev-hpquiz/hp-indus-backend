package com.makina.industrialisation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
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

	@Autowired
	AndroidPackageManagerConfiguration configuration;
	

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
		return this.getListAndroidPackageInformation(this.getAllAPK(this.configuration.getHpCorePartialName()));
	}

	/**
	 * Récupère la liste AndroidPackage pour l'APK Hp-Quiz
	 * @return List<AndroidPackage>
	 */
	public List<AndroidPackage> getAllHPQuizAPK() {
		return this.getListAndroidPackageInformation(this.getAllAPK(this.configuration.getHpQuizPartialName()));		
	}

	/**
	 * Récupère les informations du dernier HP-Core APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPCoreLatestAPK() {
		return this.getAndroidPackageInformation(this.configuration.getHpCoreLatest());
	}

	/**
	 * Récupère les informations du dernier HP-Quiz APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPQuizLatestAPK() {
		return this.getAndroidPackageInformation(this.configuration.getHpQuizLatest());		
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

		return this.sortByDate(apksList);
				
	}


	/**
	 * Récupère les informations AndroidPackage d'un fichier
	 * @param fileName
	 * @return AndroidPackage
	 */
	private AndroidPackage getAndroidPackageInformation(String fileName) {
		AndroidPackage apk = new AndroidPackage();

		File file = new File(this.configuration.getPath()+fileName).getAbsoluteFile();

		logger.debug("Récupération des informations liées au fichier {}",file.getAbsolutePath());
		if(file.exists()) {
			apk.setName(fileName);
			apk.setPath(this.webPathFormatter.format(fileName));
			//			apk.setVersion(nameApk); TODO
			try {
				FileTime ft = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
				apk.setBuildDate(ft);
				apk.setBuildDateFormatted(this.dateFormatter.format(apk.getBuildDate()));

				apk.setSize(this.sizeFormatter.format((double) Files.size(file.toPath())));
			} catch (IOException ex) {
				logger.error(ex.getMessage());
			}

		} else {
			logger.error("Le fichier {} demandé n'existe pas.", file.getAbsolutePath());
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
		
		File folder = new File(this.configuration.getPath()).getAbsoluteFile();
		
		List<File> filesList = new ArrayList<>();
		
		logger.debug("Accès au repertoire : {}", folder.getAbsolutePath());
		
		
		if(folder.exists()) {
			File[] completeFilesList = folder.listFiles();

			for(File file : completeFilesList){
				if (file.isFile() && file.getName().contains(partialFileName)) {
					filesList.add(file);
				}
			}
		} else {
			logger.error("Le dossier {} n'existe pas.", this.configuration.getPath());
		}
		logger.debug("Nombre d'APK à transmettre : {}", filesList.size());
		return filesList;
	}


	private List<AndroidPackage> sortByDate(List<AndroidPackage> androidPackageList) {

		Comparator<AndroidPackage> androidPackageBuilDateComparator
	      = Comparator.comparing(
	    		  AndroidPackage::getBuildDate, (s1, s2) -> s2.compareTo(s1)
	      );
	    
		androidPackageList.sort(androidPackageBuilDateComparator);	    
		return androidPackageList;
	}

}
