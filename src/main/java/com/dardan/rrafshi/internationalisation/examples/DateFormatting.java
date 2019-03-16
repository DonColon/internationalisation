package com.dardan.rrafshi.internationalisation.examples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Locale;

import com.dardan.rrafshi.internationalisation.temporal.TemporalManager;

public final class DateFormatting
{

	public static void main(final String[] args)
	{
		final TemporalManager temporalManager = new TemporalManager(Locale.GERMANY);

		final LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(temporalManager.formatDateTime(dateTime, FormatStyle.MEDIUM));
		System.out.println(temporalManager.formatDateTime(dateTime, FormatStyle.MEDIUM, FormatStyle.SHORT));

		final LocalDate date = LocalDate.now();
		System.out.println(temporalManager.formatDate(date, FormatStyle.FULL));

		final LocalTime time = LocalTime.now();
		System.out.println(temporalManager.formatTime(time, FormatStyle.MEDIUM));
	}

}
