package org.kubesmarts.yard.api.model;

import java.util.List;

import org.kie.j2cl.tools.json.mapper.annotation.JSONMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
@JSONMapper
public class YaRD {

    private String specVersion = "alpha";
    private String kind = "YaRD";
    private String name;
    private String expressionLang;
    private List<Input> inputs;
    private List<Element> elements;

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public String getExpressionLang() {
        return expressionLang;
    }

    public void setExpressionLang(String expressionLang) {
        this.expressionLang = expressionLang;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public List<Element> getElements() {
        return elements;
    }
}
