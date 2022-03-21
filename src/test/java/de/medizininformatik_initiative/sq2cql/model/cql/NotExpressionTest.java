package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;
import org.junit.jupiter.api.Test;

import static de.medizininformatik_initiative.sq2cql.model.cql.BooleanExpression.FALSE;
import static de.medizininformatik_initiative.sq2cql.model.cql.BooleanExpression.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexander Kiel
 */
class NotExpressionTest {

    @Test
    void print_HigherPrecedenceChild() {
        var cql = NotExpression.of(TRUE).print(PrintContext.ZERO);

        assertEquals("not true", cql);
    }

    @Test
    void print_LowerPrecedenceChild() {
        var cql = NotExpression.of(AndExpression.of(TRUE, FALSE)).print(PrintContext.ZERO);

        assertEquals("not (true and\nfalse)", cql);
    }
}
