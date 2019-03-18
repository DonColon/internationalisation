package com.dardan.rrafshi.internationalisation.examples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import com.dardan.rrafshi.internationalisation.language.ResourceManager;

public final class ResourceBundling
{

	public static void main(final String[] args)
		throws FileNotFoundException, IOException
	{
		final String pathToBundles = "src/main/resources/com/dardan/rrafshi/bundles";
		final String nameOfBundles = "bundle";

		final ResourceManager resourceManager = new ResourceManager(pathToBundles, nameOfBundles, Locale.GERMANY);
		System.out.println(resourceManager.getAvailableLocales());

		final String message = resourceManager.getMessage("edit");
		System.out.println(message);
	}

}
