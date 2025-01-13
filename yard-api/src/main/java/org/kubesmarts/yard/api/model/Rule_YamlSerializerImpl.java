package org.kubesmarts.yard.api.model;

import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;


public class Rule_YamlSerializerImpl
        implements YAMLSerializer<Rule> {

    @Override
    public void serialize(YamlMapping yamlMapping, String s, Rule rule, YAMLSerializationContext yamlSerializationContext) {

    }

    @Override
    public void serialize(YamlSequence yamlSequence, Rule rule, YAMLSerializationContext yamlSerializationContext) {

    }
}
