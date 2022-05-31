package com.makina.industrialisation.services;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.repositories.IncidentRepository;

@Service
public class IncidentService {
	
	
	@Autowired
	private IncidentRepository incidentRepository;
	
	/**
	 * Sauvegarde un incident en base de données
	 * @param incident
	 */
	public void saveIncident(Incident incident) {
		this.incidentRepository.save(incident);
	}
	
	/**
	 * Retourne tous les incidents
	 * @return
	 */
	public List<Incident> findAllIncident(){
		return this.incidentRepository.findAll();
	}
	
	/**
	 * Retourne un incident dont l'UUID est passé en paramètre
	 * @param UUID id
	 * @return INCIDENT
	 */
	public Incident findIncidentById(String id) {
		return this.incidentRepository.findById(UUID.fromString(id)).get();
	}
	
	/**
	 * Supprime un incident en fournissant un ID, supprime également l'image associé
	 * @param UUID id
	 */
	public void deleteIncidentById(String id) {
		Incident incident = this.findIncidentById(id);
		if(incident != null) {
			//Implementation remove image
			File f = new File(incident.getScreenshotPath());
			if(f.exists()) {
				f.delete();
			}
			f = null;
			this.incidentRepository.delete(this.findIncidentById(id));
		}
	}

}
