package org.kubesmarts.yard.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.ruleunits.api.SingletonStore;

public record YaRDDefinitions(
        Map<String, SingletonStore<Object>> inputs,
        List<Firable> units,
        Map<String, StoreHandle<Object>> outputs) {

    public Map<String, Object> evaluate(Map<String, Object> context) {
        Map<String, Object> results = new LinkedHashMap<>(context);
        for (String inputKey : inputs.keySet()) {
            if (!context.containsKey(inputKey)) {
                throw new IllegalArgumentException("Missing input key in context: " + inputKey);
            }
            Object inputValue = context.get(inputKey);
            inputs.get(inputKey).set(inputValue);
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
        inputs.forEach((k, v) -> v.clear());
        outputs.forEach((k, v) -> v.clear());
    }
}
