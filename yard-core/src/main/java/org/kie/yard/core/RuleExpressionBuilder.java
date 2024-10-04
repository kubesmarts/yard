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
import org.drools.ruleunits.dsl.SyntheticRuleUnit;
import org.drools.ruleunits.dsl.SyntheticRuleUnitBuilder;
import org.drools.ruleunits.dsl.accumulate.GroupByPattern1;
import org.drools.ruleunits.dsl.patterns.Pattern1Def;
import org.drools.ruleunits.dsl.patterns.PatternDef;
import org.kie.yard.api.model.Given;
import org.kie.yard.api.model.GroupBy;
import org.kie.yard.api.model.RuleExpression;
import org.kie.yard.api.model.YamlRule;

import java.util.*;

public class RuleExpressionBuilder {
    private final YaRDDefinitions definitions;
    private final List<YamlRule> rules;
    private final String name;
    private final String resultType;
    private final DoThen doThen;
    private final DoWhen doWhen;

    public RuleExpressionBuilder(final YaRDDefinitions definitions,
                                 final String name,
                                 final RuleExpression ruleExpression) {
        this.definitions = definitions;
        this.name = name;
        this.resultType = ruleExpression.getResult();
        this.rules = ruleExpression.getRules();
        this.doThen = new DoThen(definitions);
        this.doWhen = new DoWhen(definitions);
    }

    public SyntheticRuleUnit build() {

        final SyntheticRuleUnitBuilder unit = SyntheticRuleUnitBuilder.build(name);

        for (Map.Entry<String, DataSource<Object>> e : definitions.inputs().entrySet()) {
            unit.registerDataSource(e.getKey(), e.getValue(), Object.class);
        }

        final StoreHandle result = getResult();

        unit.registerGlobal(name, result);
        definitions.outputs().put(name, result);

        return unit.defineRules(rulesFactory -> {
            for (final YamlRule ruleDefinition : rules) {
                final PatternDef on = doWhen.doWhen(ruleDefinition, rulesFactory.rule());

                switch (ruleDefinition.getWhen().size()) {
                    case 1:
                        if (on instanceof Pattern1Def on1) {
                            execute(ruleDefinition, (Pattern1Def<Object>) on1, result);
                        } else if (on instanceof GroupByPattern1 on1) {
                            final GroupBy groupBy = (GroupBy) ruleDefinition.getWhen().get(0); // TODO 0?
                            execute(ruleDefinition, (GroupByPattern1<Object, Object, Object>) on1, result, groupBy);
                        }
                        // TODO 2 and 3
                }
            }
        });
    }

    private void execute(final YamlRule ruleDefinition,
                         final GroupByPattern1<Object, Object, Object> on,
                         final StoreHandle result,
                         final GroupBy groupBy) {
        on.execute(result, (storeHandle, grouper, group) -> {
            final Map<String, Object> context = new HashMap<>();
            context.put(groupBy.getGrouping().getAs(), grouper);
            context.put(groupBy.getAccumulators().getAs(), group);
            doThen.doThen(ruleDefinition, storeHandle, context);
        });
    }

    private void execute(final YamlRule ruleDefinition,
                         final Pattern1Def<Object> on,
                         final StoreHandle result) {
        on.execute(result, (storeHandle, a) -> {
            final Map<String, Object> context = new HashMap<>();
            if (ruleDefinition.getWhen().get(0) instanceof Given given) {
                context.put(given.getGiven(), a);
            }
            doThen.doThen(ruleDefinition, storeHandle, context);
        });
    }

    private StoreHandle getResult() {
        final StoreHandle<Object> result = StoreHandle.empty(Object.class);
        switch (resultType) {
            case "List":
                result.set(new ArrayList<>());
                return result;
            case "Map":
                result.set(new HashMap<>());
                return result;
            case "TreeMap":
                result.set(new TreeMap<>());
                return result;
            default:
                throw new IllegalStateException("Result type is not set correctly");
        }
    }
}
