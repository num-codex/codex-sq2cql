package de.numcodex.sq2cql.model.structured_query;

import de.numcodex.sq2cql.Container;
import de.numcodex.sq2cql.model.MappingContext;
import de.numcodex.sq2cql.model.common.TermCode;
import de.numcodex.sq2cql.model.cql.AliasExpression;
import de.numcodex.sq2cql.model.cql.BooleanExpression;
import de.numcodex.sq2cql.model.cql.ExistsExpression;
import de.numcodex.sq2cql.model.cql.Expression;
import de.numcodex.sq2cql.model.cql.InvocationExpression;
import de.numcodex.sq2cql.model.cql.MembershipExpression;
import de.numcodex.sq2cql.model.cql.QueryExpression;
import de.numcodex.sq2cql.model.cql.SourceClause;
import de.numcodex.sq2cql.model.cql.WhereClause;

import java.util.List;

/**
 * A {@code ValueSetCriterion} will select all patients that have at least one resource represented by that concept and
 * coded value.
 * <p>
 * Examples are {@code Observation} resources representing the concept of a coded laboratory value.
 */
public final class ValueSetCriterion extends AbstractCriterion {

    private final List<TermCode> selectedConcepts;

    private ValueSetCriterion(TermCode concept, List<TermCode> selectedConcepts) {
        super(concept, List.of());
        this.selectedConcepts = selectedConcepts;
    }

    /**
     * Returns a {@code ValueSetCriterion}.
     *
     * @param concept          the concept the criterion represents
     * @param selectedConcepts at least one selected value concept
     * @return the {@code ValueSetCriterion}
     */
    public static ValueSetCriterion of(TermCode concept, String fhirPath, TermCode... selectedConcepts) {
        if (selectedConcepts == null || selectedConcepts.length == 0) {
            throw new IllegalArgumentException("empty selected concepts");
        }
        return new ValueSetCriterion(concept,List.of(selectedConcepts));
    }

    public List<TermCode> getSelectedConcepts() {
        return selectedConcepts;
    }

    public Container<BooleanExpression> toCql(MappingContext mappingContext) {
        //TODO: expand the termCode, look into ConceptCriterion#fullExpr
        return retrieveExpr(mappingContext, termCode).flatMap(retrieveExpr -> {
            var alias = AliasExpression.of(retrieveExpr.getResourceType().substring(0, 1));
            var sourceClause = SourceClause.of(retrieveExpr, alias);
            var codingExpr = InvocationExpression.of(InvocationExpression.of(alias, retrieveExpr.getFhirPath()), "coding");
            return whereExpr(mappingContext, codingExpr).map(whereExpr ->
                    ExistsExpression.of(QueryExpression.of(sourceClause, WhereClause.of(whereExpr))));
        });
    }

    private Container<BooleanExpression> whereExpr(MappingContext mappingContext, Expression codingExpr) {
        return selectedConcepts.stream()
                .map(concept -> codeSelector(mappingContext, concept).map(terminology ->
                        (BooleanExpression) MembershipExpression.contains(codingExpr, terminology)))
                .reduce(Container.empty(), Container.OR);
    }
}
