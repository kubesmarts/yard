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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RulesMergeTest
        extends TestBase {

    private static final String FILE_NAME = "/rules/merge.yml";

    @Test
    public void testMerge() throws Exception {
        final String CTX = """
                {
                    "Jira 1":[
                        {"name":"Ticket 1", "status":"Blocking", "createdAt":"12-12-2000"},
                        {"name":"Ticket 2", "status":"Minor", "createdAt":"12-12-2000"}
                    ],
                    "Jira 2":[
                        {"name":"Ticket 3", "status":"Critical", "createdAt":"12-12-2000"},
                        {"name":"Ticket 4", "status":"Blocking", "createdAt":"12-12-2000"}
                    ]
                }
                """;
        final Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        final List<Map<String, Object>> o = (List<Map<String, Object>>) outputJSONasMap.get("Merged data from two ticket streams");

        assertThat(o.size()).isEqualTo(2);
        assertThat(o).contains(toTicketMap("Ticket 1"));
        assertThat(o).contains(toTicketMap("Ticket 4"));
        // 1 and 3
    }

    private Map<String, Object> toTicketMap(final String name) {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("status", "Blocking");
        map.put("createdAt", "12-12-2000");

        return map;
    }
}
