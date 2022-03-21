package de.medizininformatik_initiative.sq2cql.model.structured_query;

import de.medizininformatik_initiative.sq2cql.PrintContext;
import de.medizininformatik_initiative.sq2cql.model.MappingContext;
import de.medizininformatik_initiative.sq2cql.model.common.TermCode;
import de.medizininformatik_initiative.sq2cql.model.cql.IdentifierExpression;
import de.medizininformatik_initiative.sq2cql.model.structured_query.CodingModifier;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexander Kiel
 */
class CodingModifierTest {

    static final TermCode CONFIRMED = TermCode.of("http://terminology.hl7.org/CodeSystem/condition-ver-status",
            "confirmed", "Conformed");

    static final Map<String, String> CODE_SYSTEM_ALIASES = Map.of(
            "http://terminology.hl7.org/CodeSystem/condition-ver-status", "ver_status");

    static final MappingContext MAPPING_CONTEXT = MappingContext.of(Map.of(), null, CODE_SYSTEM_ALIASES);

    @Test
    void expression() {
        var modifier = CodingModifier.of("verificationStatus", CONFIRMED);

        var expression = modifier.expression(MAPPING_CONTEXT, IdentifierExpression.of("C"));

        assertEquals("C.verificationStatus.coding contains Code 'confirmed' from ver_status",
                expression.getExpression().map(PrintContext.ZERO::print).orElse(""));
    }
}
