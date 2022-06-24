package com.makina.industrialisation.extractors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.models.AndroidPackage;

@SpringBootTest
@ActiveProfiles("test")
class VersionExtractorTest {
	
	@Autowired
	private VersionExtractor versionExtractor;
	
	
	@Test
	void testExtractVersionWithAPK() {

	    AndroidPackage apk1 = new AndroidPackage();
	    apk1.setName("test-hp-core-1.0.1.apk");
	    apk1.setBuildDateFormatted("07/06/2022 17:00"); 
	    apk1.setBuildDate(FileTime.from(Instant.parse("2022-06-07T17:00:00.00Z")));
	    

	    AndroidPackage apk2 = new AndroidPackage();
	    apk2.setName("test-hp-core-latest.apk");
	    apk2.setBuildDateFormatted("10/06/2022 13:00"); 
	    apk2.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:00:00.00Z")));
	    

	    AndroidPackage apk3 = new AndroidPackage();
	    apk3.setName("test-hp-core-latest.apk");
	    apk3.setBuildDateFormatted("10/06/2022 13:01"); 
	    apk3.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:01:00.00Z")));
	    

	    AndroidPackage apk4 = new AndroidPackage();
	    apk4.setName("test-hp-core-latest.apk");
	    apk4.setBuildDateFormatted("10/06/2022 13:02"); 
	    apk4.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:02:00.00Z")));
	    

	    AndroidPackage apk5 = new AndroidPackage();
	    apk5.setName("test-hp-core-latest.apk");
	    apk5.setBuildDateFormatted("10/06/2022 13:03"); 
	    apk5.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:03:00.00Z")));
	    
	    AndroidPackage apk6 = new AndroidPackage();
	    apk6.setName("test-hp-core-latest.apk");
	    apk6.setBuildDateFormatted("10/06/2022 13:10"); 
	    apk6.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:10:00.00Z")));
	    
	    this.versionExtractor.extract(apk6);
	    
		List<AndroidPackage> apkList = new ArrayList<>();
		
		AndroidPackage Apk7 = new AndroidPackage();
		Apk7.setName("test-hp-core-1.0.2.apk");
		Apk7.setBuildDate(FileTime.from(Instant.parse("2022-06-10T13:00:00.00Z")));
		apkList.add(Apk7);
		AndroidPackage Apk8 = new AndroidPackage();
		Apk8.setName("test-hp-core-1.0.12.apk");
		Apk8.setBuildDate(FileTime.from(Instant.parse("2022-06-05T11:10:00.00Z")));
		apkList.add(Apk8);
		AndroidPackage Apk9 = new AndroidPackage();
		Apk9.setName("test-hp-core-1.0.1.apk");
		Apk9.setBuildDate(FileTime.from(Instant.parse("2022-05-28T18:10:00.00Z")));
		apkList.add(Apk9);
					
		String version1 = this.versionExtractor.extract(apk1, apkList);
		String version2 = this.versionExtractor.extract(apk2, apkList);
		String version3 = this.versionExtractor.extract(apk3, apkList);
		String version4 = this.versionExtractor.extract(apk4, apkList);
		String version5 = this.versionExtractor.extract(apk5, apkList);
		String version6 = this.versionExtractor.extract(apk6, apkList);
	
		assertEquals("1.0.1", version1);
		assertEquals("1.0.2", version2);
		assertEquals("1.0.2", version3);
		assertEquals("1.0.2", version4);
		assertNotEquals("1.0.2", version5);
		assertNotEquals("1.0.2", version6);
	}
	
	@Test
	void testExtractVersionWithString() {		
		AndroidPackage apk1 = new AndroidPackage();
		apk1.setName("hp-core-1.0.1.apk");

		AndroidPackage apk2 = new AndroidPackage();
		apk2.setName("hp-core-1.0.2.apk");

		AndroidPackage apk3 = new AndroidPackage();
		apk3.setName("hp-core-latest.apk");

		AndroidPackage apk4 = new AndroidPackage();
		apk4.setName("hp-core.apk");
		
		String version1 = this.versionExtractor.extract(apk1);
		String version2 = this.versionExtractor.extract(apk2);
		String version3 = this.versionExtractor.extract(apk3);
		String version4 = this.versionExtractor.extract(apk4);
		
		assertEquals("1.0.1", version1);
		assertEquals("1.0.2", version2);
		assertEquals("", version3);
		assertEquals("", version4);
	}

}
