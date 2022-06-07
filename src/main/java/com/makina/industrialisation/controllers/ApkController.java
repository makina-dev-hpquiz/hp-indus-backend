package com.makina.industrialisation.controllers;

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
    	
	@Autowired
	private AndroidPackageManager androidPackagerManager;
	
	@GetMapping(value="/lastHPCoreAPK", produces = MediaType.APPLICATION_JSON_VALUE)
    private AndroidPackage getLastHpCoreAPK() {
		return androidPackagerManager.getAndroidPackageInformation("hp-core");
    }
	
	@GetMapping(value="/lastHPQuizAPK", produces = MediaType.APPLICATION_JSON_VALUE)
    private AndroidPackage getLastHpQuizAPK() {
		return androidPackagerManager.getAndroidPackageInformation("hp-quiz");
    }
	
}