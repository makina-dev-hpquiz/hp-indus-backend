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
import com.makina.industrialisation.repositories.IncidentRepository;
import com.makina.industrialisation.utils.FileManager;

import annotations.com.makina.industrialisation.models.Incident_;

@Service
public class IncidentService {
	
	Logger logger = LogManager.getLogger(IncidentService.class);
	
	@Autowired
	private IncidentRepository incidentRepository;
	
	private Specification<Incident> specification; 
	
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
	public List<Incident> findAll(String sortBy, String searchBy, List<String> status, String priorityLevel, String incidentType){

		Sort sort = sortBy.contains("-") ? Sort.by(sortBy.substring(1, sortBy.length())).descending() : Sort.by(sortBy).ascending();
		this.specification = null;
		if(status.size() > 0) {
			addSpecification(hasStatus(status));
		}
		if(priorityLevel != null && !priorityLevel.equals("") ) { //TODO
			addSpecification(hasPriority(priorityLevel));
		}
		if(incidentType != null && !incidentType.equals("")) { //TODO
			addSpecification(isType(incidentType));
		}
		if(searchBy != null && !searchBy.equals("")) {
			addSpecification(likeTitle(searchBy));
		}
		return this.specification != null ? this.incidentRepository.findAll(this.specification, sort) : this.incidentRepository.findAll(sort);
	}
	
	private void addSpecification(Specification<Incident> spec){
		if(this.specification == null) {
			this.specification = Specification.where(spec);
		} else {
			this.specification = this.specification.and(spec);
		}		
	}
	
	private Specification<Incident> hasStatus(List<String> status){
		return (root, query, criteriaBuilder) -> 
		      criteriaBuilder.in(root.get(Incident_.STATUS)).value(status);
	}
	
	private Specification<Incident> hasPriority(String priority){
		return (root, query, criteriaBuilder) -> 
	      criteriaBuilder.equal(root.get(Incident_.PRIORITY), priority);
	}
	
	private Specification<Incident> isType(String type){
		return (root, query, criteriaBuilder) -> 
	      criteriaBuilder.equal(root.get(Incident_.TYPE), type);
	}
	
	private Specification<Incident> likeTitle(String title){
		return (root, query, criteriaBuilder) -> 
		criteriaBuilder.like(root.get(Incident_.TITLE), "%"+title+"%");
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
