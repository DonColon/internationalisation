package com.dardan.rrafshi.internationalisation.temporal;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public final class Temporals
{
	private Temporals() {}


	public static String formatDateTime(final LocalDateTime dateTime, final DateTimeFormatter format)
		throws DateTimeException
	{
		try {
			return dateTime.format(format);

		} catch(final DateTimeException exception) {

			throw new DateTimeException("Input '" + dateTime.toString() +"'can not be formatted", exception);
		}
	}

	public static LocalDateTime parseDateTime(final String dateTime, final DateTimeFormatter format)
		throws DateTimeParseException
	{
		try {
			return LocalDateTime.parse(dateTime, format);

		} catch(final DateTimeParseException exception) {

			throw new DateTimeParseException("Unparsable input", dateTime, 0);
		}
	}

	public static LocalDateTime convertDateTime(final LocalDateTime dateTime, final DateTimeFormatter format)
		throws DateTimeException, DateTimeParseException
	{
		final String formattedDateTime = Temporals.formatDateTime(dateTime, format);
		final LocalDateTime parsedDateTime = Temporals.parseDateTime(formattedDateTime, format);
		return parsedDateTime;
	}


	public static String formatDate(final LocalDate date, final DateTimeFormatter format)
		throws DateTimeException
	{
		try {
			return date.format(format);

		} catch(final DateTimeException exception) {

			throw new DateTimeException("Input '" + date.toString() +"'can not be formatted", exception);
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

	public static LocalDate convertDate(final LocalDate date, final DateTimeFormatter format)
		throws DateTimeException, DateTimeParseException
	{
		final String formattedDate = Temporals.formatDate(date, format);
		final LocalDate parsedDate = Temporals.parseDate(formattedDate, format);
		return parsedDate;
	}


	public static String formatTime(final LocalTime time, final DateTimeFormatter format)
		throws DateTimeException
	{
		try {
			return time.format(format);

		} catch(final DateTimeException exception) {

			throw new DateTimeException("Input '" + time.toString() +"'can not be formatted", exception);
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

	public static LocalTime convertTime(final LocalTime time,  final DateTimeFormatter format)
		throws DateTimeException, DateTimeParseException
	{
		final String formattedTime = Temporals.formatTime(time, format);
		final LocalTime parsedTime = Temporals.parseTime(formattedTime, format);
		return parsedTime;
	}

}
