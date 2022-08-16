package com.makina.industrialisation.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.makina.industrialisation.configuration.TomcatConfiguration;
import com.makina.industrialisation.constants.IncidentControllerConstants;
import com.makina.industrialisation.dto.IncidentDTO;
import com.makina.industrialisation.formatters.ImgWebPathFormatter;
import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.models.IncidentFilter;
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
	
	ModelMapper modelMapper = new ModelMapper();
	
	@PutMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IncidentDTO> updateIncident(IncidentDTO incidentDTO, MultipartFile file) {
		Incident incident = modelMapper.map(incidentDTO, Incident.class);
		logger.debug("Appel de l'API updateIncident INCIDENT : {}", incident.getId());

		Incident oldIncident = incidentService.findById(incident.getId());

		if(screenshotIsPresent(file)) {
			String originalFilename = this.getOriginalFilename(file);
			if(!originalFilename.equals(FileManager.getName(oldIncident.getScreenshotPath()))) {
				FileManager.deleteFile(oldIncident.getScreenshotPath());
				savePicture(file, incident);
			} else {
				incident.setScreenshotPath(oldIncident.getScreenshotPath());
				incident.setScreenshotWebPath(oldIncident.getScreenshotWebPath());
			}
		} else {
			if(incident.getScreenshotPath().equals("")) {
				incident.setScreenshotPath("");
				incident.setScreenshotWebPath("");
				FileManager.deleteFile(oldIncident.getScreenshotPath());
			}
		}
		incidentDTO = modelMapper.map(incidentService.saveIncident(incident), IncidentDTO.class);
		return new ResponseEntity<>(incidentDTO, incidentDTO != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IncidentDTO> saveIncident(IncidentDTO incidentDTO, MultipartFile file) {
		Incident incident = modelMapper.map(incidentDTO, Incident.class);
		logger.debug("Appel de l'API saveIncident");

		savePicture(file, incident);
		incidentDTO = modelMapper.map(incidentService.saveIncident(incident), IncidentDTO.class);
		return new ResponseEntity<>(incidentDTO, incidentDTO != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IncidentDTO> getIncidents(
			@RequestParam(value="sort", defaultValue = IncidentControllerConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value="q", required = false) String searchBy,
			@RequestParam(value="status", required = false) String[] status, 
			@RequestParam(value="priority", required = false) String priorityLevel,
			@RequestParam(value="type", required = false) String incidentType
			){

		logger.debug("getIncidents : {}, {}, {}, {}, {}", sortBy, searchBy, status, priorityLevel, incidentType);
		
		Stream<Incident> incidents = incidentService.findAll(new IncidentFilter(sortBy, searchBy, status, priorityLevel, incidentType)).stream();
		return incidents.map(u -> modelMapper.map(u, IncidentDTO.class))
                .collect(Collectors.toList());
		
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IncidentDTO> getIncident(@PathVariable("id") String id) {
		logger.debug("Appel de l'API getIncident : {}", id);
				
		Incident i = incidentService.findById(id);
		IncidentDTO incident = i != null ? modelMapper.map(i, IncidentDTO.class) : null;
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
				logger.debug("La valeur MultipartFile.getOriginalFilename est {}", originalFilename);
				return originalFilename;
			} else {
				throw new NullPointerException("La valeur MultipartFile.getOriginalFilename est null.");
			}
		} else {
			throw new NullPointerException("Le MultipartFile est null.");
		}
	}
}
