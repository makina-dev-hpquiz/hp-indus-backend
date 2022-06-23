package com.makina.industrialisation.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makina.industrialisation.models.AndroidPackage;
import com.makina.industrialisation.services.AndroidPackageManager;

@CrossOrigin
@RestController
@RequestMapping("apks")
public class ApkController {

	Logger logger = LogManager.getLogger(ApkController.class);

	
	@Autowired
	private AndroidPackageManager androidPackagerManager;

	@GetMapping(value="/hp-core/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public AndroidPackage getLastHpCoreAPK() {
		logger.debug("Appel de l'API getLastHpCoreAPK");
		return androidPackagerManager.getHPCoreLatestAPK();
	}

	@GetMapping(value="/hp-quiz/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public AndroidPackage getLastHpQuizAPK() {
		logger.debug("Appel de l'API getLastHpQuizAPK");
		return androidPackagerManager.getHPQuizLatestAPK();
	}

	@GetMapping(value="/hp-core", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AndroidPackage> getAllHPCoreAPK(){
		logger.debug("Appel de l'API getAllHPCoreAPK");
		return androidPackagerManager.getAllHPCoreAPK();	
	}

	@GetMapping(value="/hp-quiz", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AndroidPackage> getAllHPQuizAPK(){
		logger.debug("Appel de l'API getAllHPQuizAPK");
		return androidPackagerManager.getAllHPQuizAPK();	
	}
}