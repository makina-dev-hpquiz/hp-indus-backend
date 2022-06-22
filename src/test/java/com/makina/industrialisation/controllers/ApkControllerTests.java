package com.makina.industrialisation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.models.AndroidPackage;

@SpringBootTest
@ActiveProfiles("test")
class ApkControllerTests {
	
	@Autowired
	ApkController apkController;
	
	@Test
	void testGetLastHpCoreAPK(){
		
		AndroidPackage expected = new AndroidPackage();
		expected.setName("test-hp-quiz-latest.apk");
		expected.setVersion("");
		expected.setPath("http://192.168.43.20:8080/APK/test-hp-quiz-latest.apk");
		expected.setSize("0 o");
		expected.setBuildDateFormatted("10/06/2022 17:14");
		
		AndroidPackage result = apkController.getLastHpQuizAPK();
		
		assertEquals(expected.getName(), result.getName());
		assertEquals(expected.getVersion(), result.getVersion());
		assertEquals(expected.getPath(), result.getPath());
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getBuildDateFormatted(), result.getBuildDateFormatted());
	}

}
