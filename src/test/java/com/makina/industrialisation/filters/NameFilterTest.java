package com.makina.industrialisation.filters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NameFilterTest {
	
	@Test
	void testHasPartialName() {
		assertTrue(NameFilter.hasPartialName("hp-core-latest.apk", "hp-core"));
		assertFalse(NameFilter.hasPartialName("hp-core-1.0.1.apk", "hp-quiz"));
	}

}
