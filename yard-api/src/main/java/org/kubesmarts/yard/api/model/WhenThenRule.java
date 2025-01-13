package org.kubesmarts.yard.api.model;

import java.util.List;

public class WhenThenRule implements Rule {

    private final int rowNumber;
    private List when;
    private Object then;

    public WhenThenRule(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public int getRowNumber() {
        return rowNumber;
    }

    public List getWhen() {
        return when;
    }

    public Object getThen() {
        return then;
    }

    public void setWhen(List when) {
        this.when = when;
    }

    public void setThen(Object then) {
        this.then = then;
    }
}
