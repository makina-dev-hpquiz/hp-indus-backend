package com.makina.industrialisation.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.repositories.IncidentRepository;

@Service
public class IncidentService {
	
	Logger logger = LogManager.getLogger(IncidentService.class);
	
	@Autowired
	private IncidentRepository incidentRepository;
	
	/**
	 * Sauvegarde un incident en base de données
	 * @param incident
	 */
	public Incident saveIncident(Incident incident) {
		return this.incidentRepository.save(incident);
	}
	
	/**
	 * Retourne tous les incidents
	 * @return List<Incident>
	 */
	public List<Incident> findAll(){
		return this.incidentRepository.findAll();
	}
	
	/**
	 * Retourne un incident dont l'UUID est passé en paramètre
	 * @param UUID id
	 * @return INCIDENT
	 */
	public Incident findById(String id) {
		try{
		    Optional<Incident> incident = this.incidentRepository.findById(UUID.fromString(id));

			if(incident.isPresent()) {
				return incident.get();
			} else {
				logger.error("L'incident demandé n'a pas pu être retourné : {}", id);
				return null;
			}
		} catch (IllegalArgumentException exception){
			logger.error("L'ID fourni n'est pas dans un format valide : {}", id);
			return null;
		}
		
	}
	
	/**
	 * Supprime un incident en fournissant un ID, supprime également l'image associé
	 * @param UUID id
	 */
	public void deleteIncidentById(String id) {
		Incident incident = this.findById(id);
		if(incident != null) {
			// TODO Implementation remove image
			if(incident.getScreenshotPath() != null) {
				try {
					Files.delete(Path.of(incident.getScreenshotPath()));
				} catch (IOException e) {
					logger.error("Erreur lors de la suppression de l\'image {} associé à l'incident {}, {}", incident.getScreenshotPath(), incident.getId(), e.getMessage());
				}
			}
			this.incidentRepository.delete(this.findById(id));
		}
	}

}
