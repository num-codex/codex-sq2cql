package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record ExpressionDefinition(String identifier, Expression expression) implements Statement {

    public ExpressionDefinition {
        requireNonNull(identifier);
        requireNonNull(expression);
    }

    public static ExpressionDefinition of(String identifier, Expression expression) {
        return new ExpressionDefinition(identifier, expression);
    }

    public Expression getExpression() {
        return expression;
    }

    public String print(PrintContext printContext) {
        var newPrintContext = printContext.increase();
        return "define %s:\n%s%s".formatted(identifier, newPrintContext.getIndent(),
                expression.print(newPrintContext));
    }
}
