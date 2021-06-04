package com.paxovision.trex.selenium.matchers;

import java.util.function.Supplier;

public abstract class AbstractCondition <T> implements Condition<T> {
    private Supplier<String> description = super::toString;

    @Override
    public Condition<T> describedAs(Supplier<String> description) {
        this.description = description;

        return this;
    }

    @Override
    public String toString() {
        return description.get();
    }
}
