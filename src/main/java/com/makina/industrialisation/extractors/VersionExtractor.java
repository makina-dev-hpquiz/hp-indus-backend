package com.makina.industrialisation.extractors;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.filters.DateFilter;
import com.makina.industrialisation.models.AndroidPackage;

/**
 * Permet d'extraire le numéro de Version d'une APK.
 * @author PC Valentin
 *
 */
@Service
public class VersionExtractor {

	Logger logger = LogManager.getLogger(VersionExtractor.class);
	
	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
		
	/**
	 * Extrait la version en manipulant des APK
	 * @param AndroidPackage apk
	 * @return String
	 */
	public String extract(AndroidPackage apk, List<AndroidPackage> apkList) {
		if(apk.isLatest()) {
			return this.extractVersionOfAPKLatest(apk, apkList);
		} else {
			return this.extract(apk);
		}
	}
	
	/** 
	 * De manière globale lorsque l'APK n'est pas latest, la version est contenu dans le nom de l'APK.
	 * Cette méthode a pour objectif de manipuler le nom de l'application pour en extraire la version.
	 * @param String apkName
	 * @return String
	 */
	public String extract(AndroidPackage apk) {
		String apkName = apk.getName();		
		if(!apk.isLatest() && apkName.chars().anyMatch(Character::isDigit)) {
			return apkName.substring(apkName.lastIndexOf("-")+1, apkName.indexOf(".apk"));
		} else {
			logger.error("Impossible de déterminer la valeur de la version {}", apkName);
			return "";
		}
	}
	
	/**
	 * Récupére une APK ayant la même date que l'APK transmis en paramètre.
	 * Cette APK récupéré ne doit pas posséders le mot clef latest
	 *  
	 * @param AndroidPackage apk
	 * @param List<AndroidPackage> apkList
	 * @return AndroidPackage
	 */
	private String extractVersionOfAPKLatest(AndroidPackage apk, List<AndroidPackage> apkList) {
		try {
			AndroidPackage apkFiltered = apkList.stream().filter(
				a -> !a.isLatest() &&
				DateFilter.isApproximateDate(apk.getBuildDate(), a.getBuildDate())).collect(Collectors.toList()).get(0); 				
			return this.extract(apkFiltered);
		} catch (Exception e) {
			logger.error("Erreur lors de l'extraction de la version : {}" , e.getMessage());
		}
		return "";
	}
}
