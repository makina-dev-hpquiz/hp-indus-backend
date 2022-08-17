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
import com.makina.industrialisation.constants.IncidentPriority;
import com.makina.industrialisation.constants.IncidentStatus;
import com.makina.industrialisation.constants.IncidentType;
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
	
	private static final String TODO_MSG = IncidentStatus.TO_DO;
	private static final String DOING_MSG = IncidentStatus.DOING;
	private static final String DONE_MSG = IncidentStatus.DONE;	

	private static final String SCREEN_MSG = IncidentType.SCREEN;
	private static final String WORD_MSG = IncidentType.WORD;
	private static final String EVENT_MSG = IncidentType.EVENT;
	

	private static final String LOW_MSG = IncidentPriority.LOW;
	private static final String NORMAL_MSG = IncidentPriority.NORMAL;
	private static final String HIGHT_MSG = IncidentPriority.HIGHT;
	
	
	private static final String[] EMPTY_TAB = new String[0];
	private static final String EMPTY_STRING = "";
	
	@BeforeAll
	public static void setUp() {		
		i1 = new IncidentDTO("Panneau de recherche n'est pas responsive", "Description", "", "", LOW_MSG, "2022-07-28T00:00:00.000Z", SCREEN_MSG, TODO_MSG);
		i2 = new IncidentDTO("Erreur de texte : Question 145", "description", "", "", LOW_MSG, "2022-07-31T00:00:00.000Z", WORD_MSG, TODO_MSG);
		i3 = new IncidentDTO("Le boutons ne sont pas alignés", "description", "", "", NORMAL_MSG, "2022-06-15T00:00:00.000Z", SCREEN_MSG, TODO_MSG);
		i4 = new IncidentDTO("Erreur de texte : Question 146", "description", "", "", NORMAL_MSG, "2022-08-09T00:00:00.000Z", WORD_MSG, DOING_MSG);
		i5 = new IncidentDTO("L'image ne s'affiche pas correctement", "description", "", "", NORMAL_MSG, "2022-07-14T00:00:00.000Z", SCREEN_MSG, DOING_MSG);
		i6 = new IncidentDTO("L'application HpQuiz plante lorsqu'une question de type QCM est jouée.", "description", "", "", HIGHT_MSG, "2022-07-15T00:00:00.000Z", EVENT_MSG, DONE_MSG);
		i7 = new IncidentDTO("Menu principal à corriger", "description", "", "", HIGHT_MSG, "2022-07-16T00:00:00.000Z", WORD_MSG, DONE_MSG);
		
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
		
		ResponseEntity<IncidentDTO> result = this.incidentController.saveIncident(i3, null);
		i3.setId(result.getBody().getId());

		result = this.incidentController.saveIncident(i4, null);
		i4.setId(result.getBody().getId());
		result = this.incidentController.saveIncident(i5, null);
		i5.setId(result.getBody().getId());
		result = this.incidentController.saveIncident(i6, null);
		i6.setId(result.getBody().getId());
		result = this.incidentController.saveIncident(i7, null);
		i7.setId(result.getBody().getId());
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

		result2 = this.incidentController.updateIncident(i2, file3);
		assertTrue(result2.getBody().getScreenshotPath().contains(filename3));
		assertTrue(result2.getBody().getScreenshotWebPath().contains(filename3));

		result2 = this.incidentController.updateIncident(i2, null);

		assertEquals("", result2.getBody().getScreenshotPath());
		assertEquals("", result2.getBody().getScreenshotWebPath());

	}
	
	@Order(3)
	@Test
	void testGetIncidents() {
		List<IncidentDTO> incidents;
		// T1 : getIncidents : Avec tri (default) Le plus récent
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, EMPTY_STRING, EMPTY_STRING);
		assertEquals(7, incidents.size());
		assertEquals(incidents.get(0).getId(), i4.getId());
		
		// T2 : getIncidents : Avec tri inversé Le plus vieux TODO KO car pas de vrai date
		incidents = this.incidentController.getIncidents("-"+IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, EMPTY_STRING, EMPTY_STRING);
		assertEquals(7, incidents.size());
		System.out.println(incidents.get(0).getTitle() +" : "+incidents.get(0).getUpdatedAt());
		assertEquals(incidents.get(0).getId(), i3.getId());
		
		// T3 : getIncidents : Avec tri (default) + 1 status
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG}, EMPTY_STRING, EMPTY_STRING); 
		assertEquals(3, incidents.size());
		
		// T4 : getIncidents : Avec tri (default) + 2 status
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG}, EMPTY_STRING, EMPTY_STRING);
		assertEquals(5, incidents.size());
		
		// T5 : getIncidents : Avec tri (default) + 3 status
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, EMPTY_STRING, EMPTY_STRING);
		assertEquals(7, incidents.size());
		
		// T6 : getIncidents : Avec tri (default) + priorité
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, LOW_MSG, EMPTY_STRING);
		assertEquals(2, incidents.size());
		
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, NORMAL_MSG, EMPTY_STRING);
		assertEquals(3, incidents.size());

		// T7 : getIncidents : Avec tri (default) + type incident
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, EMPTY_STRING, SCREEN_MSG);
		assertEquals(3, incidents.size());
		
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, EMPTY_TAB, EMPTY_STRING, EVENT_MSG);
		assertEquals(1, incidents.size());

		// T8 : getIncidents : Avec tri (default) + mot clef
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "Erreur de texte", EMPTY_TAB, EMPTY_STRING, EMPTY_STRING);
		assertEquals(2, incidents.size());
		
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "principal", EMPTY_TAB, EMPTY_STRING, EMPTY_STRING);
		assertEquals(1, incidents.size());
		
		// T9 : getIncidents : Avec tri (default) + 2 status TODO_MSG, DOING_MSG + priorité DOWN_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG}, LOW_MSG, EMPTY_STRING);
		assertEquals(2, incidents.size());
		
		// 2 status TODO_MSG, DOING_MSG + priorité HIGHT_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG}, HIGHT_MSG, EMPTY_STRING);
		assertEquals(0, incidents.size());
		
		// T10 : getIncidents : Avec tri (default) + 2 status TODO_MSG, DOING_MSG + type incident WORD_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG}, EMPTY_STRING, WORD_MSG);
		assertEquals(2, incidents.size());
		
		// 2 status TODO_MSG, DOING_MSG + type incident EVENT_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG}, EMPTY_STRING, EVENT_MSG);
		assertEquals(0, incidents.size());
		
		// 3 status TODO_MSG, DOING_MSG DONE_MSG + type incident EVENT_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, EMPTY_STRING, EVENT_MSG);
		assertEquals(1, incidents.size());
		
		// T11 : getIncidents : Avec tri (default) + 2 status TODO_MSG, DOING_MSG + priorité DOWN_MSG + type incident SCREEN_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, LOW_MSG, SCREEN_MSG);
		assertEquals(1, incidents.size());

		// 2 status TODO_MSG, DOING_MSG + + priorité NORMAL_MSG + type incident SCREEN_MSG
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, EMPTY_STRING, new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, NORMAL_MSG, SCREEN_MSG);
		assertEquals(2, incidents.size());
		
		// T12 : getIncidents : Avec tri (default) + 2 status TODO_MSG, DOING_MSG + priorité DOWN_MSG + type incident WORD_MSG + mot clef "Erreur de texte" 
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "Erreur de texte", new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, LOW_MSG, WORD_MSG);
		assertEquals(1, incidents.size());
		assertEquals(i2.getId(), incidents.get(0).getId());

		// 2 status TODO_MSG, DOING_MSG + priorité NORMAL_MSG + type incident WORD_MSG + mot clef "Erreur de texte" 
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "Erreur de texte", new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, NORMAL_MSG, WORD_MSG);
		assertEquals(1, incidents.size());
		assertEquals(i4.getId(), incidents.get(0).getId());
		
		// 2 status TODO_MSG, DOING_MSG + priorité NORMAL_MSG + type incident WORD_MSG + mot clef "N'existe pas" 
		incidents = this.incidentController.getIncidents(IncidentControllerConstants.DEFAULT_SORT_BY, "N'existe pas", new String[]{TODO_MSG, DOING_MSG, DONE_MSG}, NORMAL_MSG, WORD_MSG);
		assertEquals(0, incidents.size());
		
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
		this.incidentController.deleteIncident(i3.getId().toString());
		this.incidentController.deleteIncident(i4.getId().toString());
		this.incidentController.deleteIncident(i5.getId().toString());
		this.incidentController.deleteIncident(i6.getId().toString());
		this.incidentController.deleteIncident(i7.getId().toString());
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
