package de.medizininformatik_initiative.sq2cql.model.structured_query;

import de.medizininformatik_initiative.sq2cql.Container;
import de.medizininformatik_initiative.sq2cql.model.Mapping;
import de.medizininformatik_initiative.sq2cql.model.MappingContext;
import de.medizininformatik_initiative.sq2cql.model.common.TermCode;
import de.medizininformatik_initiative.sq2cql.model.cql.BooleanExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.IdentifierExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.InvocationExpression;
import de.medizininformatik_initiative.sq2cql.model.cql.MembershipExpression;

import java.util.List;

/**
 * A {@code ValueSetCriterion} will select all patients that have at least one resource represented
 * by that concept and coded value.
 * <p>
 * Examples are {@code Observation} resources representing the concept of a coded laboratory value.
 */
public final class ValueSetCriterion extends AbstractCriterion {

  private final List<TermCode> selectedConcepts;

  private ValueSetCriterion(Concept concept, List<AttributeFilter> attributeFilters,
      TimeRestriction timeRestriction, List<TermCode> selectedConcepts) {
    super(concept, attributeFilters, timeRestriction);
    this.selectedConcepts = selectedConcepts;
  }

  /**
   * Returns a {@code ValueSetCriterion}.
   *
   * @param concept          the concept the criterion represents
   * @param selectedConcepts at least one selected value concept
   * @return the {@code ValueSetCriterion}
   */
  public static ValueSetCriterion of(Concept concept, TermCode... selectedConcepts) {
    if (selectedConcepts == null || selectedConcepts.length == 0) {
      throw new IllegalArgumentException("empty selected concepts");
    }
    return new ValueSetCriterion(concept, List.of(), null, List.of(selectedConcepts));
  }

  /**
   * Returns a {@code ValueSetCriterion}.
   *
   * @param concept          the concept the criterion represents
   * @param selectedConcepts at least one selected value concept
   * @param attributeFilters additional filters on particular attributes
   * @return the {@code ValueSetCriterion}
   */
  public static ValueSetCriterion of(Concept concept, List<TermCode> selectedConcepts,
      AttributeFilter... attributeFilters) {
    if (selectedConcepts == null || selectedConcepts.isEmpty()) {
      throw new IllegalArgumentException("empty selected concepts");
    }
    return new ValueSetCriterion(concept, List.of(attributeFilters), null, List.copyOf(selectedConcepts));
  }

  /**
   * Returns a {@code ValueSetCriterion}.
   *
   * @param concept          the concept the criterion represents
   * @param timeRestriction  the timeRestriction applied to the concept
   * @param selectedConcepts at least one selected value concept
   * @param attributeFilters additional filters on particular attributes
   * @return the {@code ValueSetCriterion}
   */
  public static ValueSetCriterion of(Concept concept, List<TermCode> selectedConcepts, TimeRestriction timeRestriction,
      AttributeFilter... attributeFilters) {
    if (selectedConcepts == null || selectedConcepts.isEmpty()) {
      throw new IllegalArgumentException("empty selected concepts");
    }
    return new ValueSetCriterion(concept, List.of(attributeFilters), timeRestriction, List.copyOf(selectedConcepts));
  }

  public List<TermCode> getSelectedConcepts() {
    return selectedConcepts;
  }

  Container<BooleanExpression> valueExpr(MappingContext mappingContext, Mapping mapping,
      IdentifierExpression identifier) {
    var valueExpr = valuePathExpr(identifier, mapping);
    return selectedConcepts.stream()
        .map(concept -> codeSelector(mappingContext, concept).map(terminology ->
            (BooleanExpression) MembershipExpression.contains(valueExpr, terminology)))
        .reduce(Container.empty(), Container.OR);
  }

  private InvocationExpression valuePathExpr(IdentifierExpression identifier, Mapping mapping) {
    if ("Coding".equals(mapping.valueType())) {
      return InvocationExpression.of(identifier, mapping.valueFhirPath());
    } else {
      return InvocationExpression.of(InvocationExpression.of(identifier, mapping.valueFhirPath()),
          "coding");
    }
  }
}
