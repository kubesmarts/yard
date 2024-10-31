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

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.SingletonStore;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record YaRDDefinitions(
        Map<String, DataSource<Object>> inputs,
        Map<String, Object> rawInputs,
        List<Firable> units,
        Map<String, StoreHandle<Object>> outputs) {

    public Map<String, Object> evaluate(Map<String, Object> context) {
        Map<String, Object> results = new LinkedHashMap<>(context);
        for (String inputKey : inputs.keySet()) {
            if (!context.containsKey(inputKey)) {
                throw new IllegalArgumentException("Missing input key in context: " + inputKey);
            }
            rawInputs.putAll(context);
            Object inputValue = context.get(inputKey);
            if (inputs.get(inputKey) instanceof SingletonStore<Object> singletonStore) {
                singletonStore.set(inputValue);
            } else if (inputs.get(inputKey) instanceof DataStore<Object> dataStore) {
                if (inputValue instanceof Collection collection) {
                    for (Object o : collection) {
                        dataStore.add(o);
                    }
                } else {
                    throw new IllegalArgumentException("Store needs to be a collection.");
                }
            }
        }
        for (Firable unit : units) {
            unit.fire(context, this);
        }
        for (Entry<String, StoreHandle<Object>> outputSets : outputs.entrySet()) {
            results.put(outputSets.getKey(), outputSets.getValue().get());
        }
        reset();
        return results;
    }

    private void reset() {
        inputs.forEach((k, v) -> {
            if (v instanceof SingletonStore<Object> vv) {
                vv.clear();
            }
            // TODO multiple
        });
        rawInputs.clear();
        outputs.forEach((k, v) -> v.clear());
    }
}
