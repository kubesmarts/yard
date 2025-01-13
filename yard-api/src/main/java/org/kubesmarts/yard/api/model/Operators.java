package org.kubesmarts.yard.api.model;

import java.util.Objects;

/**
 * Interface instead of enum due to possible custom operators.
 */
public interface Operators {

    String NOT_EQUALS = "!=";
    String EQUALS = "=";
    String GREATER_OR_EQUAL = ">=";
    String GREATER_THAN = ">";
    String LESS_OR_EQUAL = "<=";
    String LESS_THAN = "<";

    String[] ALL = {EQUALS, LESS_OR_EQUAL, LESS_THAN, GREATER_OR_EQUAL, GREATER_THAN, NOT_EQUALS};

    static int compare(final String operator,
                       final String other) {
        return getWeight(operator) - getWeight(other);
    }

    static int getWeight(final String operator) {
        for (int i = 0; i < ALL.length; i++) {
            if (Objects.equals(operator, ALL[i])) {
                return i;
            }
        }
        return 0;
    }
}
