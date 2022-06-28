package com.makina.industrialisation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.models.Incident;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
class IncidentServiceTest {
	
	@Autowired
	private IncidentService incidentService;
	
	private static Incident i1;
	private static Incident i2;
	
	
	@Order(1)
	@Test
	void testFindAllIncidentWithZeroIncidentsInDB(){
		List<Incident> incidents = incidentService.findAll();
		assertEquals(new ArrayList<Incident>(), incidents);
	}
	
	
	@Order(2)
	@Test
	void testSaveIncident() {
		i1 = new Incident();
		i1.setTitle("Test1");
		i1.setDescription("Description de Test1");
		i1.setScreenshotPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test1.jpg");
		i1.setScreenshotWebPath("http://localhost:8080/images/test1.jpg");
		i1.setPriority("Normal");
		i1.setDate("2022-05-31T13:26:10.144Z");
		i1.setType("Interface");
		
		i2 = new Incident();
		i2.setTitle("Test2 sans image");
		i2.setDescription("Description de Test2");
		i2.setPriority("Normal");
		i2.setDate("2022-06-02T15:26:10.144Z");
		i2.setType("Interface");
		
		Incident resultIncident1 = incidentService.saveIncident(i1);
		Incident resultIncident2 = incidentService.saveIncident(i2);
		
		assertEquals(i1.getId(), resultIncident1.getId());
		assertEquals(i2.getId(), resultIncident2.getId());
	}
	
	@Order(3)
	@Test
	void testFindAllIncident(){
		List<Incident> incidents = incidentService.findAll();

		assertEquals(2, incidents.size());
		
		Incident incident = incidents.stream().filter(i -> i.getId().toString().equals(i1.getId().toString())).collect(Collectors.toList()).get(0);
		assertEquals(i1.getId(), incident.getId()); 
		
		incident = incidents.stream().filter(i -> i.getId().toString().equals(i2.getId().toString())).collect(Collectors.toList()).get(0);
		assertEquals(i2.getId(), incident.getId()); 

	}
	
	@Order(4)
	@Test
	void testFindIncident() {
		
		Incident incident = incidentService.findById(i1.getId().toString());
		assertEquals(i1.getId(), incident.getId());
		
		Incident incidentNotExist = incidentService.findById("123e4567-e89b-12d3-a456-556642440000");
		assertEquals(null, incidentNotExist);
		
		Incident incidentNotExistWithBadIdFormat = incidentService.findById("123");
		assertEquals(null, incidentNotExistWithBadIdFormat);
	}
	
	@Order(5)
	@Test
	void testDeleteIncident() {
		incidentService.deleteIncidentById(i1.getId().toString());
		Incident incident = incidentService.findById(i1.getId().toString());
		assertNull(incident);
		

		incidentService.deleteIncidentById(i2.getId().toString());
		Incident incident2 = incidentService.findById(i2.getId().toString());
		assertNull(incident2);
	}
	
	

}
