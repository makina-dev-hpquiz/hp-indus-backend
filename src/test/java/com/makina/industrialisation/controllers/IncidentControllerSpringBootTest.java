package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.makina.industrialisation.configuration.TomcatConfiguration;
import com.makina.industrialisation.constants.IncidentControllerConstants;
import com.makina.industrialisation.dto.IncidentDTO;
import com.makina.industrialisation.models.Incident;


/**
 * Test les méthodes privées de IncidentController
 *
 */
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
class IncidentControllerSpringBootTest {

	@Autowired
	private IncidentController incidentController;
	
	@Autowired
	private TomcatConfiguration configuration;
	
	private static String filename1 = "test1.txt";
	private static String filename2 = "test2.txt";
	private static String filename3 = "test3.txt";

	private static MultipartFile file1;
	private static MultipartFile file2;
	private static MultipartFile file3;
	private static MultipartFile nullFile;
	private static MultipartFile emptyFile;
	
	
	private static String path = "test.txt";
	private static IncidentDTO i1;
	private static IncidentDTO i2;
	private static IncidentDTO i3;
	private static IncidentDTO i4;
	private static IncidentDTO i5;
	private static IncidentDTO i6;
	private static IncidentDTO i7;
	private static IncidentDTO i8;
	private static IncidentDTO i9;
	private static IncidentDTO i10;

	@BeforeAll
	public static void setUp() {		
		i1 = new IncidentDTO("test1", "description 1", "", "", "basse", "01/07/2022", "interface", "En attente");
		i2 = new IncidentDTO("test2", "description 2", "", "", "normal", "05/07/2022", "interface", "En attente");
		
		byte[] content = null;
		String contentType = "text/plain";
						
		file1 = new MockMultipartFile(filename1, filename1, contentType, content);
		file2 = new MockMultipartFile(filename2, filename2, contentType, content);
		file3 = new MockMultipartFile(filename3, filename3, contentType, content);
		nullFile = null;
		emptyFile =  new MockMultipartFile(path, null, contentType, content);
	}
	
	@Order(1)
	@Test
	void testSaveIncident(){
		ResponseEntity<IncidentDTO> result1 = this.incidentController.saveIncident(i1, null);
		IncidentDTO result2 = this.incidentController.saveIncident(i2, file2).getBody();
		
		assertNotNull(result1.getBody().getId());
		assertEquals("", result1.getBody().getScreenshotPath());
		i1.setId(result1.getBody().getId());
		
		assertEquals(HttpStatus.CREATED, result1.getStatusCode());
		assertNotNull(result2.getId());
		i2.setId(result2.getId());

		assertTrue(result2.getScreenshotPath().contains(filename2));
		assertTrue(result2.getScreenshotWebPath().contains(filename2));
	}
	
	@Order(2)
	@Test
	void testUpdateIncident() {
		
		String newTitle = "Test 1 Mise à jour";
		i1.setTitle(newTitle);
		ResponseEntity<IncidentDTO> result1 = this.incidentController.updateIncident(i1, null);

		assertEquals(i1.getId(), result1.getBody().getId());
		assertEquals(newTitle, result1.getBody().getTitle());
		assertEquals(HttpStatus.OK, result1.getStatusCode());
								
		ResponseEntity<IncidentDTO> result2 = this.incidentController.updateIncident(i2, file3);
		
		assertTrue(result2.getBody().getScreenshotPath().contains(filename3));
		assertTrue(result2.getBody().getScreenshotWebPath().contains(filename3));

		result2 = this.incidentController.updateIncident(result2.getBody(), file3);
		assertTrue(result2.getBody().getScreenshotPath().contains(filename3));
		assertTrue(result2.getBody().getScreenshotWebPath().contains(filename3));

		result2 = this.incidentController.updateIncident(i2, null);

		assertEquals("", result2.getBody().getScreenshotPath());
		assertEquals("", result2.getBody().getScreenshotWebPath());

	}
	
	@Order(3)
	@Test
	void testGetIncidents() {
		
		// T1 : getIncidents : Avec tri (default)
		// T2 : getIncidents : Avec tri inversé
		// T3 : getIncidents : Avec tri (default) + 1 status
		// T4 : getIncidents : Avec tri (default) + 2 status
		// T5 : getIncidents : Avec tri (default) + 3 status
		// T6 : getIncidents : Avec tri (default) + priorité
		// T7 : getIncidents : Avec tri (default) + type incident
		// T8 : getIncidents : Avec tri (default) + mot clef
		// T9 : getIncidents : Avec tri (default) + 2 status + priorité
		// T10 : getIncidents : Avec tri (default) + 2 status + incident
		// T11 : getIncidents : Avec tri (default) + 2 status + priorité + incident + mot clef
		
		
		
		
		List<IncidentDTO> incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "");
		assertEquals(2, incidents.size());
		
	}
	
	@Order(4)
	@Test
	void testGetExistIncident() {
		ResponseEntity<IncidentDTO> resultIncident = this.incidentController.getIncident(i1.getId().toString());
		assertEquals(i1.getId(), resultIncident.getBody().getId());
		assertEquals(HttpStatus.OK, resultIncident.getStatusCode());				
	}
	
	@Order(5)
	@Test
	void testDeleteIncident() {
		assertEquals(HttpStatus.ACCEPTED ,  this.incidentController.deleteIncident(i1.getId().toString()).getStatusCode());
		this.incidentController.deleteIncident(i2.getId().toString());
	}
	
	@Order(6)
	@Test
	void testGetNotExistIncident() {
		ResponseEntity<IncidentDTO> resultIncident = this.incidentController.getIncident(i1.getId().toString());
		assertEquals(null, resultIncident.getBody());
		assertEquals(HttpStatus.NOT_FOUND, resultIncident.getStatusCode());
	}
	
	@Test
	void testSavePicture() throws UnknownHostException {
		path = configuration.getImgPath()+filename1;
		
		Incident incident = new Incident();
		ReflectionTestUtils.invokeMethod(incidentController, "savePicture",  file1, incident);
		assertTrue(new File(path).exists());
		assertEquals(path, incident.getScreenshotPath());
		
		String ipAddress = InetAddress.getLocalHost().getHostAddress();
		assertEquals("http://"+ipAddress+":"+configuration.getPort()+"/images/"+filename1, incident.getScreenshotWebPath());
	}
		
	@Test
	void testScreenshotIsPresent() {
		Boolean result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent",  nullFile);
		assertFalse(result);
		
		result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent", file1);
		assertTrue(result);
		
		
		result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent", emptyFile);
		assertFalse(result);
	}
	
	@Test
	void testGetOriginalFilename() {
		String result = ReflectionTestUtils.invokeMethod(incidentController, "getOriginalFilename", file1);
		assertEquals(filename1, result);

		result = ReflectionTestUtils.invokeMethod(incidentController, "getOriginalFilename", emptyFile);
		assertEquals("", result);
		
		MockMultipartFile nullFile = null;
		assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(incidentController, "getOriginalFilename", nullFile));
	}
	
	@AfterAll
	static void removeFile() {		
		new File(path).delete();
	}
	

}
