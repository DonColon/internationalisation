package com.dardan.rrafshi.internationalisation.alphanumeric;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.dardan.rrafshi.internationalisation.Localisable;


public final class DigitFormatter implements Localisable
{
	private NumberFormat numberFormatter;
	private NumberFormat currencyFormatter;
	private NumberFormat percentageFormatter;

	private Locale currentLocale;
	private int decimals;


	public DigitFormatter(final int decimals, final Locale locale)
	{
		this.activateLocale(locale);
		this.setDecimals(decimals);
	}

	public DigitFormatter(final Locale locale)
	{
		this(3, locale);
	}

	public DigitFormatter()
	{
		this(Locale.getDefault());
	}


	public String format(final BigDecimal number)
	{
		return this.format(number.doubleValue());
	}

	public String format(final double number)
	{
		return this.numberFormatter.format(number);
	}

	public String formatAsCurrency(final BigDecimal number)
	{
		return this.formatAsCurrency(number.doubleValue());
	}

	public String formatAsCurrency(final double number)
	{
		return this.currencyFormatter.format(number);
	}

	public String formatAsPercentage(final BigDecimal number)
	{
		return this.formatAsPercentage(number.doubleValue());
	}

	public String formatAsPercentage(final double number)
	{
		return this.percentageFormatter.format(number);
	}

	@Override
	public boolean activateLocale(final Locale locale)
	{
		if(locale != null) {
			this.currentLocale = locale;

			this.numberFormatter = NumberFormat.getNumberInstance(locale);
			this.currencyFormatter = NumberFormat.getCurrencyInstance(locale);
			this.percentageFormatter = NumberFormat.getPercentInstance(locale);

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

		this.numberFormatter.setMaximumFractionDigits(decimals);
		this.currencyFormatter.setMaximumFractionDigits(decimals);
		this.percentageFormatter.setMaximumFractionDigits(decimals);
	}

	public int getDecimals()
	{
		return this.decimals;
	}

}
