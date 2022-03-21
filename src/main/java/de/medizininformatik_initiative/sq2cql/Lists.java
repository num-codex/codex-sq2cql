package de.medizininformatik_initiative.sq2cql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List utils.
 *
 * @author Alexander Kiel
 */
public interface Lists {

    static <T> List<T> concat(Collection<T> a, Collection<T> b) {
        return Stream.concat(a.stream(), b.stream()).collect(Collectors.toUnmodifiableList());
    }
}
