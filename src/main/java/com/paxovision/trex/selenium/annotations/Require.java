package com.paxovision.trex.selenium.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Require {
    int DEFAULT_AT_LEAST = 1;
    int DEFAULT_AT_MOST = Integer.MAX_VALUE;
    int DEFAULT_EXACTLY = -1;

    /**
     * Defaults to 1. Ignored for fields that are not {@link java.util.List Lists}. For list fields,
     * when evaluating conditions against required lists, the amount of elements in the list that
     * satisfy the condition must be at least this value.
     */
    int atLeast() default DEFAULT_AT_LEAST;

    /**
     * Defaults to {@link Integer#MAX_VALUE}. Ignored for fields that are not
     * {@link java.util.List Lists}. For list fields, when evaluating conditions against required
     * lists, the amount of elements in the list that satisfy the condition must be at most this
     * value.
     */
    int atMost() default DEFAULT_AT_MOST;

    /**
     * Ignored by default and for fields that are not {@link java.util.List Lists}. If set to a
     * non-negative integer, {@link #atLeast} and {@link #atMost} are ignored, and the amount of
     * elements in the list that satisfy the condition must be exactly this value.
     */
    int exactly() default DEFAULT_EXACTLY;
}
