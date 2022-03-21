package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Expression1 AND Expression2 AND Expression ... AND ExpressionN
 */
public record AndExpression(List<BooleanExpression> expressions) implements BooleanExpression {

    public static final int PRECEDENCE = 4;

    public AndExpression {
        expressions = List.copyOf(expressions);
    }

    public static AndExpression of(BooleanExpression e1, BooleanExpression e2) {
        if (e1 instanceof AndExpression) {
            return new AndExpression(Stream.concat(((AndExpression) e1).expressions.stream(),
                    Stream.of(requireNonNull(e2))).toList());
        } else {
            return new AndExpression(List.of(requireNonNull(e1), requireNonNull(e2)));
        }
    }

    @Override
    public String print(PrintContext printContext) {
        return printContext.parenthesize(PRECEDENCE, expressions.stream()
                .map(printContext.withPrecedence(PRECEDENCE)::print)
                .collect(joining(" and\n" + printContext.getIndent())));
    }
}
