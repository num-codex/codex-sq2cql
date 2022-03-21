package de.medizininformatik_initiative.sq2cql;

import de.medizininformatik_initiative.sq2cql.Container;
import org.junit.jupiter.api.Test;

import static de.medizininformatik_initiative.sq2cql.model.cql.BooleanExpression.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexander Kiel
 */
class ContainerTest {

    @Test
    void flatMap_EmptyContainer() {
        var container = Container.empty().flatMap(Container::of);

        assertEquals(Container.empty(), container);
    }

    @Test
    void flatMap_ToEmptyContainer() {
        var container = Container.of(TRUE).flatMap(expr -> Container.empty());

        assertEquals(Container.empty(), container);
    }
}
