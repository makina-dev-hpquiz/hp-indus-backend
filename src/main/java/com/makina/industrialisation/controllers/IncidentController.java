package com.makina.industrialisation.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.services.BugsManager;
import com.makina.industrialisation.services.IncidentService;

@CrossOrigin
@RestController
@RequestMapping("bugs")
public class IncidentController {

	@Autowired
	private BugsManager bugsManager;
	@Autowired
	private IncidentService incidentService;
	
//	private final String PATH  = "C:\\Users\\Utilisateur\\Documents\\workspace\\dev_mobile\\images\\";
	private final String PATH  = "C:\\apache-tomcat-9.0.55\\webapps\\images\\";
	private final String WEB_PATH = "http:\\\\192.168.1.11:8080\\images\\";
	
	
	//private void UploadBugsScreen(@ModelAttribute MultipartFile file, ModelMap modelMap) {}
	@PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	private String UploadBugsScreen(@ModelAttribute("incident") Incident incident, @RequestParam(value = "screenshot", required = false) MultipartFile screenshot,
			ModelMap modelMap) {
		System.out.println("UPLOAD EN COURS");

		if(screenshot != null) {
			try {
				screenshot.transferTo(new File(PATH+screenshot.getOriginalFilename()));
				
				System.out.println(PATH+screenshot.getOriginalFilename());
				incident.setScreenshotPath(PATH+screenshot.getOriginalFilename());
				incident.setScreenshotWebPath(WEB_PATH+screenshot.getOriginalFilename());

			} catch (IllegalStateException e) {
				System.out.print("IllegalStateException");
				e.printStackTrace(); //TODO
			} catch (IOException e) {
				System.out.print("IOException");
				e.printStackTrace(); //TODO
			}
		}
		
		incidentService.saveIncident(incident);
		bugsManager.incidents.add(incident);

		System.out.print("UPLOAD OK");
		return "OK!";
	}
	
	@GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
	private List<Incident> getIncidents() {
		
		List<Incident> incidents = incidentService.findAllIncident();
		System.out.println("Nombre d'Incidents :"+incidents.size());

		return incidents;
	}
	

	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private Incident getIncident(@PathVariable("id") String id) {
		return incidentService.findIncidentById(id);
	}
	

	@DeleteMapping(value="/delete/{id}")
	private void deleteIncident(@PathVariable("id") String id) {
		incidentService.deleteIncidentById(id);
	}

}
