package com.makina.industrialisation.services;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.AndroidPackage;

@Service
public class AndroidPackageManager {

	// TODO A mettre dans l'application.properties
	private final String ANDROID_PACKAGE_PATH = "C:/bin/apache-tomcat-9.0.55/webapps/APK/";
	private final String TOMCAT_PORT = "8080";
	
	private final String HTTP = "http://";
	private final String APK = "APK";
	private final String LAST_HP_CORE_APK = "hp-core.apk";
	private final String LAST_HP_QUIZ_APK = "hp-game.apk";
	
	private final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final double KO = 1024d;
    private final double MO = 1048576d;
    
    
	/**
	 * Récupère les informations d'un AndroidPackage 
	 * @param value
	 * @return
	 */
	public AndroidPackage getAndroidPackageInformation(String value) {
		String nameApk = "";
		switch(value) {
		case "hp-core":
			nameApk = "hp-core.apk";
			break;

		case "hp-quiz":
			nameApk = "hp-game.apk";
			break;
		}

		AndroidPackage apk = new AndroidPackage();
		File file = new File(this.ANDROID_PACKAGE_PATH+nameApk);
		apk.setName(nameApk);
		apk.setPath(formateWebPath(nameApk));
//		apk.setVersion(nameApk); TODO

		try {
			FileTime ft = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
			apk.setBuildDate(this.formatDateTime(ft));
			
			
			apk.setSize(this.formatSize(Files.size(file.toPath())));
		} catch (IOException ex) {
			// handle exception
		}
		
		return apk;
	}

	/** 
	 * Formate le chemin web pour qu'il soit comphréensif par le client
	 * @return String
	 */
	private String formateWebPath(String nameAPK) {
		String webPath = HTTP; 
				
		try {
			webPath += Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		}
		webPath += ":"+TOMCAT_PORT;
		webPath += "/"+APK+"/"+nameAPK;
		
		return webPath;
	}
	/**
	 * Formate la date de l'apk
	 * @param fileTime
	 * @return
	 */
	private String formatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(DATE_FORMATTER);
    }

	/**
	 * Formate la size pour qu'elle soit compris facilement.
	 * @param size
	 * @return String
	 */
	private String formatSize(double size) {
		String str = "";
		if(size > MO) {
			size = size/MO;
			str = " Mo";
		} else if(size > KO) {
			size = (size/KO);
			str = " Ko";
		} else {
			str =" o";
		}
		
		return (Math.round(size * 100.0) / 100.0) + str;
	}

}
