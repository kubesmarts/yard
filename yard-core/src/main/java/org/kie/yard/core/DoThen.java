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
package org.kie.yard.core;

import org.kie.yard.api.model.YamlRule;
import org.kie.yard.api.model.YamlRuleThenListImpl;

import java.util.List;
import java.util.Map;

public class DoThen {
    private final YaRDDefinitions definitions;

    public DoThen(final YaRDDefinitions definitions) {
        this.definitions = definitions;
    }

    public void doThen(final YamlRule ruleDefinition,
                       final StoreHandle storeHandle,
                       final Map<String, Object> context) {

        if (storeHandle.get() instanceof List list) {
            if (ruleDefinition.getThen() instanceof YamlRuleThenListImpl thenList) {
                if (thenList.getFunctions().containsKey("add")) {
                    list.add(context.get(thenList.getFunctions().get("add")));
                }
            }
        } else if (storeHandle.get() instanceof Map map) {
            if (ruleDefinition.getThen() instanceof YamlRuleThenListImpl thenList) {
                if (thenList.getFunctions().containsKey("put")) {
                    final Map<String, String> o = (Map<String, String>) thenList.getFunctions().get("put");
                    final Object key = new MVELLER(QuotedExprParsed.from(o.get("key"))).doTheMVEL(context, definitions);
                    final Object value = new MVELLER(QuotedExprParsed.from(o.get("value"))).doTheMVEL(context, definitions);
                    map.put(key, value);
                }
            }
        }
    }
}
