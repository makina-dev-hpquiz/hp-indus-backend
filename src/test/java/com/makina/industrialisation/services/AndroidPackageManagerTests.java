package com.makina.industrialisation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.models.AndroidPackage;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
class AndroidPackageManagerTests {

	@Autowired
	AndroidPackageManager androidPackageManager;
		
	@Autowired
	AndroidPackageManagerConfiguration configuration;
		
	/**
	 * Test la méthode AndroidPackageManager.getAndroidPackageInformation()
	 * Avec des données contenues dans le repertoire de test AndroidPackageManagerTestRepository
	 * Le fichier recherché est Fichier 1.apk (associé à la valeur HpCoreLatest en mode test)
	 */
	@Test
	@Order(1)
	void testGetAndroidPackageInformation() {
		AndroidPackage apk = ReflectionTestUtils.invokeMethod(androidPackageManager, "getAndroidPackageInformation", configuration.getHpQuizLatest());
		AndroidPackage apk2 = ReflectionTestUtils.invokeMethod(androidPackageManager, "getAndroidPackageInformation", "file");
		
		assertEquals(configuration.getHpQuizLatest(), apk.getName()) ;
		assertEquals(new AndroidPackage().getName(), apk2.getName());
		
	}
	
	/**
	 * Test la méthode AndroidPackageManager.getAllAPK()
	 * Avec des données contenues dans le repertoire de test AndroidPackageManagerTestRepository
	 * @return 
	 */
	@Test
	@Order(2)
	void testGetAllAPK() {
		ArrayList<AndroidPackage> filesList = ReflectionTestUtils.invokeMethod(androidPackageManager, "getAllAPK", configuration.getHpCorePartialName());
		assertEquals(2, filesList.size());
	}
		
	/**
	 * Test la méthode AndroidPackageManager.sortByDate()
	 */
	@Test
	@Order(3)
	void testSortByDate() {

	    List<AndroidPackage> apkList = new ArrayList<AndroidPackage>();
	    
	    AndroidPackage apk1 = new AndroidPackage();
	    AndroidPackage apk2 = new AndroidPackage();
	    AndroidPackage apk3 = new AndroidPackage();
	    
	    apk1.setName("Fichier 1.apk");
	    apk1.setBuildDateFormatted("07/06/2022 17:00"); 
	    apk1.setBuildDate(FileTime.from(Instant.parse("2022-06-07T17:00:00.00Z")));
 
	    apk2.setName("Fichier 2.apk");
	    apk2.setBuildDateFormatted("10/06/2022 17:14");
	    apk2.setBuildDate(FileTime.from(Instant.parse("2022-06-10T17:14:00.00Z")));

	    apk3.setName("Fichier 3.apk");
	    apk3.setBuildDateFormatted("29/05/2022 17:00");
	    apk3.setBuildDate(FileTime.from(Instant.parse("2022-05-29T17:00:00.00Z")));

	    apkList.add(apk1);
	    apkList.add(apk2);
	    apkList.add(apk3);
	    
	    ArrayList<AndroidPackage> sortedAndroidPackageList = ReflectionTestUtils.invokeMethod(androidPackageManager, "sortByDate",  apkList);
	
		assertEquals("Fichier 2.apk", sortedAndroidPackageList.get(0).getName());
		assertEquals("Fichier 1.apk", sortedAndroidPackageList.get(1).getName());
		assertEquals("Fichier 3.apk", sortedAndroidPackageList.get(2).getName());
		
	}
	
	@Test
	@Order(4)
	void testDeleteDoublon() {
		 List<AndroidPackage> apkList = new ArrayList<AndroidPackage>();
		    
		    AndroidPackage apk1 = new AndroidPackage();
		    AndroidPackage apk2 = new AndroidPackage();
		    AndroidPackage apk3 = new AndroidPackage();
		    
		    apk1.setName("Fichier_latest.apk");
		    apk1.setBuildDateFormatted("07/06/2022 17:00"); 
		    apk1.setBuildDate(FileTime.from(Instant.parse("2022-06-07T17:00:00.00Z")));
	 
		    apk2.setName("Fichier_1.0.2.apk");
		    apk2.setBuildDateFormatted("07/06/2022 17:00");
		    apk2.setBuildDate(FileTime.from(Instant.parse("2022-06-07T17:00:00.00Z")));

		    apk3.setName("Fichier_1.0.1.apk");
		    apk3.setBuildDateFormatted("29/05/2022 17:00");
		    apk3.setBuildDate(FileTime.from(Instant.parse("2022-05-29T17:00:00.00Z")));

		    apkList.add(apk1);
		    apkList.add(apk2);
		    apkList.add(apk3);
		    
		    ArrayList<AndroidPackage> androidPackageListWithoutDoublon = ReflectionTestUtils.invokeMethod(androidPackageManager, "deleteDoublon",  apkList);

		    assertEquals(2, androidPackageListWithoutDoublon.size());
		    assertEquals(apk2, androidPackageListWithoutDoublon.get(0));
		    assertEquals(apk3, androidPackageListWithoutDoublon.get(1));
		    
	}

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
	    
		String version1 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk1);
		String version2 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk2);
		String version3 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk3);
		String version4 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk4);
		String version5 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk5);
		String version6 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", apk6);
	
		assertEquals("1.0.1", version1);
		assertEquals("1.0.2", version2);
		assertEquals("1.0.2", version3);
		assertEquals("1.0.2", version4);
		assertNotEquals("1.0.2", version5);
		assertNotEquals("1.0.2", version6);
	}
	
	@Test
	void testExtractVersionWithString() {
		
		String nameApk1 = "hp-core-1.0.1.apk";
		String nameApk2 = "hp-core-1.0.2.apk";
		String nameApk3 = "hp-core-latest.apk";
		String nameApk4 = "hp-core.apk";
		
		String version1 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", nameApk1);
		String version2 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", nameApk2);
		String version3 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", nameApk3);
		String version4 = ReflectionTestUtils.invokeMethod(androidPackageManager, "extractVersion", nameApk4);

		

		assertEquals("1.0.1", version1);
		assertEquals("1.0.2", version2);
		assertEquals("", version3);
		assertEquals("", version4);
	}
	
}
