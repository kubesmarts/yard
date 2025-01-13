package org.kubesmarts.yard.api.model;

import java.util.ArrayList;
import java.util.List;

import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlSequence;


public class Rule_YamlDeserializerImpl
        implements YAMLDeserializer<Rule> {

    private int rowNumber = 1;

    @Override
    public Rule deserialize(YamlMapping yamlMapping,
                              String s,
                              YAMLDeserializationContext yamlDeserializationContext) throws YAMLDeserializationException {
        return deserialize(yamlMapping.getNode(s), yamlDeserializationContext);
    }

    @Override
    public Rule deserialize(YamlNode yamlNode,
                              YAMLDeserializationContext yamlDeserializationContext) {
        if (yamlNode instanceof YamlSequence) {
            final List<Comparable> items = getItems(yamlNode);
            return new InlineRule(rowNumber++, items);
        } else if (yamlNode instanceof YamlMapping) {
            final WhenThenRule whenThenRule = new WhenThenRule(rowNumber++);
            final YamlNode when = ((YamlMapping) yamlNode).getNode("when");
            final YamlNode then = ((YamlMapping) yamlNode).getNode("then");
            whenThenRule.setWhen(getItems(when));
            whenThenRule.setThen(then.asScalar().value());
            return whenThenRule;
        }
        throw  new IllegalArgumentException("Unknown rule format.");
    }

    private List<Comparable> getItems(final YamlNode yamlNode) {
        final List<Comparable> result = new ArrayList<>();
        if (yamlNode instanceof YamlSequence) {
            ((YamlSequence) yamlNode).iterator().forEachRemaining(x -> {
                final Comparable value = (Comparable) x.asScalar().value();
                result.add(value);
            });
        }
        return result;
    }
}
