package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
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
	private static String path = "test.txt";
	private static Incident i1;
	private static Incident i2;

	@Test
	void testSavePicture() throws UnknownHostException {
		path = configuration.getImgPath()+filename1;
		byte[] content = null;
		String contentType = "text/plain";
		MultipartFile file = new MockMultipartFile(filename1,
				filename1, contentType, content);
		
		Incident incident = new Incident();
		ReflectionTestUtils.invokeMethod(incidentController, "savePicture",  file, incident);
		assertTrue(new File(path).exists());
		assertEquals(path, incident.getScreenshotPath());
		
		String ipAddress = InetAddress.getLocalHost().getHostAddress();
		assertEquals("http://"+ipAddress+":"+configuration.getPort()+"/images/"+filename1, incident.getScreenshotWebPath());
	}
	
	
	
	@Test
	void testScreenshotIsPresent() {
		MultipartFile nullFile = null;
		Boolean result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent",  nullFile);
		assertFalse(result);
		
		String filename = "test.txt";
		byte[] content = null;
		String contentType = "text/plain";
						
		MultipartFile file = new MockMultipartFile(filename,
				filename, contentType, content);
		
		result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent", file);
		assertTrue(result);
		
						
		file = new MockMultipartFile(filename,
				"", contentType, content);
		
		result = ReflectionTestUtils.invokeMethod(incidentController, "screenshotIsPresent", file);
		assertFalse(result);
	}
	
	@Test
	void testGetOriginalFilename() {
		String filename = "test.txt";
		byte[] content = null;
		String contentType = "text/plain";
		
		MultipartFile file = new MockMultipartFile(filename,
				filename, contentType, content);
		String result = ReflectionTestUtils.invokeMethod(incidentController, "getOriginalFilename", file);
		assertEquals(filename, result);

		MultipartFile emptyFile = new MockMultipartFile("test", "", "", content);
		result = ReflectionTestUtils.invokeMethod(incidentController, "getOriginalFilename", emptyFile);
		assertEquals("", result);
	}
	
	@Order(1)
	@Test
	void testSaveIncident(){
		byte[] content = null;
		String contentType = "text/plain";
						
		MultipartFile file = new MockMultipartFile(filename2,
				filename2, contentType, content);
		
		i1 = new Incident("test1", "description 1", "", "", "mineur", "01/07/2022", "interface");
		i2 = new Incident("test2", "description 2", "", "", "normal", "05/07/2022", "interface");
		ResponseEntity<Incident> result1 = this.incidentController.saveIncident(i1, null);
		Incident result2 = this.incidentController.saveIncident(i2, file).getBody();
		
		assertEquals(i1.getId(), result1.getBody().getId());
		assertEquals("", result1.getBody().getScreenshotPath());
		assertEquals(HttpStatus.CREATED, result1.getStatusCode());
		assertEquals(i2.getId(), result2.getId());


		assertTrue(result2.getScreenshotPath().contains(filename2));
		assertTrue(result2.getScreenshotWebPath().contains(filename2));

	}
	
	@Order(2)
	@Test
	void testUpdateIncident() {
		
		String newTitle = "Test 1 Mise à jour";
		i1.setTitle(newTitle);
		ResponseEntity<Incident> result1 = this.incidentController.updateIncident(i1, null);

		assertEquals(i1.getId(), result1.getBody().getId());
		assertEquals(newTitle, result1.getBody().getTitle());
		assertEquals(HttpStatus.OK, result1.getStatusCode());
		
		byte[] content = null;
		String contentType = "text/plain";
						
		MultipartFile file = new MockMultipartFile(filename3,
				filename3, contentType, content);
		ResponseEntity<Incident> result2 = this.incidentController.updateIncident(i2, file);
		
		assertTrue(result2.getBody().getScreenshotPath().contains(filename3));
		assertTrue(result2.getBody().getScreenshotWebPath().contains(filename3));

		result2 = this.incidentController.updateIncident(i2, file);
		assertTrue(result2.getBody().getScreenshotPath().contains(filename3));
		assertTrue(result2.getBody().getScreenshotWebPath().contains(filename3));

		result2 = this.incidentController.updateIncident(i2, null);

		assertEquals("", result2.getBody().getScreenshotPath());
		assertEquals("", result2.getBody().getScreenshotWebPath());

	}
	
	@Order(3)
	@Test
	void testGetIncidents() {
		List<Incident> incidents = this.incidentController.getIncidents();
		assertEquals(2, incidents.size());
		
	}
	
	@Order(4)
	@Test
	void testGetExistIncident() {
		ResponseEntity<Incident> resultIncident = this.incidentController.getIncident(i1.getId().toString());
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
		ResponseEntity<Incident> resultIncident = this.incidentController.getIncident(i1.getId().toString());
		assertEquals(null, resultIncident.getBody());
		assertEquals(HttpStatus.NOT_FOUND, resultIncident.getStatusCode());
		
	}
	
	@AfterAll
	static void removeFile() {		
		new File(path).delete();
	}
	

}
