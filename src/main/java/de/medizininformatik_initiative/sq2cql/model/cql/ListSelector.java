package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import java.util.List;

import static java.util.stream.Collectors.joining;

public record ListSelector(List<? extends Expression> items) implements TermExpression {

    public ListSelector {
        items = List.copyOf(items);
    }

    public static ListSelector of(List<? extends Expression> items) {
        return new ListSelector(items);
    }

    @Override
    public String print(PrintContext printContext) {
        return "{ %s }".formatted(items.stream().map(printContext::print).collect(joining(", ")));
    }
}
