package com.paxovision.trex.selenium.exceptions;

public class TrexSeleniumException extends RuntimeException{
    private static final long serialVersionUID = 2402576231289911388L;

    public TrexSeleniumException() {
        super();
    }

    public TrexSeleniumException(String message) {
        super(message);
    }

    public TrexSeleniumException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrexSeleniumException(Throwable cause) {
        super(cause);
    }
}
