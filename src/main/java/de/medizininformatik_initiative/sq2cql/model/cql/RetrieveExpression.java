package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record RetrieveExpression(String resourceType, Expression terminology) implements Expression {

    public RetrieveExpression {
        requireNonNull(resourceType);
        requireNonNull(terminology);
    }

    public static RetrieveExpression of(String resourceType, Expression terminology) {
        return new RetrieveExpression(resourceType, terminology);
    }

    @Override
    public String print(PrintContext printContext) {
        return "[%s: %s]".formatted(resourceType, terminology.print(printContext));
    }

    public IdentifierExpression alias() {
        return IdentifierExpression.of(resourceType.substring(0, 1));
    }
}
