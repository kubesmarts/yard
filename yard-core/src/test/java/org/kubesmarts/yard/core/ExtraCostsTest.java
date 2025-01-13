package org.kubesmarts.yard.core;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtraCostsTest
        extends TestBase {

    private static final String FILE_NAME = "/extra-costs.yml";

    @Test
    public void testMPackage() throws Exception {
        final String CTX = """
                {
                    "Fragile":true,
                    "Package Tracking":true,
                    "Insurance":true,
                    "Package Type":"M"
                }
                """;
        Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        assertThat(outputJSONasMap).hasFieldOrPropertyWithValue("Total cost of premiums", 40);
    }
}
