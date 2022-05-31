package com.makina.industrialisation.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("apks")
public class ApkController {
    	
	
	@GetMapping(value="/last", produces = MediaType.APPLICATION_JSON_VALUE)
    private String getLastAPK() {
		System.out.print("Envoyé");
        return "{\"response\":\"../../assets/apk/app-debug.apk\"}";
    }
}