package de.medizininformatik_initiative.sq2cql.model.common;

import de.medizininformatik_initiative.sq2cql.model.common.Comparator;
import org.junit.jupiter.api.Test;

import static de.medizininformatik_initiative.sq2cql.model.common.Comparator.EQUAL;
import static de.medizininformatik_initiative.sq2cql.model.common.Comparator.GREATER_EQUAL;
import static de.medizininformatik_initiative.sq2cql.model.common.Comparator.GREATER_THAN;
import static de.medizininformatik_initiative.sq2cql.model.common.Comparator.LESS_EQUAL;
import static de.medizininformatik_initiative.sq2cql.model.common.Comparator.LESS_THAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Alexander Kiel
 */
class ComparatorTest {

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void fromJson() {
        assertEquals(EQUAL, Comparator.fromJson("eq"));
        assertEquals(LESS_EQUAL, Comparator.fromJson("le"));
        assertEquals(LESS_THAN, Comparator.fromJson("lt"));
        assertEquals(GREATER_EQUAL, Comparator.fromJson("ge"));
        assertEquals(GREATER_THAN, Comparator.fromJson("gt"));
        assertThrows(IllegalArgumentException.class, () -> Comparator.fromJson("foo"));
    }
}
