package org.kubesmarts.yard.core;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InsuranceBasePriceTest
        extends TestBase {

    private static final String FILE_NAME = "/insurance-base-price.yml";

    @Test
    public void testScenario1() throws Exception {
        final String CTX = """
                {
                  "Age": 47,
                  "Previous incidents?": false
                }
                """;
        Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        assertThat(outputJSONasMap).hasFieldOrPropertyWithValue("Base price", 500);
        assertThat(outputJSONasMap).hasFieldOrPropertyWithValue("Downpayment", 50);
    }

    @Test
    public void testScenario2() throws Exception {
        final String CTX = """
                {
                  "Age": 19,
                  "Previous incidents?": true
                }
                """;
        Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        assertThat(outputJSONasMap).hasFieldOrPropertyWithValue("Base price", 1000);
        assertThat(outputJSONasMap).hasFieldOrPropertyWithValue("Downpayment", 70);
    }
}
