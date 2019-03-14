package com.dardan.rrafshi.internationalisation.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public final class Chronos
{
	private Chronos() {}


	public static LocalDateTime parseDateTime(final String dateTime, final DateTimeFormatter format)
		throws DateTimeParseException
	{
		try {
			return LocalDateTime.parse(dateTime, format);

		} catch(final DateTimeParseException exception) {

			throw new DateTimeParseException("Unparsable input", dateTime, 0);
		}
	}

	public static LocalDate parseDate(final String date, final DateTimeFormatter format)
		throws DateTimeParseException
	{
		try {
			return LocalDate.parse(date, format);

		} catch(final DateTimeParseException exception) {

			throw new DateTimeParseException("Unparsable input", date, 0);
		}
	}

	public static LocalTime parseTime(final String time, final DateTimeFormatter format)
		throws DateTimeParseException
	{
		try {
			return LocalTime.parse(time, format);

		} catch(final DateTimeParseException exception) {

			throw new DateTimeParseException("Unparsable input", time, 0);
		}
	}
}
