package org.kubesmarts.yard.api.model;

import java.util.Locale;

import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.YAMLSerializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.internal.ser.YAMLSerializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;

public class WhenThenRuleThenSerializer
        implements YAMLSerializer<Object>,
                   YAMLDeserializer<Object> {

    @Override
    public Object deserialize(YamlMapping yamlMapping, String key, YAMLDeserializationContext yamlDeserializationContext) throws YAMLDeserializationException {
        return deserialize(yamlMapping.getNode(key), yamlDeserializationContext);
    }

    @Override
    public Object deserialize(YamlNode yamlNode, YAMLDeserializationContext yamlDeserializationContext) {
        if (yamlNode == null || yamlNode.isEmpty()) {
            return null;
        }
        return yamlNode.<String>asScalar().value().toLowerCase(Locale.ROOT);
    }

    @Override
    public void serialize(YamlMapping yamlMapping, String s, Object o, YAMLSerializationContext yamlSerializationContext) {
        // Not needed, we never serialize.
    }

    @Override
    public void serialize(YamlSequence yamlSequence, Object o, YAMLSerializationContext yamlSerializationContext) {
        // Not needed, we never serialize.
    }
}
