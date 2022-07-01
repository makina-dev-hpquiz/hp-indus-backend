package com.makina.industrialisation.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.makina.industrialisation.configuration.TomcatConfiguration;
import com.makina.industrialisation.formatters.ImgWebPathFormatter;
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

	@Autowired
	ImgWebPathFormatter webPathFormatter;
	
	@PutMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Incident> updateIncident(Incident incident, MultipartFile screenshot) {
		logger.debug("Appel de l'API updateIncident INCIDENT : {}", incident.getId());

		Incident oldIncident = incidentService.findById(incident.getId());

		if(screenshotIsPresent(screenshot)) {
			String originalFilename = this.getOriginalFilename(screenshot);
			if(!originalFilename.equals(FileManager.getName(oldIncident.getScreenshotPath()))) {
				FileManager.deleteFile(oldIncident.getScreenshotPath());
				savePicture(screenshot, incident);
			}
		} else {
			if(!oldIncident.getScreenshotPath().equals("")) {
				incident.setScreenshotPath("");
				incident.setScreenshotWebPath("");
				FileManager.deleteFile(oldIncident.getScreenshotPath());
			}
		}
		incident =  incidentService.saveIncident(incident);
		return new ResponseEntity<>(incident, incident != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Incident> saveIncident(Incident incident, MultipartFile screenshot) {
		logger.debug("Appel de l'API saveIncident INCIDENT : {}", incident.getId());

		savePicture(screenshot, incident);
		incident =  incidentService.saveIncident(incident);
		return new ResponseEntity<>(incident, incident != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);		
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
	

	@DeleteMapping(value="/{id}")
	public ResponseEntity<HttpStatus> deleteIncident(@PathVariable("id") String id) {
		logger.debug("Appel de l'API deleteIncident : {}", id);
		incidentService.deleteIncidentById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	/**
	 * Effectue les différentes actions d'enregistrement d'une image :
	 * Sauvegarde en dur
	 * Ajout des nouvelles informations de chemin d'accès dans l'objet incident
	 * @param screenshot
	 * @param incident
	 */
	private void savePicture(MultipartFile screenshot, Incident incident) {
		if(this.screenshotIsPresent(screenshot)) {
			FileManager.saveFile(screenshot, tomcatConfiguration.getImgPath());
			incident.setScreenshotPath( tomcatConfiguration.getImgPath()+screenshot.getOriginalFilename());
			incident.setScreenshotWebPath(webPathFormatter.format(screenshot.getOriginalFilename()));
		}
	}
	
	/**
	 * Indique si une image est bien présente
	 * @param screenshot
	 * @return boolean
	 */
	private boolean screenshotIsPresent(MultipartFile screenshot) {
		if(screenshot != null) {	
			return !"".equals(getOriginalFilename(screenshot));
		}
		return false;
	}
	
	/**
	 * Retourne le MultipartFile.getOriginalFilename
	 * Lance un NullPointerException si la valeur est null.
	 * @param screenshot
	 * @return String
	 */
	private String getOriginalFilename(MultipartFile screenshot) {
		if(screenshot!= null ) {
			String originalFilename = screenshot.getOriginalFilename();
			if(originalFilename != null) {
				return originalFilename;
			} else {
				throw new NullPointerException("La valeur MultipartFile.getOriginalFilename est null.");
			}
		} else {
			throw new NullPointerException("Le MultipartFile est null.");
		}
	}
}
