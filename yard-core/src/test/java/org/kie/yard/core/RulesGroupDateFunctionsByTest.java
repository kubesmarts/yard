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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class RulesGroupDateFunctionsByTest
        extends TestBase {

    private static final String FILE_NAME = "/rules/groupby-functions.yml";
    private String data = """
            {
                "Current Time": "12-Dec-2024",
                "Tickets":
                [
                    {"name":"Bug 1", "createdAt":"11-Dec-2024", "priority": "Critical"},
                    {"name":"Bug 2", "createdAt":"11-Dec-2024", "priority": "Major"},
                    {"name":"Bug 3", "createdAt":"5-Dec-2024", "priority": "Minor"},
                    {"name":"Bug 4", "createdAt":"10-Dec-2024", "priority": "Critical"},
                    {"name":"Bug 5", "createdAt":"1-Dec-2024", "priority": "Critical"}
                ]
            }
            """;

    @Test
    public void testGroupByAndSumByInitial() throws Exception {
        final Map<String, Object> outputJSONasMap = evaluate(data, FILE_NAME);
        final Map<String, Object> o = (Map<String, Object>) outputJSONasMap.get("Group week old tickets by type");

        assertThat(o.size()).isEqualTo(3);
        assertEquals(1,o.get("Major"));
        assertEquals(2,o.get("Critical"));
        assertEquals(1,o.get("Minor"));
    }

    @Test
    public void testGroupByAndSumByDate() throws Exception {
        final Map<String, Object> outputJSONasMap = evaluate(data, FILE_NAME);
        final Map<String, Object> o = (Map<String, Object>) outputJSONasMap.get("All Tickets group by Date");

        assertThat(o.size()).isEqualTo(4);
        assertThat(o.keySet()).containsExactly(
                "2024-12-01",
                "2024-12-05",
                "2024-12-10",
                "2024-12-11"
                );
        assertEquals(1,o.get("2024-12-01"));
        assertEquals(1,o.get("2024-12-05"));
        assertEquals(1,o.get("2024-12-10"));
        assertEquals(2,o.get("2024-12-11"));
    }
}
