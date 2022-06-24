package com.makina.industrialisation.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makina.industrialisation.models.AndroidPackage;

@ActiveProfiles("test")
@WebMvcTest(ApkController.class)
class ApkControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	 @Autowired
	  private ObjectMapper objectMapper;
	 
	 @MockBean
	 private ApkController apkController;
		
		@Test
		void getHPCoreTest() throws JsonProcessingException, Exception {
			List<AndroidPackage> resultAPKList = new ArrayList<>();
			resultAPKList.add(new AndroidPackage());
			resultAPKList.get(0).setName("test-hp-core-1.0.3.apk");
			resultAPKList.add(new AndroidPackage());
			resultAPKList.get(1).setName("test-hp-core-1.0.2.apk");
			resultAPKList.add(new AndroidPackage());
			resultAPKList.get(2).setName("test-hp-core-1.0.1.apk");

			List<AndroidPackage> expectedAPKList = new ArrayList<>();
			expectedAPKList.add(new AndroidPackage());
			expectedAPKList.get(0).setName("test-hp-core-1.0.3.apk");
			expectedAPKList.add(new AndroidPackage());
			expectedAPKList.get(1).setName("test-hp-core-1.0.2.apk");
			expectedAPKList.add(new AndroidPackage());
			expectedAPKList.get(2).setName("test-hp-core-1.0.1.apk");
			
			when(apkController.getAllHPCore()).thenReturn(resultAPKList);
			
			mockMvc.perform(get("/apks/hp-core/")).andExpect(status().isOk())
		    .andExpect(content().string(objectMapper.writeValueAsString(expectedAPKList).toString()));
			
		}
		
	 @Test
	  void getHPQuizLatestTest() throws Exception {
		AndroidPackage result = new AndroidPackage();
		result.setName("test-hp-quiz-latest.apk");
		result.setVersion("");
		result.setPath("http://192.168.43.20:8080/APK/test-hp-quiz-latest.apk");
		result.setSize("0 o");
		result.setBuildDateFormatted("10/06/2022 17:14");
		
		AndroidPackage expected = new AndroidPackage();
		expected.setName("test-hp-quiz-latest.apk");
		expected.setVersion("");
		expected.setPath("http://192.168.43.20:8080/APK/test-hp-quiz-latest.apk");
		expected.setSize("0 o");
		expected.setBuildDateFormatted("10/06/2022 17:14");

		when(apkController.getHpQuizLatest()).thenReturn(result);
		
		mockMvc.perform(get("/apks/hp-quiz/latest")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(expected).toString()));
	}
	    
	@Test
	void getHPQuizTest() throws JsonProcessingException, Exception {
		List<AndroidPackage> resultAPKList = new ArrayList<>();
		resultAPKList.add(new AndroidPackage());
		resultAPKList.get(0).setName("test-hp-quiz-1.0.3.apk");
		resultAPKList.add(new AndroidPackage());
		resultAPKList.get(1).setName("test-hp-quiz-1.0.2.apk");
		resultAPKList.add(new AndroidPackage());
		resultAPKList.get(2).setName("test-hp-quiz-1.0.1.apk");

		List<AndroidPackage> expectedAPKList = new ArrayList<>();
		expectedAPKList.add(new AndroidPackage());
		expectedAPKList.get(0).setName("test-hp-quiz-1.0.3.apk");
		expectedAPKList.add(new AndroidPackage());
		expectedAPKList.get(1).setName("test-hp-quiz-1.0.2.apk");
		expectedAPKList.add(new AndroidPackage());
		expectedAPKList.get(2).setName("test-hp-quiz-1.0.1.apk");
		
		when(apkController.getAllHPQuiz()).thenReturn(resultAPKList);
		
		mockMvc.perform(get("/apks/hp-quiz/")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(expectedAPKList).toString()));
		
	}
	 
	 @Test
	  void getHPCoreLatestTest() throws Exception {
		AndroidPackage result = new AndroidPackage();
		result.setName("test-hp-core-latest.apk");
		result.setVersion("");
		result.setPath("http://192.168.43.20:8080/APK/test-hp-core-latest.apk");
		result.setSize("0 o");
		result.setBuildDateFormatted("10/06/2022 17:14");
		
		AndroidPackage expected = new AndroidPackage();
		expected.setName("test-hp-core-latest.apk");
		expected.setVersion("");
		expected.setPath("http://192.168.43.20:8080/APK/test-hp-core-latest.apk");
		expected.setSize("0 o");
		expected.setBuildDateFormatted("10/06/2022 17:14");

		when(apkController.getHpCoreLatest()).thenReturn(result);
		
		mockMvc.perform(get("/apks/hp-core/latest")).andExpect(status().isOk())
	    .andExpect(content().string(objectMapper.writeValueAsString(expected).toString()));
	}
	 
}
