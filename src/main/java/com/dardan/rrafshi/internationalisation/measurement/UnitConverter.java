package com.dardan.rrafshi.internationalisation.measurement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.dardan.rrafshi.internationalisation.DigitFormatter;
import com.dardan.rrafshi.internationalisation.Localisable;

public final class UnitConverter implements Localisable
{
	private static final Map<UnitCategory, UnitDefinition> BASE_UNITS = new EnumMap<>(UnitCategory.class);

	{
		BASE_UNITS.put(UnitCategory.ACCELERATION, UnitDefinition.METER_PER_SQUARE_SECOND);
		BASE_UNITS.put(UnitCategory.ANGLE, UnitDefinition.RADIAN);
		BASE_UNITS.put(UnitCategory.AREA, UnitDefinition.SQUARE_METER);
		BASE_UNITS.put(UnitCategory.CURRENT, UnitDefinition.AMPERE);
		BASE_UNITS.put(UnitCategory.DATA, UnitDefinition.BIT);
		BASE_UNITS.put(UnitCategory.ELECTRIC_CHARGE, UnitDefinition.ELEMENTARY_CHARGE);
		BASE_UNITS.put(UnitCategory.ENERGY, UnitDefinition.JOULE);
		BASE_UNITS.put(UnitCategory.FORCE, UnitDefinition.NEWTON);
		BASE_UNITS.put(UnitCategory.HUMIDITY, UnitDefinition.PERCENTAGE);
		BASE_UNITS.put(UnitCategory.LENGTH, UnitDefinition.METER);
		BASE_UNITS.put(UnitCategory.LUMINANCE, UnitDefinition.CANDELA_SQUARE_METER);
		BASE_UNITS.put(UnitCategory.LUMINOUS_FLUX, UnitDefinition.LUX);
		BASE_UNITS.put(UnitCategory.MASS, UnitDefinition.KILOGRAM);
		BASE_UNITS.put(UnitCategory.PRESSURE, UnitDefinition.PASCAL);
		BASE_UNITS.put(UnitCategory.SPEED, UnitDefinition.METER_PER_SECOND);
		BASE_UNITS.put(UnitCategory.TEMPERATURE, UnitDefinition.KELVIN);
		BASE_UNITS.put(UnitCategory.TEMPERATURE_GRADIENT, UnitDefinition.KELVIN_PER_SECOND);
		BASE_UNITS.put(UnitCategory.TIME, UnitDefinition.SECOND);
		BASE_UNITS.put(UnitCategory.TORQUE, UnitDefinition.NEWTON_METER);
		BASE_UNITS.put(UnitCategory.VOLUME, UnitDefinition.CUBIC_MILLIMETER);
		BASE_UNITS.put(UnitCategory.VOLTAGE, UnitDefinition.VOLT);
		BASE_UNITS.put(UnitCategory.WORK, UnitDefinition.WATT);
	}


	private final DigitFormatter formatter;
	private Locale currentLocale;
	private final Unit unit;


	public UnitConverter(final UnitCategory category, final Locale locale)
	{
		this.formatter = new DigitFormatter(locale);
		this.unit = BASE_UNITS.get(category).getUnit();
		this.activateLocale(locale);
	}

	public UnitConverter(final UnitCategory category)
	{
		this(category, Locale.getDefault());
	}


    public double convert(final double value, final UnitDefinition fromUnit, final UnitDefinition toUnit)
    {
        if (toUnit.getUnit().getCategory() != this.getUnitCategory())
        	throw new IllegalArgumentException("Unit has to be of type '" + this.getUnitCategory() + "'");

        double convertedValue = value;

        convertedValue += fromUnit.getUnit().getOffset().doubleValue();
        convertedValue *= fromUnit.getUnit().getFactor().doubleValue();

        convertedValue += this.unit.getOffset().doubleValue();
        convertedValue *= this.unit.getFactor().doubleValue();

        convertedValue /= toUnit.getUnit().getFactor().doubleValue();
        convertedValue -= toUnit.getUnit().getOffset().doubleValue();

        return convertedValue;
    }

    public String convertToString(final double value, final UnitDefinition fromUnit, final UnitDefinition toUnit)
    {
    	final double convertedValue = this.convert(value, fromUnit, toUnit);
    	final String formattedValue = this.formatter.format(convertedValue);

    	return String.join(" ", formattedValue, toUnit.getUnit().getUnitShort());
    }

	public Map<UnitCategory, List<UnitDefinition>> getAllUnitDefinitions()
	{
		final Map<UnitCategory, List<UnitDefinition>> allUnitDefnitions = new EnumMap<>(UnitCategory.class);
		final List<UnitCategory> unitCategories = new ArrayList<>();

		unitCategories.addAll(Arrays.asList(UnitCategory.values()));
		unitCategories.forEach(category -> allUnitDefnitions.put(category, new ArrayList<>()));

		for(final UnitDefinition unitDefinition : UnitDefinition.values()) {
			final UnitCategory category = unitDefinition.getUnit().getCategory();
			final List<UnitDefinition> unitDefinitions = allUnitDefnitions.get(category);
			unitDefinitions.add(unitDefinition);
		}
		return allUnitDefnitions;
	}

	public Map<UnitCategory, List<UnitDefinition>> getAllActiveUnitDefinitions()
	{
		final Map<UnitCategory, List<UnitDefinition>> allActiveUnitDefnitions = new EnumMap<>(UnitCategory.class);
		final List<UnitCategory> unitCategories = new ArrayList<>();

		unitCategories.addAll(Arrays.asList(UnitCategory.values()));
		unitCategories.forEach(category -> allActiveUnitDefnitions.put(category, new ArrayList<>()));

		for(final UnitDefinition unitDefinition : UnitDefinition.values()) {
			final Unit unit = unitDefinition.getUnit();
			if(unit.isActive()) {
				final List<UnitDefinition> unitDefinitions = allActiveUnitDefnitions.get(unit.getCategory());
				unitDefinitions.add(unitDefinition);
			}
		}
		return allActiveUnitDefnitions;
	}

	public List<Unit> getAvailableUnits(final UnitCategory category)
	{
		return this.getAllUnitDefinitions().get(category).stream()
				   .map(unitDefinition -> unitDefinition.getUnit())
				   .collect(Collectors.toList());
	}

	@Override
	public boolean activateLocale(final Locale locale)
	{
		if(locale != null) {
			this.currentLocale = locale;
			this.formatter.activateLocale(locale);
			return true;
		}
		return false;
	}

	@Override
	public Locale currentLocale()
	{
		return this.currentLocale;
	}

	public void setDecimals(final int decimals)
	{
		this.formatter.setDecimals(decimals);
	}

	public int getDecimals()
	{
		return this.formatter.getDecimals();
	}


	public UnitCategory getUnitCategory()
	{
		return this.unit.getCategory();
	}

	public String getUnitName()
	{
		return this.unit.getUnitName();
	}

	public String getUnitShort()
	{
		return this.unit.getUnitShort();
	}

	public BigDecimal getFactor()
	{
		return this.unit.getFactor();
	}

	public BigDecimal getOffset()
	{
		return this.unit.getOffset();
	}

	public boolean isActive()
	{
		return this.unit.isActive();
	}

	public void setActive(final boolean active)
	{
		this.unit.setActive(active);
	}

}
