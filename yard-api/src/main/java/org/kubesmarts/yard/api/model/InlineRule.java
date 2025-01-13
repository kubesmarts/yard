package org.kubesmarts.yard.api.model;

import java.util.List;

public class InlineRule implements Rule {

    private final int rowNumber;
    public List def;

    public InlineRule(int rowNumber, List data) {
        this.rowNumber = rowNumber;
        this.def = data;
    }

    @Override
    public int getRowNumber() {
        return rowNumber;
    }

    public List getDef() {
        return def;
    }

    public void setDef(List def) {
        this.def = def;
    }
}
