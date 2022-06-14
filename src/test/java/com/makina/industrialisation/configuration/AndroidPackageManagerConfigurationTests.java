package com.makina.industrialisation.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AndroidPackageManagerConfigurationTests {
	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
	@Test
	void testGetPath(){
		String result = configuration.getPath();
		assertEquals("src/test/resources/AndroidPackageManagerTestRepository/", result);
	}
	
	@Test
	void testGetHpCoreLatest(){
		String result = configuration.getHpCoreLatest();
		assertEquals("Fichier 1.txt", result);
		
	}
}
