package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record NotExpression(BooleanExpression expression) implements BooleanExpression {

    public static final int PRECEDENCE = 11;

    public NotExpression {
        requireNonNull(expression);
    }

    public static NotExpression of(BooleanExpression expression) {
        return new NotExpression(expression);
    }

    @Override
    public String print(PrintContext printContext) {
        return printContext.parenthesize(PRECEDENCE, "not " + expression.print(printContext.withPrecedence(PRECEDENCE)));
    }
}
