package com.paxovision.trex.selenium.exceptions;

public class UIElementException extends TrexSeleniumException{
    public UIElementException() {
    }

    public UIElementException(String message) {
        super(message);
    }

    public UIElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIElementException(Throwable cause) {
        super(cause);
    }
}
