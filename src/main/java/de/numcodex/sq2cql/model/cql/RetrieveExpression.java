package de.numcodex.sq2cql.model.cql;

import de.numcodex.sq2cql.PrintContext;

import java.util.Objects;

public final class RetrieveExpression implements Expression {

    private final String resourceType;
    private final Expression terminology;
    private final String valueFhirPath;
    private final String fhirPath;

    private RetrieveExpression(String resourceType, Expression terminology, String valueFhirPath, String fhirPath) {
        this.resourceType = Objects.requireNonNull(resourceType);
        this.terminology = Objects.requireNonNull(terminology);
        this.valueFhirPath = valueFhirPath;
        this.fhirPath = fhirPath;
    }

    public static RetrieveExpression of(String resourceType, Expression terminology, String valueFhirPath, String fhirPath) {
        return new RetrieveExpression(resourceType, terminology, valueFhirPath, fhirPath);
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getValueFhirPath() { return valueFhirPath; }

    public String getFhirPath() { return fhirPath; }

    @Override
    public String print(PrintContext printContext) {
        return "[%s: %s]".formatted(resourceType, terminology.print(printContext));
    }
}
