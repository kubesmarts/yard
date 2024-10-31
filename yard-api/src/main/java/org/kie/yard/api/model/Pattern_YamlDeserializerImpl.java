/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.kie.yard.api.model;

import org.kie.j2cl.tools.yaml.mapper.api.YAMLDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.exception.YAMLDeserializationException;
import org.kie.j2cl.tools.yaml.mapper.api.internal.deser.YAMLDeserializationContext;
import org.kie.j2cl.tools.yaml.mapper.api.node.NodeType;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlMapping;
import org.kie.j2cl.tools.yaml.mapper.api.node.YamlNode;

import java.util.ArrayList;
import java.util.Objects;

public class Pattern_YamlDeserializerImpl
        implements YAMLDeserializer<Pattern> {

    @Override
    public Pattern deserialize(YamlMapping yamlMapping, String s, YAMLDeserializationContext yamlDeserializationContext) throws YAMLDeserializationException {
        return deserialize(yamlMapping.getNode(s), yamlDeserializationContext);
    }

    @Override
    public Pattern deserialize(YamlNode yamlNode, YAMLDeserializationContext yamlDeserializationContext) {
        final YamlMapping mapping = yamlNode.asMapping();
        if (mapping.keys().contains("given")) {
            return createGiven(mapping);
        } else if (mapping.keys().contains("groupBy")) {
            final GroupBy groupBy = new GroupBy();
            groupBy.setGiven(createGiven(mapping.getNode("groupBy").asMapping()));
            groupBy.setGrouping(createGrouping(mapping.getNode("grouping").asMapping()));
            groupBy.setAccumulators(createAccumulator(mapping.getNode("accumulator").asMapping()));
            return groupBy;
        }
        throw new IllegalStateException("Unknown element, should be given or groupBy");
    }

    private Accumulator createAccumulator(final YamlMapping groupBy) {
        final Accumulator accumulator = new Accumulator();
        accumulator.setFunction((String) groupBy.getNode("function").asScalar().value());
        accumulator.setAs((String) groupBy.getNode("as").asScalar().value());
        if (groupBy.keys().contains("parameter")) {
            accumulator.setParameter((String) groupBy.getNode("parameter").asScalar().value());
        }
        // TODO All are cast to Strings, check what happens with numbers
        return accumulator;
    }

    private static Given createGiven(YamlMapping mapping) {
        final Given given = new Given();
        given.setGiven((String) mapping.getNode("given").asScalar().value());
        given.setFrom((String) mapping.getNode("from").asScalar().value());
        given.setHaving(new ArrayList<>());
        if (mapping.keys().contains("having")
                && Objects.equals(mapping.getNode("having").type(), NodeType.SEQUENCE)) {
            for (YamlNode having : mapping.getNode("having").asSequence()) {
                given.getHaving().add((String) having.asScalar().value());
            }
        }
        return given;
    }

    private Grouping createGrouping(YamlMapping groupingNode) {
        final Grouping grouping = new Grouping();
        grouping.setBy((String) groupingNode.getNode("by").asScalar().value());
        grouping.setAs((String) groupingNode.getNode("as").asScalar().value());
        return grouping;
    }
}
