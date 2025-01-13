package org.kubesmarts.yard.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DublicateElementNameTest
        extends TestBase {

    private static final String FILE_NAME = "/two-elements-with-duplicate-name.yml";

    @Test
    public void testNoDublicateNames() throws Exception {
        final String CTX = """
                {
                    "Age": 10
                }
                """;
        assertThrows(IllegalArgumentException.class, () ->
                evaluate(CTX, FILE_NAME)
        );
    }
}
