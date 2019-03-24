package com.dardan.rrafshi.internationalisation.examples;

import java.util.Locale;

import com.dardan.rrafshi.internationalisation.alphanumeric.DigitFormatter;

public class DigitFormatting
{

	public static void main(final String[] args)
	{
		final double number = 1005.0558309583;
		final double percentage = 0.3467;

		final DigitFormatter formatter = new DigitFormatter(3, Locale.GERMANY);
		System.out.println(formatter.format(number));
		System.out.println(formatter.formatAsCurrency(number));
		System.out.println(formatter.formatAsPercentage(percentage));
	}

}
