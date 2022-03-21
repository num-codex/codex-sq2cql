package de.medizininformatik_initiative.sq2cql.model.structured_query;

import de.medizininformatik_initiative.sq2cql.Container;
import de.medizininformatik_initiative.sq2cql.model.Mapping;
import de.medizininformatik_initiative.sq2cql.model.MappingContext;
import de.medizininformatik_initiative.sq2cql.model.cql.BetweenExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.BooleanExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.Expression;
import de.medizininformatik_initiative.sq2cql.model.cql.IdentifierExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.InvocationExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.QuantityExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.TypeExpression;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A {@code RangeCriterion} will select all patients that have at least one resource represented by that concept and
 * a range of numeric values.
 * <p>
 * Examples are {@code Observation} resources representing the concept of a numeric laboratory value.
 */
public final class RangeCriterion extends AbstractCriterion {

    private final BigDecimal lowerBound;
    private final BigDecimal upperBound;
    private final String unit;

  private RangeCriterion(Concept concept, List<AttributeFilter> attributeFilters,
      TimeRestriction timeRestriction, BigDecimal lowerBound,
      BigDecimal upperBound, String unit) {
    super(concept, attributeFilters, timeRestriction);
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.unit = unit;
  }

  public static RangeCriterion of(Concept concept, BigDecimal lowerBound, BigDecimal upperBound) {
    return new RangeCriterion(concept, List.of(), null, requireNonNull(lowerBound),
        requireNonNull(upperBound), null);
  }

  public static RangeCriterion of(Concept concept, BigDecimal lowerBound, BigDecimal upperBound,
      String unit,
      AttributeFilter... attributeFilters) {
    return new RangeCriterion(concept, List.of(attributeFilters), null, requireNonNull(lowerBound),
        requireNonNull(upperBound), requireNonNull(unit));
  }

  public static RangeCriterion of(Concept concept, BigDecimal lowerBound, BigDecimal upperBound,
      TimeRestriction timeRestriction,
      AttributeFilter... attributeFilters) {
    return new RangeCriterion(concept, List.of(attributeFilters), null, requireNonNull(lowerBound),
        requireNonNull(upperBound), null);
  }

  public static RangeCriterion of(Concept concept, BigDecimal lowerBound, BigDecimal upperBound,
      String unit, TimeRestriction timeRestriction,
      AttributeFilter... attributeFilters) {
    return new RangeCriterion(concept, List.of(attributeFilters), null, requireNonNull(lowerBound),
        requireNonNull(upperBound), requireNonNull(unit));
  }

    public BigDecimal getLowerBound() {
        return lowerBound;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public Optional<String> getUnit() {
        return Optional.ofNullable(unit);
    }

    @Override
    Container<BooleanExpression> valueExpr(MappingContext mappingContext, Mapping mapping,
                                           IdentifierExpression identifier) {
        var castExpr = TypeExpression.of(InvocationExpression.of(identifier, mapping.valueFhirPath()), "Quantity");
        return Container.of(BetweenExpression.of(castExpr, quantityExpression(lowerBound, unit),
                quantityExpression(upperBound, unit)));
    }

    private Expression quantityExpression(BigDecimal value, String unit) {
        return unit == null ? QuantityExpression.of(value) : QuantityExpression.of(value, unit);
    }
}
