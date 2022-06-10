package com.makina.industrialisation.formatters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SizeFormatter implements Formatter<Double> {

	Logger logger = LogManager.getLogger(SizeFormatter.class);

	private static final double KO = 1024d;
	private static final double MO = 1048576d;

	private static final String MO_STR = "Mo";
	private static final String KO_STR = "Ko";
	private static final String O_STR = "o";
	private static final String ESCAPE = " ";

	/**
	 * Formate la size pour qu'elle soit compris facilement.
	 * @param size
	 * @return String
	 */
	@Override
	public String format(Double size) {
		String str = ESCAPE;

		if(size > MO) {
			size = size/MO;
			str += MO_STR;
		} else if(size > KO) {
			size = (size/KO);
			str += KO_STR;
		} else {
			str += O_STR;
			return Math.round(size) + str;
		}

		logger.debug("Formatage de la valeur {}", size);
		return (Math.round(size * 100.0) / 100.0) + str;
	}

}
