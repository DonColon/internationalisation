package com.dardan.rrafshi.internationalisation.measurement;

import java.math.BigDecimal;
import java.util.Objects;

public final class Unit
{
	private final UnitCategory category;
	private final String unitShort;
	private final String unitName;

	private BigDecimal factor;
	private BigDecimal offset;
	private boolean active;


	public Unit(final UnitCategory category, final String unitShort, final String unitName,
				final BigDecimal factor, final BigDecimal offset, final boolean active)
	{
		this.category = category;
		this.unitShort = unitShort;
		this.unitName = unitName;
		this.factor = factor;
		this.offset = offset;
		this.active = active;
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort,
				final BigDecimal factor, final BigDecimal offset)
	{
		this(category, unitName, unitShort, factor, offset, true);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort,
				final double factor, final double offset, final boolean active)
	{
		this(category, unitName, unitShort, new BigDecimal(Double.toString(factor)),
				new BigDecimal(Double.toString(offset)), active);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort,
				final double factor, final double offset)
	{
		this(category, unitName, unitShort, new BigDecimal(Double.toString(factor)),
				new BigDecimal(Double.toString(offset)), true);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort,
				final BigDecimal factor, final boolean active)
	{
		this(category, unitName, unitShort, factor, new BigDecimal("0.0"), active);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort, final BigDecimal factor)
	{
		this(category, unitName, unitShort, factor, new BigDecimal("0.0"), true);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort,
				final double factor, final boolean active)
	{
		this(category, unitName, unitShort, new BigDecimal(Double.toString(factor)),
				new BigDecimal("0.0"), active);
	}

	public Unit(final UnitCategory category, final String unitName, final String unitShort, final double factor)
	{
		this(category, unitName, unitShort, new BigDecimal(Double.toString(factor)),
				new BigDecimal("0.0"), true);
	}


	@Override
	public String toString() {
		return this.category + " " + this.unitName + " (" + this.unitShort + ") " + this.factor + ", " + this.offset;
	}

	@Override
	public boolean equals(final Object object)
	{
		if(object == null) return false;
		if(this == object) return true;

		if(this.getClass() != object.getClass())
			return false;

		final Unit unit = (Unit) object;
		return Objects.equals(this.category, unit.getCategory())
			&& Objects.equals(this.unitName, unit.getUnitName())
			&& Objects.equals(this.unitShort, unit.getUnitShort());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.category, this.unitName, this.unitShort);
	}


	public BigDecimal getFactor()
	{
		return this.factor;
	}

	public void setFactor(final BigDecimal factor)
	{
		this.factor = factor;
	}

	public void setFactor(final double factor)
	{
		this.factor = new BigDecimal(Double.toString(factor));
	}

	public BigDecimal getOffset()
	{
		return this.offset;
	}

	public void setOffset(final BigDecimal offset)
	{
		this.offset = offset;
	}

	public void setOffset(final double offset)
	{
		this.offset = new BigDecimal(Double.toString(offset));
	}

	public boolean isActive()
	{
		return this.active;
	}

	public void setActive(final boolean active)
	{
		this.active = active;
	}

	public UnitCategory getCategory()
	{
		return this.category;
	}

	public String getUnitName()
	{
		return this.unitName;
	}

	public String getUnitShort()
	{
		return this.unitShort;
	}

}
