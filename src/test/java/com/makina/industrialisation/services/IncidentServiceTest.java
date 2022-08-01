package com.makina.industrialisation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.dto.IncidentDTO;
import com.makina.industrialisation.models.Incident;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
class IncidentServiceTest {
	
	@Autowired
	private IncidentService incidentService;
	
	private static Incident i1;
	private static Incident i2;
	private static Incident i3;

	static ModelMapper modelMapper;
	
	@BeforeAll
	public static void setUp() {
		modelMapper = new ModelMapper();
		
		IncidentDTO i1DTO = new IncidentDTO();
		i1DTO.setTitle("Test1");
		i1DTO.setDescription("Description de Test1");
		i1DTO.setScreenshotPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test1.jpg");
		i1DTO.setScreenshotWebPath("http://localhost:8080/images/test1.jpg");
		i1DTO.setPriority("Normal");
		i1DTO.setDate("2022-05-31T13:26:10.144Z");
		i1DTO.setType("Interface");
		
		IncidentDTO i2DTO = new IncidentDTO();
		i2DTO.setTitle("Test2 sans image");
		i2DTO.setDescription("Description de Test2");
		i2DTO.setPriority("Normal");
		i2DTO.setDate("2022-06-02T15:26:10.144Z");
		i2DTO.setType("Interface");
		
		IncidentDTO i3DTO = new IncidentDTO();
		i3DTO.setTitle("Test3");
		i3DTO.setDescription("Description de Test3");
		i3DTO.setScreenshotPath("");
		i3DTO.setScreenshotWebPath("");
		i3DTO.setPriority("Normal");
		i3DTO.setDate("2022-05-31T13:26:10.144Z");
		i3DTO.setType("Interface");
		
		i1 = modelMapper.map(i1DTO, Incident.class);
		i2 = modelMapper.map(i2DTO, Incident.class);
		i3 = modelMapper.map(i3DTO, Incident.class);
	}
	
	
	@Order(1)
	@Test
	void testFindAllIncidentWithZeroIncidentsInDB(){
		List<Incident> incidents = incidentService.findAll();
		assertEquals(new ArrayList<Incident>(), incidents);
	}
	
	
	@Order(2)
	@Test
	void testSaveIncident() {
		Incident resultIncident1 = incidentService.saveIncident(i1);
		Incident resultIncident2 = incidentService.saveIncident(i2);
		Incident resultIncident3 = incidentService.saveIncident(i3);
		
		assertEquals(i1.getId(), resultIncident1.getId());
		assertEquals(i2.getId(), resultIncident2.getId());
		assertEquals(i3.getId(), resultIncident3.getId());
	}
	
	@Order(3)
	@Test
	void testFindAllIncident(){
		List<Incident> incidents = incidentService.findAll();

		assertEquals(3, incidents.size());
		
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
		
		incidentService.deleteIncidentById(i3.getId().toString());
		Incident incident3 = incidentService.findById(i3.getId().toString());
		assertNull(incident3);
	}
	
	

}
