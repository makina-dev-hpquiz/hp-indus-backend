package com.makina.industrialisation.services;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.Incident;
import com.makina.industrialisation.models.IncidentFilter;
import com.makina.industrialisation.repositories.IncidentRepository;
import com.makina.industrialisation.specifications.IncidentSpecifications;
import com.makina.industrialisation.utils.FileManager;

@Service
public class IncidentService {
	
	Logger logger = LogManager.getLogger(IncidentService.class);
	
	@Autowired
	private IncidentRepository incidentRepository;
	
	@Autowired
	private IncidentSpecifications incidentSpecification;
		
	/**
	 * Sauvegarde un incident en base de données
	 * @param incident
	 */
	public Incident saveIncident(Incident incident) {
		return this.incidentRepository.save(incident);
	}
	
	/**
	 * Retourne tous les incidents
	 * @param priorityLevel 
	 * @param incidentType 
	 * @return List<Incident>
	 */
	public List<Incident> findAll(IncidentFilter incidentFilter){
//		String sortBy, String searchBy, List<String> status, String priorityLevel, String incidentType
		Sort sort = incidentFilter.getSortBy().contains("-") ? Sort.by(incidentFilter.getSortBy().substring(1, incidentFilter.getSortBy().length())).descending() : Sort.by(incidentFilter.getSortBy()).ascending();
		Specification<Incident> spec = null;
		if(incidentFilter.hasValidStatus()) {
			spec = incidentSpecification.addSpecification(spec, incidentSpecification.hasStatus(incidentFilter.getStatus()));
		}
		if(incidentFilter.hasValidPriority()) {
			spec = incidentSpecification.addSpecification(spec, incidentSpecification.hasPriority(incidentFilter.getPriorityLevel()));
		}
		if(incidentFilter.hasValidIncidentType()) { //TODO
			spec = incidentSpecification.addSpecification(spec, incidentSpecification.isType(incidentFilter.getIncidentType()));
		}
		if(incidentFilter.hasValidSearchBy()) {
			spec = incidentSpecification.addSpecification(spec, incidentSpecification.likeTitle(incidentFilter.getSearchBy()));
		}
		return spec != null ? this.incidentRepository.findAll(spec, sort) : this.incidentRepository.findAll(sort);
	}
		
	/**
	 * Retourne un incident dont l'UUID est passé en paramètre
	 * @param UUID id
	 * @return INCIDENT
	 */
	public Incident findById(UUID id) {
		return this.findById(id.toString());
	}
	
	/**
	 * Retourne un incident dont l'UUID au format string est passé en paramètre
	 * @param String id
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
			if(incident.getScreenshotPath() != null && !incident.getScreenshotPath().equals("")) {
				FileManager.deleteFile(incident.getScreenshotPath());
			}
			this.incidentRepository.delete(this.findById(id));
		}
	}	
}
