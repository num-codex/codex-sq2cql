package de.numcodex.sq2cql.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.numcodex.sq2cql.model.common.TermCode;
import de.numcodex.sq2cql.model.structured_query.Modifier;

import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Kiel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Mapping {

    private final TermCode termCodeKey;
    private final String resourceType;
    private final List<Modifier> fixedCriteria;
    private final String valueFhirPath;

    private Mapping(TermCode termCodeKey, String resourceType, String valueFhirPath, List<Modifier> fixedCriteria) {
        this.termCodeKey = termCodeKey;
        this.resourceType = resourceType;
        this.valueFhirPath = valueFhirPath;
        this.fixedCriteria = fixedCriteria;
    }

    @JsonCreator
    public static Mapping of(@JsonProperty("key") TermCode termCodeKey,
                             @JsonProperty("fhirResourceType") String resourceType,
                             @JsonProperty("valueFhirPath") String valueFhirPath,
                             @JsonProperty("fixedCriteria") Modifier... fixedCriteria) {
        return new Mapping(Objects.requireNonNull(termCodeKey), Objects.requireNonNull(resourceType),
                valueFhirPath,
                fixedCriteria == null ? List.of() : List.of(fixedCriteria));
    }

    public TermCode getKey() {
        return termCodeKey;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getValueFhirPath(){
        return valueFhirPath;
    }

    public String getFhirPath() {
        if (fixedCriteria.size() == 0){
            return "value";
        }
        return fixedCriteria.get(0).getFhirPath();
    }
    public List<Modifier> getFixedCriteria() {
        return fixedCriteria;
    }
}
