package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.models.IncidentPriority;
import com.makina.industrialisation.models.IncidentStatus;
import com.makina.industrialisation.models.IncidentType;

@ActiveProfiles("test")
@SpringBootTest
class IncidentPropertiesControllerTests {

	@Autowired
	IncidentPropertiesController incidentPController;

	@Test
	void testGetIncidentType() {
		List<String> types = this.incidentPController.getTypes();
		assertEquals(IncidentType.get(), types);
	}
	
	@Test
	void testGetIncidentSearchType() {		
		List<String> types = this.incidentPController.getIncidentSearchType();
		List<String> list = new ArrayList<>(); 
		list.add(IncidentType.NONE_SEARCH);
		list.addAll(IncidentType.get());

		assertEquals(list, types);
	}
	
	@Test
	void testGetIncidentPriorities() {
		List<String> priorities = this.incidentPController.getPriorities();
		assertEquals(IncidentPriority.get(), priorities);
	}
	
	@Test
	void testGetIncidentSearchPriorities() {		
		List<String> priorities = this.incidentPController.getSearchPriorities();
		List<String> list = new ArrayList<>(); 
		list.add(IncidentPriority.NONE_SEARCH);
		list.addAll(IncidentPriority.get());

		assertEquals(list, priorities);
	}
	
	@Test
	void testGetIncidentStatus() {
		List<String> status = this.incidentPController.getStatus();
		assertEquals(IncidentStatus.get(), status);
	}
}
