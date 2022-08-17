package com.makina.industrialisation.models;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.makina.industrialisation.constants.IncidentControllerConstants;
import com.makina.industrialisation.constants.IncidentPriority;
import com.makina.industrialisation.constants.IncidentStatus;
import com.makina.industrialisation.constants.IncidentType;

public class IncidentFilterTest {

	static IncidentFilter emptyIncidentFilter;
	static IncidentFilter nullIncidentFilter;
	static IncidentFilter badIncidentFilter;
	static IncidentFilter uppercaseIncidentFilter;
	static IncidentFilter goodIncidentFilter;
	
	
	@BeforeAll
	public static void setUp() {

		emptyIncidentFilter = new IncidentFilter("", "", null, "", "");
		nullIncidentFilter = new IncidentFilter(null, null, null, null, null);
		badIncidentFilter = new IncidentFilter("sortby", "", new String[0], "priority", "type");
		goodIncidentFilter = new IncidentFilter(IncidentControllerConstants.DEFAULT_SORT_BY, "text", new String[] {IncidentStatus.DOING}, IncidentPriority.LOW, IncidentType.SCREEN);
		
		uppercaseIncidentFilter = new IncidentFilter(IncidentControllerConstants.DEFAULT_SORT_BY.toUpperCase(),
				"text".toUpperCase(),
				new String[] {IncidentStatus.DOING.toUpperCase()},
				IncidentPriority.LOW.toUpperCase(),
				IncidentType.SCREEN.toUpperCase());		
	}
	
	@Test
	void testHasValidSortBy() {
		assertFalse(emptyIncidentFilter.hasValidSortBy());
		assertFalse(nullIncidentFilter.hasValidSortBy());
		assertFalse(badIncidentFilter.hasValidSortBy());
		assertTrue(goodIncidentFilter.hasValidSortBy());
		
		goodIncidentFilter.setSortBy("-"+IncidentControllerConstants.DEFAULT_SORT_BY);
		assertTrue(goodIncidentFilter.hasValidSortBy());
		assertFalse(uppercaseIncidentFilter.hasValidSortBy());
	}
	
	@Test
	void testHasValidSearchBy(){
		assertFalse(emptyIncidentFilter.hasValidSearchBy());
		assertFalse(nullIncidentFilter.hasValidSearchBy());
		assertFalse(badIncidentFilter.hasValidSearchBy());
		assertTrue(goodIncidentFilter.hasValidSearchBy());
		assertTrue(uppercaseIncidentFilter.hasValidSearchBy());
	}
	
	@Test
	void testHasValidStatus(){
		assertFalse(emptyIncidentFilter.hasValidStatus());
		assertFalse(nullIncidentFilter.hasValidStatus());
		assertFalse(badIncidentFilter.hasValidStatus());
		assertTrue(goodIncidentFilter.hasValidStatus());
		
		goodIncidentFilter.setStatus(IncidentStatus.get());
		assertTrue(goodIncidentFilter.hasValidStatus());
		assertFalse(uppercaseIncidentFilter.hasValidStatus());
	}
	
	@Test
	void testHasValidPriority(){
		assertFalse(emptyIncidentFilter.hasValidPriority());
		assertFalse(nullIncidentFilter.hasValidPriority());
		assertFalse(badIncidentFilter.hasValidPriority());
		
		IncidentPriority.get().forEach((incidentPriority) -> {
			goodIncidentFilter.setPriorityLevel(incidentPriority);
			assertTrue(goodIncidentFilter.hasValidPriority());
		});
				
		assertFalse(uppercaseIncidentFilter.hasValidPriority());
	}
	
	@Test
	void testhasValidIncidentType(){
		assertFalse(emptyIncidentFilter.hasValidIncidentType());
		assertFalse(nullIncidentFilter.hasValidIncidentType());
		assertFalse(badIncidentFilter.hasValidIncidentType());
		
		IncidentType.get().forEach((incidentType) -> {
			goodIncidentFilter.setIncidentType(incidentType);
			assertTrue(goodIncidentFilter.hasValidIncidentType());
		});
	
		
		assertFalse(uppercaseIncidentFilter.hasValidIncidentType());
	}
	
	@Test
	void testIsNotEmpty(){
		String nullValue = null;
		
		Boolean result = ReflectionTestUtils.invokeMethod(goodIncidentFilter, "isNotEmpty",  nullValue);
		assertFalse(result);
		result = ReflectionTestUtils.invokeMethod(goodIncidentFilter, "isNotEmpty",  "");
		assertFalse(result);
		result = ReflectionTestUtils.invokeMethod(goodIncidentFilter, "isNotEmpty",  "valid");
		assertTrue(result);
	}
}
