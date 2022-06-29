package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class ImgWebPathFormatterTests {

	@Autowired
	ImgWebPathFormatter webPathFormatter;
	
	@Test
	void testformat() throws UnknownHostException{
		String nameFile = "test1.png";

		String expected = "http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/images/"+nameFile;				
		String result = this.webPathFormatter.format(nameFile);
		
		assertEquals(expected, result);
	}
	
	@Test
	void testGetFolderName() {
		String folder = ReflectionTestUtils.invokeMethod(webPathFormatter, "getFolderName");
		assertEquals("images", folder);
	}
}
