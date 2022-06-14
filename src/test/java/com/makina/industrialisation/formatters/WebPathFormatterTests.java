package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;

@SpringBootTest
class WebPathFormatterTests {

	@Autowired
	WebPathFormatter webPathFormatter;
	

	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
	@Test
	void testformat() throws UnknownHostException{
		String nameApk = "hp-core.apk";
		
		StringBuilder sb = new StringBuilder();
		sb.append(configuration.getProtocol());
		sb.append(Inet4Address.getLocalHost().getHostAddress());
		sb.append(":");
		sb.append(configuration.getPort());
		sb.append("/");
		sb.append(configuration.getFolderName());
		sb.append("/");
		sb.append(nameApk);
		String expected = sb.toString();
		
		String result = this.webPathFormatter.format(nameApk);
		
		assertEquals(expected, result);
	}
}
