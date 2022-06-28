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
	public AndroidPackage getHpCoreLatest() {
		logger.debug("Appel de l'API getHpCoreLatest");
		return androidPackagerManager.getHPCoreLatest();
	}

	@GetMapping(value="/hp-quiz/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public AndroidPackage getHpQuizLatest() {
		logger.debug("Appel de l'API getHpQuizLatest");
		return androidPackagerManager.getHPQuizLatest();
	}

	@GetMapping(value="/hp-core", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AndroidPackage> getAllHPCore(){
		logger.debug("Appel de l'API getAllHPCore");
		return androidPackagerManager.getAllHPCore();	
	}

	@GetMapping(value="/hp-quiz", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AndroidPackage> getAllHPQuiz(){
		logger.debug("Appel de l'API getAllHPQuiz");
		return androidPackagerManager.getAllHPQuiz();	
	}
}