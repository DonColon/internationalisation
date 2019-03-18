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


public final class TemporalManager
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


	public String formatDateTime(final LocalDateTime dateTime, final FormatStyle dateTimeStyle)
		throws DateTimeException
	{
		return this.formatDateTime(dateTime, dateTimeStyle, dateTimeStyle);
	}

	public String formatDateTime(final LocalDateTime dateTime, final FormatStyle dateStyle, final FormatStyle timeStyle)
		throws DateTimeException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.formatDateTime(dateTime, format);
	}

	public LocalDateTime parseDateTime(final String dateTime, final FormatStyle dateTimeStyle)
		throws DateTimeParseException
	{
		return this.parseDateTime(dateTime, dateTimeStyle, dateTimeStyle);
	}

	public LocalDateTime parseDateTime(final String dateTime, final FormatStyle dateStyle, final FormatStyle timeStyle)
		throws DateTimeParseException
	{
		final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
				.withLocale(this.currentLocale).withZone(this.timeZone);
		return Temporals.parseDateTime(dateTime, format);
	}

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


	public void activateLocale(final Locale locale)
	{
		this.currentLocale = locale;
	}

	public void changeTimeZone(final ZoneId timeZone)
	{
		this.timeZone = timeZone;
	}

	public Locale getCurrentLocale()
	{
		return this.currentLocale;
	}

}
