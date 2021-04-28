package com.paxovision.trex.selenium.synq;

import java.time.Duration;

public class TimeoutException extends SynqException {
    private static final long serialVersionUID = 7194182399119358208L;
    
    public TimeoutException(Event<?> event, Duration duration) {
        super("Timed out after " + duration + " waiting for event to occur.\n" +
                "Event occurs when " + event.toString());
    }
}
