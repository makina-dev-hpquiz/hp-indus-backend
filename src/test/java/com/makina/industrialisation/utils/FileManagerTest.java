package com.makina.industrialisation.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.configuration.TomcatConfiguration;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
class FileManagerTest {

	@Autowired
	AndroidPackageManagerConfiguration configuration;
	

	@Autowired
	TomcatConfiguration tomcatConfiguration;
	
	
	@Order(1)
	@Test
	void testSaveFile() {
		String filename = "test.txt";
		String filePath = tomcatConfiguration.getImgPath()+filename;
		String contentType = "text/plain";
		byte[] content = null;
				
		FileManager.saveFile(new MockMultipartFile(filename,
				filename, contentType, content),  tomcatConfiguration.getImgPath());
		
		assertTrue(new File(filePath).exists());
	}
	
	@Order(2)
	@Test
	void testGetName() {
		assertEquals("test-hp-quiz-latest.apk", FileManager.getName(configuration.getPath()+configuration.getHpQuizLatest()));
		assertEquals("", FileManager.getName("TEST.txt"));
	}

	@Order(3)
	@Test
	void testDeleteFile() {
		String filePath = tomcatConfiguration.getImgPath()+"test.txt";
		FileManager.deleteFile(filePath);
		assertFalse(new File(filePath).exists());
	}
}
