package com.makina.industrialisation.extractors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
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
	void testExtract() throws IOException {
		File file = new File(this.configuration.getPath()+"test.txt");
		String millis = (""+FileTime.from(Instant.now()).toMillis());
		file.createNewFile();
		
		
		FileTime ft = dateExtractor.extract(file);
		File fileNotExist = new File(this.configuration.getPath()+"unknow.apk").getAbsoluteFile();
		FileTime ftNull = dateExtractor.extract(fileNotExist);
		
	    file.delete();
		
	    //Suppression du dernier caractères milliseconde pour qu'il n'y ai pas de décalage
		long millisLong = Long.parseLong(millis.substring(0, millis.length()-2));
		String ftMillis = ""+ft.toMillis();
	    long ftMillisLong =  Long.parseLong(ftMillis.substring(0, ftMillis.length()-2));
		
		
		assertEquals(millisLong, ftMillisLong); 
	    assertEquals(null, ftNull);
	    
	}
}
