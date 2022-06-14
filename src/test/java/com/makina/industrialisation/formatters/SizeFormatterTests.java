package com.makina.industrialisation.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SizeFormatterTests {
	
	@Autowired
	SizeFormatter sizeFormatter;

	@ParameterizedTest
	@CsvSource({
		"5228320d, 4.99 Mo",
		"20224d, 19.75 Ko",
		"44d, 44 o",
	})
	void testFormat(double valueToTest, String expected) {
		String result = sizeFormatter.format(valueToTest);
		assertEquals(expected, result);
	}

}
