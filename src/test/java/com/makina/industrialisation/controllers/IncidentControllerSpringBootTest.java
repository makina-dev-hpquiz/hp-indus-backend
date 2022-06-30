package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@ActiveProfiles("test")
@SpringBootTest
class IncidentControllerSpringBootTest {

	@Autowired
	private IncidentController incidentController;
	
	@Autowired
	private TomcatConfiguration configuration;
	
	private static String filename = "test.txt";
	private static String path = "test.txt";

	@Test
	void testSavePicture() throws UnknownHostException {
		path = configuration.getImgPath()+filename;
		byte[] content = null;
		String contentType = "text/plain";
		MultipartFile file = new MockMultipartFile(filename,
				filename, contentType, content);
		
		Incident incident = new Incident();
		ReflectionTestUtils.invokeMethod(incidentController, "savePicture",  file, incident);
		assertTrue(new File(path).exists());
		assertEquals(path, incident.getScreenshotPath());
		
		String ipAddress = InetAddress.getLocalHost().getHostAddress();
		assertEquals("http://"+ipAddress+":"+configuration.getPort()+"/images/test.txt", incident.getScreenshotWebPath());
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
	
	@AfterAll
	static void removeFile() {		
		File f = new File(path+filename);
		f.delete();
	}
	

}
