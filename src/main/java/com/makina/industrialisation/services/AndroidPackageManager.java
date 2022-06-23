package com.makina.industrialisation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.filters.DateFilter;
import com.makina.industrialisation.filters.NameFilter;
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
	
	private String latest = "latest";

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
	public List<AndroidPackage> getAllHPCore() {
		return this.getListAndroidPackageInformation(this.getAllAPK(this.configuration.getHpCorePartialName()));
	}

	/**
	 * Récupère la liste AndroidPackage pour l'APK Hp-Quiz
	 * @return List<AndroidPackage>
	 */
	public List<AndroidPackage> getAllHPQuiz() {
		return this.getListAndroidPackageInformation(this.getAllAPK(this.configuration.getHpQuizPartialName()));		
	}

	/**
	 * Récupère les informations du dernier HP-Core APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPCoreLatest() {
		return this.getAndroidPackageInformation(this.configuration.getHpCoreLatest());
	}

	/**
	 * Récupère les informations du dernier HP-Quiz APK
	 * @return AndroidPackage
	 */
	public AndroidPackage getHPQuizLatest() {
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
		this.sortByDate(apksList);
		return this.deleteDoublon(apksList);
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
			
			this.extractDate(apk, file);
			apk.setVersion(this.extractVersion(apk)); 
			
			try {
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

	/**
	 * Retire de la liste un l'item en doublon ayant la valeur '' dans le nom du fichier
	 * @param androidPackageList
	 * @return androidPackageList
	 */
	private List<AndroidPackage> deleteDoublon(List<AndroidPackage> androidPackageList) {
		try {
			AndroidPackage toDelete = androidPackageList.stream().filter(apk -> apk.getName().contains(latest)).collect(Collectors.toList()).get(0);
			androidPackageList.remove(toDelete);
		} catch(Exception e) {
			logger.error("Impossible de trouver et de supprimer l'élément doublon : {}", e.getMessage());
		}
		
		return androidPackageList;
	}

	/**
	 * Extrait la version en manipulant des APK
	 * @param AndroidPackage apk
	 * @return String
	 */
	private String extractVersion(AndroidPackage apk) {
		if(apk.getName().contains(latest)) {
			// Détermine le nom de l'application
			String partialName = "";
			if(apk.getName().contains(this.configuration.getHpCorePartialName())) {
				partialName = this.configuration.getHpCorePartialName();
			} else if(apk.getName().contains(this.configuration.getHpQuizPartialName())) {
				partialName = this.configuration.getHpQuizPartialName();
			} else {
				logger.error("Impossible d'identifié le nom de l'APK : {}", apk.getName());
			}
			
			//Récupère les fichiers reliés à l'application déterminé plus tot
			List<AndroidPackage> apkList = new ArrayList<>();
			for(File f : this.getAllAPK(partialName)) {
				AndroidPackage nApk = new AndroidPackage();
				nApk.setName(f.getName());
				this.extractDate(nApk, f);
				apkList.add(nApk);
			}
			
			// Récupére une APK ayant la même date et n'ayant pas le mot clef latest que l'APK dont on cherche la version
			AndroidPackage apkToFilter = apk;
			try {
				AndroidPackage apkFiltered = apkList.stream().filter(
						a -> !NameFilter.isLatestVersion(a.getName()) &&
						DateFilter.isApproximateDate(apkToFilter.getBuildDate(), a.getBuildDate())).collect(Collectors.toList()).get(0); 
		
				return this.extractVersion(apkFiltered.getName());
			} catch (Exception e) {
				logger.error("Erreur lors de l'extraction de la version : {}" , e.getMessage());
			}
		
		} else {
			return this.extractVersion(apk.getName());
		}

		return "";
	}
	
	/**
	 * Extrait la version en manipulant une String
	 * @param String apkName
	 * @return String
	 */
	private String extractVersion(String apkName) {
		if(!apkName.contains(latest) && apkName.matches("(.*)[0-9](.*)")) {
			return apkName.substring(apkName.lastIndexOf("-")+1, apkName.indexOf(".apk"));
		} else {
			logger.error("Impossible de déterminer la valeur de la version {}", apkName);
			return "";
		}
	}

	private AndroidPackage extractDate(AndroidPackage apk, File f) {
		try {
			FileTime ft = (FileTime) Files.getAttribute(f.toPath(), "creationTime");
			apk.setBuildDate(ft);
			apk.setBuildDateFormatted(this.dateFormatter.format(apk.getBuildDate()));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}

		return apk;
	}

}

