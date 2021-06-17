package com.paxovision.trex.selenium.api;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ListAssert;

import java.util.List;

public interface IDeselect {
    boolean isMultiple();

    List<UIElement<?>> getOptions();

    List<UIElement<?>> getAllSelectedOptions();

    UIElement<?> getFirstSelectedOption();

    SelectUIElement deselectAll();

    SelectUIElement deselectByValue(String value);

    SelectUIElement deselectByIndex(int index);

    SelectUIElement deselectByVisibleText(String visibleText);

    SelectUIElement shouldHaveOptions(String... expectedOptions);
    SelectUIElement shouldHaveSelectedOptions(String... expectedOptions);
    SelectUIElement shouldHaveFirstSelectedOption(String expectedOptions);
    SelectUIElement shouldHaveMultipleSelectionOption(boolean expectedOptions);

    ListAssert<String> hasOptions();
    ListAssert<String> hasSelectedOptions();
    AbstractStringAssert<?> hasFirstSelectedOption();
    AbstractBooleanAssert<?> hasMultipleSelectionOption();
}
