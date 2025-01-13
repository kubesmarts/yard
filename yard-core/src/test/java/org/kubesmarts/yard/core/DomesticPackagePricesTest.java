package org.kubesmarts.yard.core;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomesticPackagePricesTest
        extends TestBase {

    private static final String FILE_NAME = "/domestic-package-prices.yml";

    @Test
    public void testMPackage() throws Exception {
        final String CTX = """
                {
                    "Height":10,
                    "Width":10,
                    "Length": 10,
                    "Weight":10
                }
                """;
        Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        assertEquals(6.9, ((Map) outputJSONasMap.get("Package")).get("Cost"));
        assertEquals("M", ((Map) outputJSONasMap.get("Package")).get("Size"));
    }

    @Test
    public void testLPackage() throws Exception {
        final String CTX = """
                {
                    "Height":12,
                    "Width":10,
                    "Length": 10,
                    "Weight":10
                }
                """;
        Map<String, Object> outputJSONasMap = evaluate(CTX, FILE_NAME);
        assertEquals(8.9, ((Map) outputJSONasMap.get("Package")).get("Cost"));
        assertEquals("L", ((Map) outputJSONasMap.get("Package")).get("Size"));
    }
}
