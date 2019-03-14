package com.dardan.rrafshi.internationalisation.language;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public final class Locales
{
	public static final Comparator<Locale> byLanguage = Comparator.comparing(Locale::getDisplayLanguage);

	public static final Comparator<Locale> byCountry = Comparator.comparing(Locale::getCountry);

	public static final Comparator<Locale> LOCALE_COMPARATOR = byLanguage.thenComparing(byCountry);


	private Locales() {}


	public static List<Locale> getSortedLocales()
	{
		final Locale[] availableLocales = Locale.getAvailableLocales();

		return Arrays.stream(availableLocales)
					 .sorted(LOCALE_COMPARATOR)
					 .collect(Collectors.toList());
	}

	public static List<Locale> getLanguageOnlyLocales()
	{
		final List<Locale> sortedLocales = Locales.getSortedLocales();

		final Predicate<Locale> isLanguageOnly = locale -> locale.getCountry().isEmpty();

		return sortedLocales.stream()
							.filter(isLanguageOnly)
							.collect(Collectors.toList());
	}

}
