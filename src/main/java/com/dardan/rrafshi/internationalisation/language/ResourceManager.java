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

import com.dardan.rrafshi.internationalisation.Localisable;

/**
 * <h1>ResourceManager</h1>
 * A class for managing resource bundles. It detects resource bundles in the
 * specified path and loads the content. It allows you to easily switch between
 * bundles according to the selected locale object.
 *
 * @author Dardan Rrafshi
 * @version 0.0.1
 * @since 2019-03-20
 */
public final class ResourceManager implements Localisable
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


	/**
	 * Loads the available resource bundles from specified path with the specified bundlename
	 * and adds them to the map.
	 *
	 * @throws IOException - when the user doesn't have the rights to read the file
	 * @throws FileNotFoundException - when the file doesn't exist in the specified path
	 */
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

	/**
	 * Searches the specified directory after resource bundles with the specified searchname.
	 *
	 * @param directory - the directory to search the resource bundles
	 * @param searchname - the name of the files to search after
	 * @return listOfBundles - an array of all files that matches the filter
	 */
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

	/**
	 * Creates a file filter that looks for files that starts with the specified bundlename
	 * and ends with the file type '.properties'.
	 *
	 * @param bundlename - the name of the resource bundles
	 * @return filter - the created file filter
	 */
	private FileFilter createResourceBundleFilter(final String bundlename)
	{
		final FileFilter filter = (pathname) -> {
			return pathname.getName().startsWith(bundlename)
				&& pathname.getName().endsWith(".properties");
		};
		return filter;
	}


	/**
	 * Fetch the message value linked to the key value in a safe way
	 * and format the template with the given values.
	 *
	 * @param key - the key value of the message
	 * @param values - the values to fill the placeholders as a collection
	 * @return message - the formated message linked to the key value
	 */
	public String getMessage(final String key, final Collection<Object> values)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key, values.toArray());
	}

	/**
	 * Fetch the message value linked to the key value in a safe way
	 * and format the template with the given values.
	 *
	 * @param key - the key value of the message
	 * @param values - the values to fill the placeholders as varargs
	 * @return message - the formated message linked to the key value
	 */
	public String getMessage(final String key, final Object... values)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key, values);
	}

	/**
	 * Fetch the message value linked to the key value in a safe way.
	 *
	 * @param key - the key value of the message
	 * @return message - the message linked to the key value
	 */
	public String getMessage(final String key)
	{
		return ResourceBundles.getMessage(this.currentResourceBundle, key);
	}

	/**
	 * Sets the current locale and resource bundle of the manager
	 *
	 * @param locale - the locale object to activate
	 * @return activated - indicates if the activation was successful
	 */
	@Override
	public boolean activateLocale(final Locale locale)
	{
		if(this.supportsLocale(locale)) {
			this.currentResourceBundle = this.availableResourceBundles.get(locale);
			this.currentLocale = locale;
			return true;
		}
		return false;
	}

	/**
	 * Returns the current selected locale object of the manager.
	 *
	 * @return locale - the current locale object
	 */
	@Override
	public Locale currentLocale()
	{
		return this.currentLocale;
	}

	/**
	 * Adds a resource bundle for a specific language on runtime.
	 *
	 * @param locale - the locale object to support
	 * @param resourceBundle - the resource bundle of the locale
	 */
	public void addResourceBundle(final Locale locale, final ResourceBundle resourceBundle)
	{
		this.availableResourceBundles.put(locale, resourceBundle);
	}

	/**
	 * Checks if the locale is supported by the manager.
	 *
	 * @param locale - the locale object to check
	 * @return isSupported - indicates if the locale object is supported
	 */
	public boolean supportsLocale(final Locale locale)
	{
		return this.availableResourceBundles.containsKey(locale);
	}

	/**
	 * Returns the available locale objects of the manager.
	 *
	 * @return availableLocales - a list of the available locale objects
	 */
	public List<Locale> getAvailableLocales()
	{
		return new ArrayList<>(this.availableResourceBundles.keySet());
	}

}
