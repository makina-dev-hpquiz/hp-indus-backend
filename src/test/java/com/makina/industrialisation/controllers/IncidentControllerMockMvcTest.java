package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makina.industrialisation.constants.IncidentControllerConstants;
import com.makina.industrialisation.dto.IncidentDTO;

/**
 * Test les méthodes EndPoint de IncidentController
 * @author PC Valentin
 *
 */
@ActiveProfiles("test")
@WebMvcTest(IncidentController.class)
public class IncidentControllerMockMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	 
	@MockBean
	private IncidentController incidentController;
			
	private static IncidentDTO i1;
	private static IncidentDTO i2;
	private static IncidentDTO i3;
	
	private static MockMultipartFile file;
	
	@BeforeAll
	public static void setUp() {
		i1 = new IncidentDTO();
		i1.setId(UUID.fromString("d7cd23b8-991c-470f-ac63-d8fb106f391e"));
		i1.setTitle("Test1");
		i1.setDescription("Description de Test1");
		i1.setScreenshotPath("http://localhost:8080/images/test1.jpg");
		i1.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test1.jpg");
		i1.setPriority("Normal");
		i1.setUpdatedAt("2022-05-31T13:26:10.144Z");
		i1.setType("Interface");
		
		i2 = new IncidentDTO();
		i2.setId(UUID.fromString("d7cd23b8-991c-470f-ac63-d8fb106f3915"));
		i2.setTitle("Test2");
		i2.setDescription("Description de Test2");
		i2.setScreenshotPath("http://localhost:8080/images/test2.jpg");
		i2.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test2.jpg");
		i2.setPriority("Normal");
		i2.setUpdatedAt("2022-06-02T15:26:10.144Z");
		i2.setType("Interface");
		
		i3 = new IncidentDTO();
		i3.setId(UUID.fromString("d7cd23b8-991c-470f-ac63-d8fb106f3933"));
		i3.setTitle("Test3");
		i3.setDescription("Description de Test3");
		i3.setScreenshotPath("http://localhost:8080/images/test3.jpg");
		i3.setScreenshotWebPath("C:\\apache-tomcat-9.0.55\\webapps\\images\\test3.jpg");
		i3.setPriority("Normal");
		i3.setUpdatedAt("2022-06-06T15:26:10.144Z");
		i3.setType("Interface");
		
		String filename = "test.txt";
		byte[] content = null;
		String contentType = "text/plain";
						
		file = new MockMultipartFile(filename,
				filename, contentType, content);
	}
	
	@Test
	void testGetIncidents() throws JsonProcessingException, Exception{
		
		when(incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "", null, "", "")).thenReturn(new ArrayList<IncidentDTO>());
		
		mockMvc.perform(get("/incidents")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(new ArrayList<IncidentDTO>()).toString()));
				
		assertEquals("[]", objectMapper.writeValueAsString(new ArrayList<IncidentDTO>()).toString());
		
		List<IncidentDTO> resultIncidentsList = new ArrayList<>();
		resultIncidentsList.add(i1);
		resultIncidentsList.add(i2);
		resultIncidentsList.add(i3);
		
		List<IncidentDTO> expectedIncidentsList = new ArrayList<>();
		expectedIncidentsList.add(i1);
		expectedIncidentsList.add(i2);
		expectedIncidentsList.add(i3);
		
		String qValue = "test";
		String[] statusValue = new String[] {"En cours"};
		
		when(incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, qValue, statusValue, qValue, qValue)).thenReturn(resultIncidentsList);

		mockMvc.perform(get("/incidents?sort="+IncidentControllerConstants.DEFAULT_SORT_BY+"&q="+qValue+"&status=En cours&priority="+qValue+"&type="+qValue)).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(expectedIncidentsList).toString()));
	}
		
	@Test
	void testGetIncident() throws JsonProcessingException, Exception{
		when(incidentController.getIncident(i1.getId().toString())).thenReturn(new ResponseEntity<>(i1, HttpStatus.OK));
		mockMvc.perform(get("/incidents/"+i1.getId())).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(i1).toString()));
		
		
		when(incidentController.getIncident("3")).thenReturn(new ResponseEntity<>(new IncidentDTO(), HttpStatus.NOT_FOUND));
		mockMvc.perform(get("/incidents/3")).andExpect(status().isNotFound());	    
	}
	
	@Test
	void testSaveIncident() throws JsonProcessingException, Exception{
		when(incidentController.saveIncident(any(), any())).thenReturn(new ResponseEntity<>(i1, HttpStatus.CREATED));

		mockMvc.perform(MockMvcRequestBuilders.multipart("/incidents")
				.file(file)
				.content(objectMapper.writeValueAsString(i1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
	    .andExpect(content().string(objectMapper.writeValueAsString(i1)));	
	}
	
	@Test
	void testUpdateIncident() throws JsonProcessingException, Exception {
		when(incidentController.updateIncident(any(), any())).thenReturn(new ResponseEntity<>(i1, HttpStatus.CREATED));

		MockMultipartHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.multipart("/incidents");
	    builder.with(new RequestPostProcessor() {
	        @Override
	        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
	            request.setMethod("PUT");
	            return request;
	        }
	    });
		
		mockMvc.perform(builder
				.file(file)
				.content(objectMapper.writeValueAsString(i1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
	    .andExpect(content().string(objectMapper.writeValueAsString(i1)));	
	}
	
	@Test
	void testDeleteIncident() throws Exception{
		when(incidentController.deleteIncident(i1.getId().toString())).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED));
		mockMvc.perform(delete("/incidents/"+i1.getId())).andExpect(status().isAccepted());
	}
}
