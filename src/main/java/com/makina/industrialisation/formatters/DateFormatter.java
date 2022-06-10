package com.makina.industrialisation.formatters;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class DateFormatter implements Formatter<FileTime> {

	private static final DateTimeFormatter DATE_FORMATTER =
			DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	/**
	 * Formate la date de l'apk 
	 * @param fileTime
	 * @return String
	 */
	@Override
	public String format(FileTime fileTime) {
		LocalDateTime localDateTime = fileTime
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		return localDateTime.format(DATE_FORMATTER);
	}
}




