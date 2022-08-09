package com.makina.industrialisation.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.Incident;

import annotations.com.makina.industrialisation.models.Incident_;

@Service
public class IncidentSpecifications {
	
	public Specification<Incident> hasStatus(List<String> status){
		return (root, query, criteriaBuilder) -> 
		      criteriaBuilder.in(root.get(Incident_.STATUS)).value(status);
	}
	
	public Specification<Incident> hasPriority(String priority){
		return (root, query, criteriaBuilder) -> 
	      criteriaBuilder.equal(root.get(Incident_.PRIORITY), priority);
	}
	
	public Specification<Incident> isType(String type){
		return (root, query, criteriaBuilder) -> 
	      criteriaBuilder.equal(root.get(Incident_.TYPE), type);
	}
	
	public Specification<Incident> likeTitle(String title){
		return (root, query, criteriaBuilder) -> 
		criteriaBuilder.like(root.get(Incident_.TITLE), "%"+title+"%");
	}
	
	public Specification<Incident> addSpecification(Specification<Incident> specification, Specification<Incident> newSpec){
		if(specification == null) {
			specification = Specification.where(newSpec);
		} else {
			specification = specification.and(newSpec);
		}
		
		return specification;
	}
}
