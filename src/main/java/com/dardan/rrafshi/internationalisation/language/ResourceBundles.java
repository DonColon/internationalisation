package com.dardan.rrafshi.internationalisation.language;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <h1>ResourceBundles</h1>
 * A utility class for the ResourceBundle class. It provides helper methods to
 * to create locales from bundle names and acessing messages in a save way.
 *
 * @author Dardan Rrafshi
 * @version 0.0.1
 * @since 2019-03-20
 */
public final class ResourceBundles
{
	private static final String INDICATOR_MISSING_RESOURCE = "?";
	private static final String INDICATOR_MISSING_KEY = "??";


	private ResourceBundles() {}

	/**
	 * Fetch the message value linked to the key value in a safe way
	 * and format the template with the given values.
	 * If the key or the resource is missing the key value is returned
	 * with an indicator (? - missing key) or (?? - missing resource).
	 *
	 * @param bundle - the resource bundle to access
	 * @param key - the key value of the message
	 * @param values - the values to fill the placeholders
	 * @return message - the formated message linked to the key value
	 */
	public static String getMessage(final ResourceBundle bundle, final String key, final Object[] values)
	{
		final MessageFormat messageFormat = new MessageFormat(ResourceBundles.getMessage(bundle, key));
		return messageFormat.format(values);
	}

	/**
	 * Fetch the message value linked to the key value in a safe way.
	 * If the key or the resource is missing the key value is returned
	 * with an indicator (? - missing key) or (?? - missing resource).
	 *
	 * @param bundle - the resource bundle to access
	 * @param key - the key value of the message
	 * @return message - the message linked to the key value
	 */
	public static String getMessage(final ResourceBundle bundle, final String key)
	{
		if(bundle != null) {
			try {
				return bundle.getString(key);

			} catch(final MissingResourceException exception) {

				return INDICATOR_MISSING_KEY + key;
			}
		}
		return INDICATOR_MISSING_RESOURCE + key;
	}

	/**
	 * Creates a locale object from language/country code specified in the resource bundle.
	 *
	 * @param bundleName - the name of the resource bundle
	 * @return locale - a locale object of the resource bundle or the default locale
	 */
	public static Locale createLocaleFromBundleName(final String bundleName)
	{
		final int languageIndex = bundleName.indexOf('_');
		final int countryIndex = bundleName.indexOf('_', languageIndex + 1);

		final String language = bundleName.substring(languageIndex + 1,  languageIndex + 3);
		final String country = bundleName.substring(countryIndex + 1, countryIndex + 3);

		return new Locale(language, country);
	}

}
