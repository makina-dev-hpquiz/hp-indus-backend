package com.makina.industrialisation.filters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NameFilterTest {
	
	@Test
	void testIsLatestVersion() {
		assertTrue(NameFilter.isLatestVersion("hp-core-latest.apk"));
		assertFalse(NameFilter.isLatestVersion("hp-core-1.0.1.apk"));
	}

}
