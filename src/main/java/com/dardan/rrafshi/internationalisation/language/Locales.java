package com.dardan.rrafshi.internationalisation.language;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <h1>Locales</h1>
 * A utility class for the Locale class. It provides helper methods to filter and
 * sort Locale objects.
 *
 * @author Dardan Rrafshi
 * @version 0.0.1
 * @since 2019-03-20
 */
public final class Locales
{
	public static final Comparator<Locale> BY_LANGUAGE = Comparator.comparing(Locale::getDisplayLanguage);

	public static final Comparator<Locale> BY_COUNTRY = Comparator.comparing(Locale::getCountry);

	public static final Comparator<Locale> LOCALE_COMPARATOR = BY_LANGUAGE.thenComparing(BY_COUNTRY);


	private Locales() {}


	/**
	 * Sorts all available locales first after the language code then by
	 * the country code.
	 *
	 * @return sortedLocales - A sorted list of all available locales
	 */
	public static List<Locale> getSortedLocales()
	{
		final Locale[] availableLocales = Locale.getAvailableLocales();

		return Arrays.stream(availableLocales)
					 .sorted(LOCALE_COMPARATOR)
					 .collect(Collectors.toList());
	}

	/**
	 * Filters a list of sorted locales. It checks if the locale object only
	 * represents a language locale object.
	 *
	 * @return languageLocales - a sorted list of all available language locales
	 */
	public static List<Locale> getLanguageOnlyLocales()
	{
		final List<Locale> sortedLocales = Locales.getSortedLocales();

		final Predicate<Locale> isLanguageOnly = locale -> locale.getCountry().isEmpty();

		return sortedLocales.stream()
							.filter(isLanguageOnly)
							.collect(Collectors.toList());
	}

}
