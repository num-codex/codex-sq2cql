package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

import static java.util.Objects.requireNonNull;

public record CodeSelector(String code, String codeSystemIdentifier) implements TermExpression {

    public CodeSelector {
        requireNonNull(code);
        requireNonNull(codeSystemIdentifier);
    }

    public static CodeSelector of(String code, String codeSystemIdentifier) {
        return new CodeSelector(code, codeSystemIdentifier);
    }

    @Override
    public String print(PrintContext printContext) {
        return "Code '%s' from %s".formatted(code, codeSystemIdentifier);
    }
}
