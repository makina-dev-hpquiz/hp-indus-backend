package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class ApkWebPathFormatterTests {

	@Autowired
	ApkWebPathFormatter webPathFormatter;
		
	@Test
	void testformat() throws UnknownHostException{
		
		
		String nameApk = "hp-core.apk";

		String expected = "http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/APK/"+nameApk;				
		String result = this.webPathFormatter.format(nameApk);
		
		assertEquals(expected, result);
	}
	
	@Test
	void testGetFolderName() {
		String folderName = ReflectionTestUtils.invokeMethod(webPathFormatter, "getFolderName");
		assertEquals("APK", folderName);
	}
}
