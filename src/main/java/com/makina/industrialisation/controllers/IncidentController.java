package com.makina.industrialisation.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.makina.industrialisation.configuration.TomcatConfiguration;
import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.services.IncidentService;
import com.makina.industrialisation.utils.FileManager;

@CrossOrigin
@RestController
@RequestMapping("incidents")
public class IncidentController {


	Logger logger = LogManager.getLogger(IncidentController.class);
	
	@Autowired
	private IncidentService incidentService;
	
	@Autowired 
	TomcatConfiguration tomcatConfiguration;
	
	private final String WEB_PATH = "http:\\\\192.168.1.11:8080\\images\\";
	
	@PutMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public Incident updateIncident(@ModelAttribute("incident") Incident incident, @RequestParam(value = "screenshot", required = false) MultipartFile screenshot,
			ModelMap modelMap) {
		
		Incident oldIncident = incidentService.findById(incident.getId());

		if(screenshot.getOriginalFilename()!= null && !screenshot.getOriginalFilename().equals(FileManager.getName(oldIncident.getScreenshotPath()))) {
			FileManager.deleteFile(oldIncident.getScreenshotPath());
			oldIncident.setScreenshotPath("");
			oldIncident.setScreenshotWebPath("");
			savePicture(screenshot, incident);
		}
		
		return incidentService.saveIncident(incident);
		
	}
	
	@PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public Incident saveIncident(@ModelAttribute("incident") Incident incident, @RequestParam(value = "screenshot", required = false) MultipartFile screenshot,
			ModelMap modelMap) {
		logger.debug("Appel de l'API saveIncident");
				
		savePicture(screenshot, incident);
		return incidentService.saveIncident(incident);
	}
	
	
	private void savePicture(MultipartFile screenshot, Incident incident) {
		if(screenshot != null) {
			FileManager.saveFile(screenshot,  tomcatConfiguration.getImgPath());
			incident.setScreenshotPath( tomcatConfiguration.getImgPath()+screenshot.getOriginalFilename());
			incident.setScreenshotWebPath(WEB_PATH+screenshot.getOriginalFilename()); //TODO ReUseWebPathFormatter
		}
	}
	
	@GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Incident> getIncidents() {
		logger.debug("Appel de l'API getIncidents");
		return incidentService.findAll();
	}
	

	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Incident> getIncident(@PathVariable("id") String id) {
		logger.debug("Appel de l'API getIncident : {}", id);
		Incident incident = incidentService.findById(id);
		
		return new ResponseEntity<>(incident, incident != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);		
	}
	

	@DeleteMapping(value="/delete/{id}")
	public void deleteIncident(@PathVariable("id") String id) {
		incidentService.deleteIncidentById(id);
	}

}
