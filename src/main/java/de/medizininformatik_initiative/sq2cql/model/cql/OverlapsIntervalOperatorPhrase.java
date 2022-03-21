package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record OverlapsIntervalOperatorPhrase(Expression leftInterval,
                                             Expression rightInterval) implements
    BooleanExpression {

  public OverlapsIntervalOperatorPhrase {
    requireNonNull(leftInterval);
    requireNonNull(rightInterval);
  }

  public static OverlapsIntervalOperatorPhrase of(Expression leftInterval,
      Expression rightInterval) {
    return new OverlapsIntervalOperatorPhrase(leftInterval, rightInterval);
  }

  @Override
  public String print(PrintContext printContext) {
    return "%s overlaps %s".formatted(leftInterval.print(printContext),
        rightInterval.print(printContext));
  }

}
