package com.dardan.rrafshi.internationalisation.examples;

import java.util.Locale;

import com.dardan.rrafshi.internationalisation.measurement.UnitCategory;
import com.dardan.rrafshi.internationalisation.measurement.UnitConverter;
import com.dardan.rrafshi.internationalisation.measurement.UnitDefinition;


public final class UnitConversion
{

	public static void main(final String[] args)
	{
		final UnitConverter converter = new UnitConverter(UnitCategory.LENGTH, Locale.GERMANY);
		System.out.println(converter.convertToString(10, UnitDefinition.DECIMETER, UnitDefinition.KILOMETER));
	}

}
