package com.paxovision.trex.selenium.exceptions;

import com.paxovision.trex.selenium.api.UIElement;

public class UIElementNotDisplayedException extends UIElementException{
    public UIElementNotDisplayedException(UIElement element) {
        super("Can't interact with an element if it is not displayed.\nElement was a " + element);
    }

    public UIElementNotDisplayedException(UIElement element, Throwable cause) {
        super("Can't interact with an element if it is not displayed.\nElement was a " + element, cause);
    }
}
