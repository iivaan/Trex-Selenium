package com.paxovision.trex.selenium.exceptions;

public class NoRequiredElementsException extends TrexSeleniumException{
    public NoRequiredElementsException(Object view) {
        super("No fields that implement View, Element, Findable, or List of those types, were " +
                "configured to be required. Configure required elements by use of @Require, " +
                "@RequireAll, and @NotRequired annotations.\n" +
                "View analyzed was: " + view);
    }
}
