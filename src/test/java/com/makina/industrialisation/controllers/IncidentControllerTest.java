package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makina.industrialisation.models.Incident;

@ActiveProfiles("test")
@WebMvcTest(IncidentController.class)
public class IncidentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	 
	@MockBean
	private IncidentController incidentController;
	
	
	private static Incident i1;
	private static Incident i2;
	private static Incident i3;
	
	@BeforeAll
	public static void setUp() {
		i1 = new Incident();
		i1.setTitle("Test1");
		i1.setDescription("Description de Test1");
		i1.setScreenshotPath("http://localhost:8080/images/test1.jpg");
		i1.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test1.jpg");
		i1.setPriority("Normal");
		i1.setDate("2022-05-31T13:26:10.144Z");
		i1.setType("Interface");
		
		i2 = new Incident();
		i2.setTitle("Test2");
		i2.setDescription("Description de Test2");
		i2.setScreenshotPath("http://localhost:8080/images/test2.jpg");
		i2.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test2.jpg");
		i2.setPriority("Normal");
		i2.setDate("2022-06-02T15:26:10.144Z");
		i2.setType("Interface");
		
		i3 = new Incident();
		i3.setTitle("Test3");
		i3.setDescription("Description de Test3");
		i3.setScreenshotPath("http://localhost:8080/images/test3.jpg");
		i3.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test3.jpg");
		i3.setPriority("Normal");
		i3.setDate("2022-06-06T15:26:10.144Z");
		i3.setType("Interface");
	}
	
	@Test
	void testGetIncidents() throws JsonProcessingException, Exception{
		
		when(incidentController.getIncidents()).thenReturn(new ArrayList<Incident>());
		
		mockMvc.perform(get("/incidents")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(new ArrayList<Incident>()).toString()));
				
		assertEquals("[]", objectMapper.writeValueAsString(new ArrayList<Incident>()).toString());
		
		List<Incident> resultIncidentsList = new ArrayList<>();
		resultIncidentsList.add(i1);
		resultIncidentsList.add(i2);
		resultIncidentsList.add(i3);
		
		List<Incident> expectedIncidentsList = new ArrayList<>();
		expectedIncidentsList.add(i1);
		expectedIncidentsList.add(i2);
		expectedIncidentsList.add(i3);
		
		
		when(incidentController.getIncidents()).thenReturn(resultIncidentsList);
		
		mockMvc.perform(get("/incidents")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(expectedIncidentsList).toString()));
	}
		
	@Test
	void testGetIncident() throws JsonProcessingException, Exception{
		when(incidentController.getIncident(i1.getId().toString())).thenReturn(new ResponseEntity<>(i1, HttpStatus.OK));
		mockMvc.perform(get("/incidents/"+i1.getId())).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(i1).toString()));
		
		
		when(incidentController.getIncident("3")).thenReturn(new ResponseEntity<>(new Incident(), HttpStatus.NOT_FOUND));
		mockMvc.perform(get("/incidents/3")).andExpect(status().isNotFound());	    
	}
	
	@Test
	void testUploadBugsScreen(){
		
	}
	
	@Test
	void testDeleteIncident(){
		
	}
}
