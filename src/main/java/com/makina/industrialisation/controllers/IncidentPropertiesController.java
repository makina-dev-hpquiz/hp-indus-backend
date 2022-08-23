package com.makina.industrialisation.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makina.industrialisation.constants.IncidentPriority;
import com.makina.industrialisation.constants.IncidentStatus;
import com.makina.industrialisation.constants.IncidentType;
import com.makina.industrialisation.models.IncidentProperty;

@CrossOrigin
@RestController
@RequestMapping("incidents")
public class IncidentPropertiesController {


	Logger logger = LogManager.getLogger(IncidentPropertiesController.class);
	
	@GetMapping(value="/types", produces = MediaType.APPLICATION_JSON_VALUE)
	public IncidentProperty getTypes() {
		logger.debug("Appel de l'API getTypes");
		return IncidentType.getIncidentProperty();
	}

	@GetMapping(value="/priorities", produces = MediaType.APPLICATION_JSON_VALUE)
	public IncidentProperty getPriorities() {
		logger.debug("Appel de l'API getPriorities");
		return IncidentPriority.getIncidentProperty();
	}

	@GetMapping(value="/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public IncidentProperty getStatus() {
		logger.debug("Appel de l'API getStatus");
		return IncidentStatus.getIncidentProperty();
	}	
}
