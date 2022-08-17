package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.constants.IncidentPriority;
import com.makina.industrialisation.constants.IncidentStatus;
import com.makina.industrialisation.constants.IncidentType;
import com.makina.industrialisation.models.IncidentProperty;

@ActiveProfiles("test")
@SpringBootTest
class IncidentPropertiesControllerTests {

	@Autowired
	IncidentPropertiesController incidentPController;

	@Test
	void testGetIncidentType() {
		IncidentProperty types = this.incidentPController.getTypes();
		assertEquals(IncidentType.get(), types.getProperties());
	}
	
	@Test
	void testGetIncidentPriorities() {
		IncidentProperty priorities = this.incidentPController.getPriorities();
		assertEquals(IncidentPriority.get(), priorities.getProperties());
	}
	
	
	@Test
	void testGetIncidentStatus() {
		IncidentProperty status = this.incidentPController.getStatus();
		assertEquals(IncidentStatus.get(), status.getProperties());
	}
	
}
