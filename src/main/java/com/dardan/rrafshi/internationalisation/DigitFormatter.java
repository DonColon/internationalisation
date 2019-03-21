package com.dardan.rrafshi.internationalisation;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


public final class DigitFormatter implements Localisable
{
	private Locale currentLocale;
	private int decimals;


	public DigitFormatter(final int decimals, final Locale locale)
	{
		this.setDecimals(decimals);
		this.activateLocale(locale);
	}

	public DigitFormatter(final Locale locale)
	{
		this(3, locale);
	}


	public String format(final BigDecimal number)
	{
		return this.format(number.doubleValue());
	}

	public String format(final double number)
	{
		final NumberFormat format = NumberFormat.getNumberInstance(this.currentLocale);
		format.setMaximumFractionDigits(this.decimals);

		return format.format(number);
	}

	public String formatAsCurrency(final BigDecimal number)
	{
		return this.formatAsCurrency(number.doubleValue());
	}

	public String formatAsCurrency(final double number)
	{
		final NumberFormat format = NumberFormat.getCurrencyInstance(this.currentLocale);
		format.setMaximumFractionDigits(this.decimals);

		return format.format(number);
	}

	public String formatAsPercentage(final BigDecimal number)
	{
		return this.formatAsPercentage(number.doubleValue());
	}

	public String formatAsPercentage(final double number)
	{
		final NumberFormat format = NumberFormat.getPercentInstance(this.currentLocale);
		format.setMinimumFractionDigits(this.decimals);

		return format.format(number);
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

	public void setDecimals(final int decimals)
	{
		if(decimals < 0)
			this.decimals = 0;
		else
			this.decimals = decimals;
	}

	public int getDecimals()
	{
		return this.decimals;
	}

}
