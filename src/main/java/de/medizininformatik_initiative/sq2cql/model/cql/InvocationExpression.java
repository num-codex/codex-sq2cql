package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

/**
 * @author Alexander Kiel
 */
public record InvocationExpression(Expression expression, String invocation) implements Expression {

    public InvocationExpression {
        requireNonNull(expression);
        requireNonNull(invocation);
    }

    public static InvocationExpression of(Expression expression, String invocation) {
        return new InvocationExpression(expression, invocation);
    }

    @Override
    public String print(PrintContext printContext) {
        return "%s.%s".formatted(expression.print(printContext), invocation);
    }
}
