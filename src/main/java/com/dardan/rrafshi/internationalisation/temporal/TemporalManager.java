package com.dardan.rrafshi.internationalisation.temporal;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import com.dardan.rrafshi.internationalisation.Localisable;


/**
 * <h1>TemporalManager</h1>
 * A class for managing temporal objects like date and time. It can parse strings
 * into temporal objects, format temporal objects to strings and convert temporals
 * into localised temporals with a time zone. You can easily change the format by
 * changing the locale object.
 *
 * @author Dardan Rrafshi
 * @version 0.0.1
 * @since 2019-03-20
 */
public final class TemporalManager implements Localisable
{
	private Locale currentLocale;
	private ZoneId timeZone;


	public TemporalManager(final Locale locale, final ZoneId timeZone)
	{
		this.activateLocale(locale);
		this.changeTimeZone(timeZone);
	}

	public TemporalManager(final Locale locale)
	{
		this(locale, ZoneId.systemDefault());
	}

	public TemporalManager()
	{
		this(Locale.getDefault());
	}


	/**
	 * Formats a LocalDateTime object into a String with the format of the
	 * current locale and the specified style.
	 *
	 * @param dateTime - the datetime object to format
	 * @param dateTimeStyle - the format style that should be used
	 * @return formattedDateTime - the string representation of the datetime object
	 * @throws DateTimeException - when the datetime object can't be formatted
	 */
	public String formatDateTime(final LocalDateTime dateTime, final FormatStyle dateTimeStyle)
		throws DateTimeException
	{
		return this.formatDateTime(dateTime, dateTimeStyle, dateTimeStyle);
	}

	/**
	 * Formats a LocalDateTime object into a String with the format of the
	 * current locale and the specified style for the date and the time.
	 *
	 * @param dateTime - the datetime object to format
	 * @param dateStyle - the style for the date portion
	 * @param timeStyle - the style for the time portion
	 * @return formattedDateTime - the string representation of the datetime object
	 * @throws DateTimeException - when the datetime object can't be formatted
	 */
	public String formatDateTime(final LocalDateTime dateTime, final FormatStyle dateStyle, final FormatStyle timeStyle)
		throws DateTimeException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.formatDateTime(dateTime, format);
	}

	/**
	 * Parses a String into LocalDateTime object with the format of the
	 * current locale and the specified style.
	 *
	 * @param dateTime - the string to parse
	 * @param dateTimeStyle - the format style that should be used
	 * @return parsedDateTime - the datetime object parsed from the string
	 * @throws DateTimeParseException - when the string isn't in the right format
	 */
	public LocalDateTime parseDateTime(final String dateTime, final FormatStyle dateTimeStyle)
		throws DateTimeParseException
	{
		return this.parseDateTime(dateTime, dateTimeStyle, dateTimeStyle);
	}

	/**
	 * Parses a String into LocalDateTime object with the format of the
	 * current locale and the specified style for the date and the time.
	 *
	 * @param dateTime - the string to parse
	 * @param dateStyle - the style for the date portion
	 * @param timeStyle - the style for the time portion
	 * @return parsedDateTime - the datetime object parsed from the string
	 * @throws DateTimeParseException - when the string isn't in the right format
	 */
	public LocalDateTime parseDateTime(final String dateTime, final FormatStyle dateStyle, final FormatStyle timeStyle)
		throws DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.parseDateTime(dateTime, format);
	}

	/**
	 * Converts a LocalDateTime object into a LocalDateTime object with the
	 * format of the current locale and the specified style.
	 *
	 * @param dateTime - the datetime object to convert
	 * @param dateTimeStyle - the format style that should be used
	 * @return convertedDateTime - the converted datetime object with the new format
	 * @throws DateTimeException - when the datetime object can't be formatted
	 * @throws DateTimeParseException - when the string isn't in the right format
	 */
	public LocalDateTime convertDateTime(final LocalDateTime dateTime, final FormatStyle dateTimeStyle)
		throws DateTimeException, DateTimeParseException
	{
		return this.convertDateTime(dateTime, dateTimeStyle, dateTimeStyle);
	}

	public LocalDateTime convertDateTime(final LocalDateTime dateTime, final FormatStyle dateStyle, final FormatStyle timeStyle)
		throws DateTimeException, DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.convertDateTime(dateTime, format);
	}


	/**
	 * Formats a LocalDate object into a String with the format of the
	 * current locale and the specified style.
	 *
	 * @param date - the date object to format
	 * @param dateStyle - the style for the date
	 * @return formattedDateTime - the string representation of the date object
	 * @throws DateTimeException - when the date object can't be formatted
	 */
	public String formatDate(final LocalDate date, final FormatStyle dateStyle)
		throws DateTimeException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(dateStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.formatDate(date, format);
	}

	public LocalDate parseDate(final String date, final FormatStyle dateStyle)
		throws DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(dateStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.parseDate(date, format);
	}

	public LocalDate convertDate(final LocalDate date, final FormatStyle dateStyle)
		throws DateTimeException, DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDate(dateStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.convertDate(date, format);
	}

	/**
	 * Formats a LocalTime object into a String with the format of the
	 * current locale and the specified style.
	 *
	 * @param time - the date object to format
	 * @param timeStyle - the style for the time
	 * @return formattedDateTime - the string representation of the time object
	 * @throws DateTimeException - when the time object can't be formatted
	 */
	public String formatTime(final LocalTime time, final FormatStyle timeStyle)
		throws DateTimeException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.formatTime(time, format);
	}

	public LocalTime parseTime(final String time, final FormatStyle timeStyle)
		throws DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.parseTime(time, format);
	}

	public LocalTime convertTime(final LocalTime time, final FormatStyle timeStyle)
		throws DateTimeException, DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.convertTime(time, format);
	}


	@Override
	public boolean activateLocale(final Locale locale)
	{
		if(locale != null) {
			this.currentLocale = locale;
			return true;
		}
		return false;
	}

	@Override
	public Locale currentLocale()
	{
		return this.currentLocale;
	}

	public void changeTimeZone(final ZoneId timeZone)
	{
		this.timeZone = timeZone;
	}

	public ZoneId currentTimeZone()
	{
		return this.timeZone;
	}

}
