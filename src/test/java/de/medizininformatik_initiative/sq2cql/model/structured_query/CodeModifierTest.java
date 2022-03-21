package de.medizininformatik_initiative.sq2cql.model.structured_query;

import de.medizininformatik_initiative.sq2cql.PrintContext;
import de.medizininformatik_initiative.sq2cql.model.MappingContext;
import de.medizininformatik_initiative.sq2cql.model.cql.IdentifierExpression;
import de.medizininformatik_initiative.sq2cql.model.structured_query.CodeModifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeModifierTest {

    @Test
    void expression_OneCode() {
        var modifier = CodeModifier.of("status", "final");

        var expression = modifier.expression(MappingContext.of(), IdentifierExpression.of("O"));

        assertEquals("O.status = 'final'", PrintContext.ZERO.print(expression));
    }

    @Test
    void expression_TwoCodes() {
        var modifier = CodeModifier.of("status", "completed", "in-progress");

        var expression = modifier.expression(MappingContext.of(), IdentifierExpression.of("P"));

        assertEquals("P.status in { 'completed', 'in-progress' }", PrintContext.ZERO.print(expression));
    }
}
