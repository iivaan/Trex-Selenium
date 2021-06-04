package com.paxovision.trex.selenium.matchers;

public class ConditionEvaluationException extends RuntimeException{
    public ConditionEvaluationException(String message) {
        super(message);
    }

    public ConditionEvaluationException(Throwable cause) {
        super(cause);
    }

    public ConditionEvaluationException(String message, Throwable cause) {
        super(message, cause);

    }
}
