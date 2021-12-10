package de.numcodex.sq2cql.model.structured_query;

import de.numcodex.sq2cql.Container;
import de.numcodex.sq2cql.model.MappingContext;
import de.numcodex.sq2cql.model.cql.BooleanExpression;
import de.numcodex.sq2cql.model.cql.Expression;
import de.numcodex.sq2cql.model.cql.InvocationExpression;
import de.numcodex.sq2cql.model.cql.ListSelector;
import de.numcodex.sq2cql.model.cql.MembershipExpression;
import de.numcodex.sq2cql.model.cql.StringLiteralExpression;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Alexander Kiel
 */
public final class CodeModifier extends AbstractModifier {

    private final List<String> codes;

    private CodeModifier(String path, List<String> codes) {
        super(path);
        this.codes = codes;
    }

    public static CodeModifier of(String path, String... codes) {
        return new CodeModifier(path, codes == null ? List.of() : List.of(codes));
    }

    public Container<BooleanExpression> expression(MappingContext mappingContext, Expression alias) {
        var propertyExpr = InvocationExpression.of(alias, path);
        var list = ListSelector.of(codes.stream().map(StringLiteralExpression::of).collect(Collectors.toList()));
        return Container.of(MembershipExpression.in(propertyExpr, list));
    }

    @Override
    public String getFhirPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeModifier that = (CodeModifier) o;
        return path.equals(that.path) && codes.equals(that.codes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, codes);
    }
}
