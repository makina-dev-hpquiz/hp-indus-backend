package com.makina.industrialisation.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makina.industrialisation.models.IncidentPriority;
import com.makina.industrialisation.models.IncidentStatus;
import com.makina.industrialisation.models.IncidentType;

@CrossOrigin
@RestController
@RequestMapping("incidents")
public class IncidentPropertiesController {


	@GetMapping(value="/types", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getTypes() {
		return IncidentType.get();
	}

	@GetMapping(value="/search-types", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getIncidentSearchType() {
		return IncidentType.getSearch();
	}

	@GetMapping(value="/priorities", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getPriorities() {
		return IncidentPriority.get();
	}


	@GetMapping(value="/search-priorities", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getSearchPriorities() {
		return IncidentPriority.getSearch();
	}

	@GetMapping(value="/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getStatus() {
		return IncidentStatus.get();
	}	
}
