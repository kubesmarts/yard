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

import org.kie.j2cl.tools.yaml.mapper.api.annotation.YAMLMapper;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeDeserializer;
import org.kie.j2cl.tools.yaml.mapper.api.annotation.YamlTypeSerializer;

import java.util.List;

@YAMLMapper
public class YamlRule {

    @YamlTypeDeserializer(Pattern_YamlDeserializerImpl.class)
    @YamlTypeSerializer(Pattern_YamlSerializerImpl.class)
    private List<Pattern> when;

    @YamlTypeDeserializer(YamlRuleThen_YamlDeserializerImpl.class)
    @YamlTypeSerializer(YamlRuleThen_YamlSerializerImpl.class)
    private YamlRuleThen then;

    public List<Pattern> getWhen() {
        return when;
    }

    public void setWhen(List<Pattern> when) {
        this.when = when;
    }

    public YamlRuleThen getThen() {
        return then;
    }

    public void setThen(YamlRuleThen then) {
        this.then = then;
    }
}
