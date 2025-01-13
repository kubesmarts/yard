package org.kubesmarts.yard.api.model;

import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlSubtype;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeInfo;

@YamlTypeInfo(
        key = "type",
        value = {
                @YamlSubtype(alias = "DecisionTable", type = DecisionTable.class),
                @YamlSubtype(alias = "LiteralExpression", type = LiteralExpression.class)
        })
@JsonbTypeInfo(
        key = "type",
        value = {
                @JsonbSubtype(alias = "DecisionTable", type = DecisionTable.class),
                @JsonbSubtype(alias = "LiteralExpression", type = LiteralExpression.class)
        }
)
public interface DecisionLogic {

}
