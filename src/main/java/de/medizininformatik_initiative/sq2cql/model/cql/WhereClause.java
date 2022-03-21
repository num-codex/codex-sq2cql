package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record WhereClause(Expression expression) {

    public WhereClause {
        requireNonNull(expression);
    }

    public static WhereClause of(Expression expression) {
        return new WhereClause(expression);
    }

    public String toCql(PrintContext printContext) {
        return "where " + expression.print(printContext.resetPrecedence().increase());
    }
}
