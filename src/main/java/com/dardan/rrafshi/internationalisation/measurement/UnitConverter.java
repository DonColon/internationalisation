package com.dardan.rrafshi.internationalisation.measurement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class UnitConverter
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
	private static final int MAXIMUM_NUMBER_OF_DECIMALS = 12;


	private Locale currentLocale;
	private String formatString;
	private int decimals;

	private UnitDefinition baseUnitDefinition;
	private final Unit unit;


	public UnitConverter(final UnitCategory category, final UnitDefinition baseUnitDefinition, final Locale locale)
	{
		this.baseUnitDefinition = baseUnitDefinition;
		this.unit = BASE_UNITS.get(category).getUnit();

		this.decimals = 3;
		this.formatString = "%.3f";
		this.activateLocale(locale);
	}

	public UnitConverter(final UnitCategory category, final UnitDefinition baseUnitDefinition)
	{
		this(category, baseUnitDefinition, Locale.getDefault());
	}

	public UnitConverter(final UnitCategory category, final Locale locale)
	{
		this(category, BASE_UNITS.get(category), locale);
	}

	public UnitConverter(final UnitCategory category)
	{
		this(category, BASE_UNITS.get(category));
	}


    public double convert(final double value, final UnitDefinition unitDefinition) {
        if (unitDefinition.getUnit().getCategory() != this.getUnitCategory())
        	throw new IllegalArgumentException("Unit has to be of type '" + this.getUnitCategory() + "'");

        return (
			        (
			        		(
			        				(
			        						value + this.baseUnitDefinition.getUnit().getOffset().doubleValue()
			        				)
			        				* this.baseUnitDefinition.getUnit().getFactor().doubleValue()
			        		)
			        		+ this.unit.getOffset().doubleValue()
			        )
			        * this.unit.getFactor().doubleValue()
        		)
        		/ unitDefinition.getUnit().getFactor().doubleValue() - unitDefinition.getUnit().getOffset().doubleValue();
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


	public UnitDefinition getBaseUnitDefinition()
	{
		return this.baseUnitDefinition;
	}

	public void setBaseUnitDefinition(final UnitDefinition baseUnitDefinition)
	{
		if(baseUnitDefinition.getUnit().getCategory() == this.getUnitCategory())
			this.baseUnitDefinition = baseUnitDefinition;
	}

	public Locale getCurrentLocale()
	{
		return this.currentLocale;
	}

	public void activateLocale(final Locale locale)
	{
		this.currentLocale = locale;
	}

	public int getDecimals()
	{
		return this.decimals;
	}

	public void setDecimals(final int decimals)
	{
		if(decimals > MAXIMUM_NUMBER_OF_DECIMALS)
			this.decimals = MAXIMUM_NUMBER_OF_DECIMALS;
		else if(decimals < 0)
			this.decimals = 0;
		else
			this.decimals = decimals;

		this.formatString = "%." + decimals + "f";
	}

	public String getFormatString()
	{
		return this.formatString;
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
