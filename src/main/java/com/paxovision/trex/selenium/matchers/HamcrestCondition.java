package com.paxovision.trex.selenium.matchers;

import org.hamcrest.Matcher;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class HamcrestCondition <T> implements Condition<T>{
    private static final Matcher<Object> isTrueOrNonNull =
            not(anyOf(nullValue(), equalTo((Object)Boolean.FALSE)));

    private Callable<T> item;
    private Matcher<? super T> matcher;
    private T lastResult = null;

    private Supplier<String> description = () -> "";

    public static <T> HamcrestCondition<T> matchCallTo(Callable<T> item, Matcher<? super T> matcher) {
        return new HamcrestCondition<T>(item, matcher);
    }

    public static <T> HamcrestCondition<T> match(T item, Matcher<? super T> matcher) {
        return new HamcrestCondition<T>(new Callable<T>() {
            @Override public T call() { return item; }
            @Override public String toString() { return item.toString(); }
        }, matcher);
    }

    public static <T> HamcrestCondition<T> isTrueOrNonNull(Callable<T> item) {
        return new HamcrestCondition<T>(item, isTrueOrNonNull);
    }

    /**
     * Shortcut for a matcher that will match any value that is not null and not false.
     * @param item
     */
    public HamcrestCondition(Callable<T> item) {
        this(item, isTrueOrNonNull);
    }

    public HamcrestCondition(Callable<T> item, Matcher<? super T> matcher) {
        this.item = item;
        this.matcher = matcher;
    }

    @Override
    public boolean isMet() {
        try {
            lastResult = item.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ConditionEvaluationException(e);
        }

        return matcher.matches(lastResult);
    }

    @Override
    public T lastResult() {
        return lastResult;
    }

    @Override
    public Condition<T> describedAs(Supplier<String> description) {
        this.description = description;

        return this;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();

        String desc = description.get();
        desc = (!"".equals(desc) && desc != null)
                ? desc
                : "The item under examination is " + matcher;

        try {
            // Try to determine some more details
            boolean isMet = matcher.matches(lastResult);

            toString.append(desc).append(",\n");

            if(isMet) {
                toString.append("  as seen by last examined result, ");
            } else {
                toString.append("  however the last examined result was, ");
            }

            toString.append("\"").append(lastResult).append("\",\n")
                    .append(isMet ? "  which is " : "  which is not ").append(matcher);
        } catch (RuntimeException e) {
            // Couldn't get more details
            toString.append(desc).append(".");
        }

        return toString.toString();
    }

    public Callable<T> getSupplier() {
        return item;
    }

    public Matcher<? super T> getMatcher() {
        return matcher;
    }
}
