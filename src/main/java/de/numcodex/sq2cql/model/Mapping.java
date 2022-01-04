package de.numcodex.sq2cql.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.numcodex.sq2cql.model.common.TermCode;
import de.numcodex.sq2cql.model.structured_query.Modifier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Alexander Kiel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Mapping {

    private final TermCode concept;
    private final String resourceType;
    private final List<Modifier> fixedCriteria;
    private final Optional<String> valueFhirPath;

    private Mapping(TermCode concept, String resourceType, String valueFhirPath, List<Modifier> fixedCriteria) {
        this.concept = concept;
        this.resourceType = resourceType;
        this.fixedCriteria = fixedCriteria;
        this.valueFhirPath = Optional.ofNullable(valueFhirPath);
    }

    @JsonCreator
    public static Mapping of(@JsonProperty("key") TermCode concept,
                             @JsonProperty("fhirResourceType") String resourceType,
                             @JsonProperty("valueFhirPath") String valueFhirPath,
                             @JsonProperty("fixedCriteria") Modifier... fixedCriteria) {
        return new Mapping(Objects.requireNonNull(concept), Objects.requireNonNull(resourceType), valueFhirPath,
                fixedCriteria == null ? List.of() : List.of(fixedCriteria));
    }

    public TermCode getConcept() {
        return concept;
    }

    public String getResourceType() {
        return resourceType;
    }

    public List<Modifier> getFixedCriteria() {
        return fixedCriteria;
    }

    public TermCode getTermCode() { return concept; }

    public Optional<String> getValueFhirPath() { return valueFhirPath; }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Mapping map = (Mapping) o;

        if (!concept.equals(map.concept)) { return false; }

        if (!resourceType.equals(map.resourceType)){ return false; }

        var sourceCriterion= fixedCriteria.iterator();
        var mapCriterion = map.fixedCriteria.iterator();
        while(sourceCriterion.hasNext() && mapCriterion.hasNext())
        {
            if(!sourceCriterion.next().equals(mapCriterion.next())){ return false; }
        }

        return !valueFhirPath.equals(map.valueFhirPath);
    }

}
