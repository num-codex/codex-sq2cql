package de.medizininformatik_initiative.sq2cql.model.structured_query;

import de.medizininformatik_initiative.sq2cql.model.common.TermCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConceptTest {

    static final TermCode C1 = TermCode.of("foo", "c1", "c1-d");
    static final TermCode C2 = TermCode.of("foo", "c2", "c2-d");

    @Test
    void constructor_Null() {
        assertThrows(NullPointerException.class, () -> new Concept(null));
    }

    @Test
    void format_WithOneTermCode() {
        assertEquals("(system: foo, code: c1, display: c1-d)", "%s".formatted(Concept.of(C1)));
    }

    @Test
    void format_WithTwoTermCodes() {
        assertEquals("(system: foo, code: c1, display: c1-d), (system: foo, code: c2, display: c2-d)",
                "%s".formatted(Concept.of(C1, C2)));
    }
}
