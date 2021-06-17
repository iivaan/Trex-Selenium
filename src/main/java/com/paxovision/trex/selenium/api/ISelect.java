package com.paxovision.trex.selenium.api;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ListAssert;

import java.util.List;

public interface ISelect {
    boolean isMultiple();

    List<UIElement<?>> getOptions();

    List<UIElement<?>> getAllSelectedOptions();

    UIElement<?> getFirstSelectedOption();

    SelectUIElement selectAll();

    SelectUIElement selectByVisibleText(String visibleText);

    SelectUIElement selectByIndex(int index);

    SelectUIElement selectByValue(String value);

    SelectUIElement shouldHaveOptions(String... expectedOptions);
    SelectUIElement shouldHaveSelectedOptions(String... expectedOptions);
    SelectUIElement shouldHaveFirstSelectedOption(String expectedOptions);
    SelectUIElement shouldHaveMultipleSelectionOption(boolean expectedOptions);

    ListAssert<String> hasOptions();
    ListAssert<String> hasSelectedOptions();
    AbstractStringAssert<?> hasFirstSelectedOption();
    AbstractBooleanAssert<?> hasMultipleSelectionOption();

}
