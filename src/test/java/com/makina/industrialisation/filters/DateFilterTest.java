package com.makina.industrialisation.filters;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.junit.jupiter.api.Test;

class DateFilterTest {

	@Test
	void isApproximateDateTest() {
		FileTime ft1 = FileTime.from(Instant.parse("2022-06-10T17:00:00.00Z"));	
		FileTime ft2 = FileTime.from(Instant.parse("2022-06-10T17:00:00.00Z"));	
		
		FileTime ft3 = FileTime.from(Instant.parse("2022-06-10T17:01:00.00Z"));
		FileTime ft4 = FileTime.from(Instant.parse("2022-06-10T17:02:00.00Z"));	
		FileTime ft5 = FileTime.from(Instant.parse("2022-06-10T17:03:00.00Z"));	
		FileTime ft6 = FileTime.from(Instant.parse("2022-06-10T17:10:00.00Z"));	
		
		assertTrue(DateFilter.isApproximateDate(ft1, ft2));
		
		assertTrue(DateFilter.isApproximateDate(ft1, ft3));
		assertTrue(DateFilter.isApproximateDate(ft1, ft4));
		assertFalse(DateFilter.isApproximateDate(ft1, ft5));
		assertFalse(DateFilter.isApproximateDate(ft1, ft6));		
	}

}
