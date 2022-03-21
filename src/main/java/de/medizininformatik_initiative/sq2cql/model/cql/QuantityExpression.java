package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public record QuantityExpression(BigDecimal value, String unit) implements Expression {

    public QuantityExpression {
        requireNonNull(value);
    }

    public static QuantityExpression of(BigDecimal value) {
        return new QuantityExpression(value, null);
    }

    public static QuantityExpression of(BigDecimal value, String unit) {
        return new QuantityExpression(value, requireNonNull(unit));
    }

    @Override
    public String print(PrintContext printContext) {
        return unit == null ? value.toString() : "%s '%s'".formatted(value, unit);
    }
}
