package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record StringLiteralExpression(String value) implements Expression {

    public StringLiteralExpression {
        requireNonNull(value);
    }

    public static StringLiteralExpression of(String value) {
        return new StringLiteralExpression(value);
    }

    @Override
    public String print(PrintContext printContext) {
        return "'%s'".formatted(value);
    }
}
