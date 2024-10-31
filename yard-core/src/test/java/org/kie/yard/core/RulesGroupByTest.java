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

public class RulesGroupByTest
        extends TestBase {

    private static final String FILE_NAME = "/rules/groupby.yml";
    private String data = """
            {
                "Persons":
                [
                    {"name":"Toni", "age":"12", "kids": "0"},
                    {"name":"Lars", "age":"24", "kids": "0"},
                    {"name":"David", "age":"29", "kids": "2"},
                    {"name":"Eric", "age":"21", "kids": "1"},
                    {"name":"Jens", "age":"40", "kids": "2"},
                    {"name":"Jens", "age":"44", "kids": "2"},
                    {"name":"John", "age":"104", "kids": "3"}
                ]
            }
            """;

    @Test
    public void testGroupByAndSumByInitial() throws Exception {
        final Map<String, Object> outputJSONasMap = evaluate(data, FILE_NAME);
        final Map<String, Object> o = (Map<String, Object>) outputJSONasMap.get("Kids by parent initials");

        assertThat(o.size()).isEqualTo(4);
        assertEquals(7,o.get("J"));
        assertEquals(0,o.get("L"));
        assertEquals(2,o.get("D"));
        assertEquals(1,o.get("E"));
    }

    @Test
    public void testCountByDecade() throws Exception {
        final Map<String, Object> outputJSONasMap = evaluate(data, FILE_NAME);
        final Map<String, Object> o = (Map<String, Object>) outputJSONasMap.get("Parent count by decade");

        assertThat(o.size()).isEqualTo(3);
        assertEquals(2,o.get("20"));
        assertEquals(1,o.get("100"));
        assertEquals(2,o.get("40"));
    }
}
