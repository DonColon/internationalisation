package com.dardan.rrafshi.internationalisation.language;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public final class ResourceManager
{
	private final Map<Locale, ResourceBundle> availableResourceBundles;
	private final String pathToBundles;
	private final String nameOfBundles;

	private ResourceBundle currentResourceBundle;
	private Locale currentLocale;


	public ResourceManager(final String pathToBundles, final String nameOfBundles, final Locale locale)
		throws IOException
	{
		this.availableResourceBundles = new HashMap<>();
		this.pathToBundles = pathToBundles;
		this.nameOfBundles = nameOfBundles;

		this.init();
		this.activateLocale(locale);
	}


	private void init()
		throws IOException
	{
		final File[] propertyFiles = ResourceBundles.searchBundles(this.nameOfBundles, this.pathToBundles);

		for(final File propertyFile : propertyFiles) {

			try(InputStream stream = new BufferedInputStream(new FileInputStream(propertyFile))) {

				final ResourceBundle resourceBundle = new PropertyResourceBundle(stream);

				final Locale locale = ResourceBundles.createLocaleFromBundleName(propertyFile.getName());

				this.addResourceBundle(locale, resourceBundle);

			} catch (final IOException exception) {

				throw new IOException("Failed to load resource from file '" + propertyFile.getName() + "'", exception);
			}
		}
	}

	public boolean activateLocale(final Locale locale)
	{
		if(this.supportsLocale(locale)) {
			this.currentResourceBundle = this.availableResourceBundles.get(locale);
			this.currentLocale = locale;
			return true;
		}
		return false;
	}

	public void addResourceBundle(final Locale locale, final ResourceBundle resourceBundle)
	{
		this.availableResourceBundles.put(locale, resourceBundle);
	}

	public String getString(final String key)
	{
		return ResourceBundles.getString(this.currentResourceBundle, key);
	}

	public boolean supportsLocale(final Locale locale)
	{
		return this.availableResourceBundles.containsKey(locale);
	}

	public Locale getCurrentLocale()
	{
		return this.currentLocale;
	}

	public List<Locale> getAvailableLocales()
	{
		return new ArrayList<>(this.availableResourceBundles.keySet());
	}

}
