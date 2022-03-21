package de.medizininformatik_initiative.sq2cql.model.cql;

import de.medizininformatik_initiative.sq2cql.PrintContext;

/**
 * @author Alexander Kiel
 */
public interface Statement {

    String print(PrintContext printContext);
}
