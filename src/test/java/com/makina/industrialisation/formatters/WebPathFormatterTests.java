package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebPathFormatterTests {

	@Autowired
	WebPathFormatter webPathFormatter;
	
	@Test
	void testformat() throws UnknownHostException{
		String nameApk = "hp-core.apk";
		
		String expected = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/APK/"+nameApk;
		String result = this.webPathFormatter.format(nameApk);
		
		assertEquals(expected, result);
	}
}
