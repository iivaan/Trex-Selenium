package com.paxovision.trex.selenium.matchers;

import org.jooq.lambda.fi.util.function.CheckedPredicate;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Condition<T>  {
    /**
     * Evaluates the condition, which may include any arbitrary computation(s). The result of that
     * computation under test is typed by T, and available via {@link #lastResult()}.
     * <P>
     * Determining whether or not the condition was met is simply a matter of overriding this
     * function and returning true or false based on whatever logic you want. You can make some
     * interesting object available related to that logic by overriding {@link #lastResult()}.
     *
     * @return True if the condition was met. False if not.
     * @throws ConditionEvaluationException if a checked exception occurred while
     * trying to determine if the condition was met.
     */
    boolean isMet();

    /**
     * Requires the condition be first evaluated via {@link #isMet()}.
     *
     * @return The last value returned by the computation under test, or a reference to the object
     * under examination.
     * @throws IllegalStateException if the test was not yet run via {@link #isMet()}, or the
     * previous test failed.
     */
    T lastResult();

    default Condition<T> describedAs(String description) {
        return describedAs(() -> description);
    }

    //@Experimental
    Condition<T> describedAs(Supplier<String> description);

    static <T> Condition<T> matchCallTo(Callable<T> item, CheckedPredicate<? super T> predicate) {
        return new AbstractCondition<T>() {
            private T lastResult = null;

            @Override
            public boolean isMet() {
                try {
                    lastResult = item.call();
                    return predicate.test(lastResult);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ConditionEvaluationException(e);
                } catch (Throwable throwable) {
                    throw new ConditionEvaluationException(throwable);
                }
            }

            @Override
            public T lastResult() {
                return lastResult;
            }

        }.describedAs(() -> item + " to satisfy " + predicate);
    }

    static <T> Condition<T> match(T item, CheckedPredicate<? super T> predicate) {
        return matchCallTo(new Callable<T>() {

            @Override
            public T call() throws Exception {
                return item;
            }

            @Override
            public String toString() {
                return item.toString();
            }

        }, predicate);
    }
}
