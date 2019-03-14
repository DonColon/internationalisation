package com.dardan.rrafshi.internationalisation.language;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public final class ResourceBundles
{
	private static final String INDICATOR_MISSING_RESOURCE = "?";
	private static final String INDICATOR_MISSING_KEY = "??";


	private ResourceBundles() {}


	public static String getString(final ResourceBundle bundle, final String key)
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

	public static File[] searchBundles(final String directory, final String searchname)
	{
		final FileFilter bundleFilter = ResourceBundles.createResourceBundleFilter(searchname);

		final File bundleDirectory = new File(directory);

		if(bundleDirectory.exists()) {
			return bundleDirectory.listFiles(bundleFilter);
		} else {
			return new File[0];
		}
	}

	private static FileFilter createResourceBundleFilter(final String bundlename)
	{
		final FileFilter filter = (pathname) -> {
			return pathname.getName().startsWith(bundlename)
				&& pathname.getName().endsWith(".properties");
		};
		return filter;
	}

	public static Locale createLocaleFromBundleName(final String name)
	{
		final int languageIndex = name.indexOf('_');
		final int countryIndex = name.indexOf('_', languageIndex + 1);

		final String language = name.substring(languageIndex + 1,  languageIndex + 3);
		final String country = name.substring(countryIndex + 1, countryIndex + 3);

		if(language == null && country == null) {
			return Locale.getDefault();
		} else {
			return new Locale(language, country);
		}
	}
}
