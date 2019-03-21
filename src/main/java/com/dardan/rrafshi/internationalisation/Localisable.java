package com.dardan.rrafshi.internationalisation;

import java.util.Locale;


public interface Localisable
{

	boolean activateLocale(Locale locale);

	Locale currentLocale();

}
