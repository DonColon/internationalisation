package com.dardan.rrafshi.internationalisation.language;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
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
		throws IOException, FileNotFoundException
	{
		this.availableResourceBundles = new HashMap<>();
		this.pathToBundles = pathToBundles;
		this.nameOfBundles = nameOfBundles;

		this.init();
		this.activateLocale(locale);
	}

	public ResourceManager(final String pathToBundles, final String nameOfBundles)
		throws IOException, FileNotFoundException
	{
		this(pathToBundles, nameOfBundles, Locale.getDefault());
	}


	private void init()
		throws IOException, FileNotFoundException

	{
		final File[] propertyFiles = this.searchBundles(this.pathToBundles, this.nameOfBundles);

		for(final File propertyFile : propertyFiles) {

			try(InputStream stream = new BufferedInputStream(new FileInputStream(propertyFile))) {

				final ResourceBundle resourceBundle = new PropertyResourceBundle(stream);

				final Locale locale = ResourceBundles.createLocaleFromBundleName(propertyFile.getName());

				this.addResourceBundle(locale, resourceBundle);

			} catch (final FileNotFoundException exception) {

				throw new FileNotFoundException("Property file '" + propertyFile.getName() + "' not found in the directory '" + this.pathToBundles + "'");

			} catch (final IOException exception) {

				throw new IOException("Failed to load resource from file '" + propertyFile.getName() + "'", exception);
			}
		}
	}

	private File[] searchBundles(final String directory, final String searchname)
	{
		final FileFilter bundleFilter = this.createResourceBundleFilter(searchname);

		final File bundleDirectory = new File(directory);

		if(bundleDirectory.exists()) {
			return bundleDirectory.listFiles(bundleFilter);
		} else {
			return new File[0];
		}
	}

	private FileFilter createResourceBundleFilter(final String bundlename)
	{
		final FileFilter filter = (pathname) -> {
			return pathname.getName().startsWith(bundlename)
				&& pathname.getName().endsWith(".properties");
		};
		return filter;
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

	public String getMessage(final String key, final Collection<Object> values)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key, values.toArray());
	}

	public String getMessage(final String key, final Object... values)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key, values);
	}

	public String getMessage(final String key)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key);
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
