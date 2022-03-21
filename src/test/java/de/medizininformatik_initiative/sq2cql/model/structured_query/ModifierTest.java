package de.medizininformatik_initiative.sq2cql.model.structured_query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.medizininformatik_initiative.sq2cql.model.structured_query.Modifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Alexander Kiel
 */
class ModifierTest {

    @Test
    void fromJson_MissingValues() {
        var mapper = new ObjectMapper();

        try {
            mapper.readValue("""
                    {
                      "type": "foo",
                      "fhirPath": "bar"
                    }
                    """, Modifier.class);
            fail();
        } catch (JsonProcessingException e) {
            assertEquals("missing modifier values", e.getCause().getMessage());
        }

        try {
            mapper.readValue("""
                    {
                      "type": "foo",
                      "fhirPath": "bar",
                      "value": []
                    }
                    """, Modifier.class);
            fail();
        } catch (JsonProcessingException e) {
            assertEquals("empty modifier values", e.getCause().getMessage());
        }
    }

    @Test
    void fromJson_UnknownType() {
        var mapper = new ObjectMapper();

        try {
            mapper.readValue("""
                    {
                      "type": "foo",
                      "fhirPath": "bar",
                      "value": ["a"]
                    }
                    """, Modifier.class);
            fail();
        } catch (JsonProcessingException e) {
            assertEquals("unknown type: foo", e.getCause().getMessage());
        }
    }
}
