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

import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class MVELLER {

    private final String DATE_FUNCTION = """
            import java.time.Duration;
            import java.time.LocalDate;
            def date(text) {
                java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern(org.drools.util.DateUtils.getDateFormatMask());
                return LocalDate.parse(text, format);
            }
            """;

    private final QuotedExprParsed expr;

    public MVELLER(final QuotedExprParsed expr) {
        this.expr = expr;
    }

    public Object doTheMVEL(final Map<String, Object> context,
                            final YaRDDefinitions units) {

        final Map<String, Object> internalContext = new HashMap<>();
        internalContext.putAll(context);

        for (Map.Entry<String, Object> rawInput: units.rawInputs().entrySet()) {
            internalContext.put(QuotedExprParsed.escapeIdentifier(rawInput.getKey()),rawInput.getValue());
        }

        for (Map.Entry<String, StoreHandle<Object>> outKV : units.outputs().entrySet()) {
            if (!outKV.getValue().isValuePresent()) {
                continue;
            }
            internalContext.put(QuotedExprParsed.escapeIdentifier(outKV.getKey()), outKV.getValue().get());
        }

        try {
            final String expression = new StringBuilder()
                    .append(DATE_FUNCTION)
                    .append(expr.getRewrittenExpression()).toString();
            final Object eval = MVEL.eval(expression, internalContext);
            return eval;
        } catch (Exception e) {
            throw new RuntimeException("interpretation failed at runtime", e);
        }
    }
}
