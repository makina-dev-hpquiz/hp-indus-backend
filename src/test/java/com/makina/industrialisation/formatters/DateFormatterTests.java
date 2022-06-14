package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DateFormatterTests {

	@Autowired
	DateFormatter dateformatter;

	@Test
	void testFormat() throws ParseException {	    
		String expected = "07/06/2022 17:00";
		
		Date date = new SimpleDateFormat("dd/MM/yy HH:mm").parse(expected);
		String result = dateformatter.format(FileTime.fromMillis(date.getTime()));
		
		assertEquals(expected, result);
	}
}
