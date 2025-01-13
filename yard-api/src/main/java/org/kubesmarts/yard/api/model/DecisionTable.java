package org.kubesmarts.yard.api.model;

import java.util.List;

import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import org.kie.j2cl.tools.json.mapper.annotation.JSONMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;

@YAMLMapper
@JSONMapper
public class DecisionTable implements DecisionLogic {

    private List<String> inputs;
    private String hitPolicy = "ANY";
    @Deprecated
    private List<String> outputComponents;
    @YamlTypeSerializer(Rule_YamlSerializerImpl.class)
    @YamlTypeDeserializer(Rule_YamlDeserializerImpl.class)
    @JsonbTypeSerializer(RuleJSONDefSerializer.class)
    @JsonbTypeDeserializer(RuleJSONDefSerializer.class)
    private List<Rule> rules;

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public void setOutputComponents(List<String> outputComponents) {
        this.outputComponents = outputComponents;
    }

    public List<String> getInputs() {
        return inputs;
    }

    @Deprecated
    public List<String> getOutputComponents() {
        return outputComponents;
    }

    public String getHitPolicy() {
        return hitPolicy;
    }

    public void setHitPolicy(String hitPolicy) {
        this.hitPolicy = hitPolicy;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
