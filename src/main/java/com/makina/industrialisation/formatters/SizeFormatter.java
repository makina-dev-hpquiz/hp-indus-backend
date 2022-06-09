package com.makina.industrialisation.formatters;

import org.springframework.stereotype.Service;

@Service
public class SizeFormatter extends AbstractFormatter<Double> {

	private final double KO = 1024d;
	private final double MO = 1048576d;
	
	/**
	 * Formate la size pour qu'elle soit compris facilement.
	 * @param size
	 * @return String
	 */
	@Override
	public String format(Double size) {
		String str = "";
		if(size > MO) {
			size = size/MO;
			str = " Mo";
		} else if(size > KO) {
			size = (size/KO);
			str = " Ko";
		} else {
			str =" o";
		}

		return (Math.round(size * 100.0) / 100.0) + str;
	}

}
