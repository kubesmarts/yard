package org.kubesmarts.yard.core;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.drools.util.IoUtils;

public class TestBase {

    private JsonMapper jsonMapper = JsonMapper.builder().build();

    protected Map<String, Object> evaluate(String jsonInputCxt, String file) throws Exception {
        final String yamlDecision = read(file);
        final String OUTPUT_JSON = new YaRDRunner(yamlDecision).evaluate(jsonInputCxt);
        return readJSON(OUTPUT_JSON);
    }

    private String read(String file) throws IOException {
        return new String(IoUtils.readBytesFromInputStream(this.getClass().getResourceAsStream(file), true));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> readJSON(final String CONTEXT) throws JsonProcessingException {
        return jsonMapper.readValue(CONTEXT, Map.class);
    }
}
