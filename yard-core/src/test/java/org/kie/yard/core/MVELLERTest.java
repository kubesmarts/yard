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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MVELLERTest {

    @Test
    void testSimple() {
        final Map<String, Object> context = new HashMap<>();
        final Map<Object, Object> value = new HashMap<>();
        value.put("name", "Ticket 3");
        value.put("status", "Critical");
        context.put("$ticket", value);
        final String expr = "$ticket.status == \"Blocking\"";
        final Object o = new MVELLER(QuotedExprParsed.from(expr)).doTheMVEL(context, new YaRDDefinitions(new HashMap<>(), new HashMap<>(), null, new HashMap<>()));
        assertEquals(false, o);
    }

    @Test
    void testFirstLetter() {
        final Map<String, Object> context = new HashMap<>();
        final Map<Object, Object> value = new HashMap<>();
        value.put("name", "Lars");
        value.put("age", "24");
        context.put("$p", value);
        final String expr = "$p.age >= 18";
        final Object o = new MVELLER(QuotedExprParsed.from(expr)).doTheMVEL(context, new YaRDDefinitions(new HashMap<>(), new HashMap<>(), null, new HashMap<>()));
        assertEquals(true, o);
    }

    @Test
    void testDate() {
        final Map<String, Object> context = new HashMap<>();
        final String expr = "date('20-Dec-2024').month";
        final Object o = new MVELLER(QuotedExprParsed.from(expr)).doTheMVEL(context, new YaRDDefinitions(new HashMap<>(), new HashMap<>(), null, new HashMap<>()));
        assertEquals("DECEMBER", o.toString());
    }

    @Test
    void testDuration() {
        final Map<String, Object> context = new HashMap<>();
        final String expr = "Duration.between(" +
                "               date('10-Jan-2022').atStartOfDay(), " +
                "               date('15-Jan-2022').atStartOfDay()).toDays()";
        final Object o = new MVELLER(QuotedExprParsed.from(expr)).doTheMVEL(context, new YaRDDefinitions(new HashMap<>(), new HashMap<>(), null, new HashMap<>()));
        assertEquals("5", o.toString());
    }
}