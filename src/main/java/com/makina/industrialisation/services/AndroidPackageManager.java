package com.makina.industrialisation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.extractors.DateExtractor;
import com.makina.industrialisation.extractors.VersionExtractor;
import com.makina.industrialisation.filters.NameFilter;
import com.makina.industrialisation.formatters.ApkWebPathFormatter;
import com.makina.industrialisation.formatters.DateFormatter;
import com.makina.industrialisation.formatters.SizeFormatter;
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
	private ApkWebPathFormatter webPathFormatter;
	
	@Autowired
	private DateFormatter dateFormatter;

	@Autowired
	private DateExtractor dateExtractor;
	
	@Autowired
	private VersionExtractor versionExtractor;
	
	
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
			apk.setBuildDate(dateExtractor.extract(file));
			apk.setBuildDateFormatted(this.dateFormatter.format(apk.getBuildDate()));
			//Prepare extract version
			if(apk.isLatest()) {
				String partialName = NameFilter.hasPartialName(apk.getName(), this.configuration.getHpCorePartialName()) ?  this.configuration.getHpCorePartialName() :  this.configuration.getHpQuizPartialName();
				List<AndroidPackage> apkList = new ArrayList<>();
				for(File f : this.getAllAPK(partialName)) {
					AndroidPackage nApk = new AndroidPackage();
					nApk.setName(f.getName());
					nApk.setBuildDate(dateExtractor.extract(f));
					apkList.add(nApk);
				}
			apk.setVersion(this.versionExtractor.extract(apk, apkList)); 
			} else {
				apk.setVersion(this.versionExtractor.extract(apk)); 
			}
			
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


	/**
	 * Tri la liste du plus récent au plus ancien
	 * 
	 * @param List<AndroidPackage> androidPackageList
	 * @return List<AndroidPackage>
	 */
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
			AndroidPackage toDelete = androidPackageList.stream().filter(AndroidPackage::isLatest).collect(Collectors.toList()).get(0);
			androidPackageList.remove(toDelete);
		} catch(Exception e) {
			logger.error("Impossible de trouver et de supprimer l'élément doublon : {}", e.getMessage());
		}
		
		return androidPackageList;
	}

}

