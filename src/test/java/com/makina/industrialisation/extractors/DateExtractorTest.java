package com.makina.industrialisation.extractors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;

@SpringBootTest
@ActiveProfiles("test")
class DateExtractorTest {

	@Autowired
	DateExtractor dateExtractor;
	
	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
	@Test
	void testExtract() {
		File file = new File(this.configuration.getPath()+this.configuration.getHpQuizLatest()).getAbsoluteFile();
		FileTime ft = dateExtractor.extract(file);
		File fileNotExist = new File(this.configuration.getPath()+"unknow.apk").getAbsoluteFile();
		FileTime ftNull = dateExtractor.extract(fileNotExist);
		
		long millis = FileTime.from(Instant.parse("2022-06-10T13:14:48.4986769Z")).toMillis();
		
		assertEquals(millis, ft.toMillis());
	    assertEquals(null, ftNull);
	}
}
