package org.kubesmarts.yard.api.model;

import org.kie.j2cl.tools.json.mapper.annotation.JSONMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;

@YAMLMapper
@JSONMapper
public class Input {

    private String name;
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}