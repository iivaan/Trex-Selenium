package com.paxovision.trex.selenium.api;

import com.paxovision.trex.selenium.guice.GuiceFactory;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface UIElement<T extends UIElement<T>> extends Element,Text{

    public static TestObjectFacade getInstance(By by){
        TestObjectFacade element = GuiceFactory.TestObjectGuiceModuleInjector.getInstance(TestObjectFacade.class).setBy(by);
        return element;
    }
    public static TestObjectFacade getInstance(SearchContext context, By by){
        TestObjectFacade element = GuiceFactory.TestObjectGuiceModuleInjector.getInstance(TestObjectFacade.class)
                                                .setSearchContext(context)
                                                .setBy(by);
        return element;
    }
    public static TestObjectFacade getInstance(WebElement resolvedElement, SearchContext context, By by){
        TestObjectFacade element = GuiceFactory.TestObjectGuiceModuleInjector.getInstance(TestObjectFacade.class)
                .setResolvedElement(resolvedElement)
                .setSearchContext(context)
                .setBy(by);
        return element;
    }

    ISelect select();
    IDeselect deselect();

    UIElement findElement(By by);
    List<UIElement> findElements(By by);

    T click();
    T submit();
    T sendKeys(CharSequence... charSequence);
    T clear();

    boolean hasClass(String cssClassName);
    boolean hasFocus();
    T setFocus();
    T setWindowFocus();

    T highlight();
    T setHighlightBorder(boolean highlight);

    T waitUntilEnabled();
    T waitUntilEnabled(int waitForTimeoutInMilliseconds);

    T waitUntilDisabled();
    T waitUntilDisabled(int waitForTimeoutInMilliseconds);

    T waitUntilClickable();
    T waitUntilClickable(int waitForTimeoutInMilliseconds);

    T waitUntilVisible();
    T waitUntilVisible(int waitForTimeoutInMilliseconds);

    T waitUntilPresence();
    T waitUntilPresence(int waitForTimeoutInMilliseconds);

    T waitUntilAttribute(String attribute,String value);
    T waitUntilAttribute(int waitForTimeoutInMilliseconds,String attribute,String value);

    T waitUntilToBeSelected();
    public T waitUntilToBeSelected(int waitForTimeoutInMilliseconds);

    T waitUntilSelectionStateToBe(boolean value);
    T waitUntilSelectionStateToBe(int waitForTimeoutInMilliseconds, boolean value);

    T waitUntilTextToBe(String value);
    T waitUntilTextToBe(int waitForTimeoutInMilliseconds, String value);

    T waitUntilValueToBe(String value);
    T waitUntilValueToBe(int waitForTimeoutInMilliseconds, String value);

    T waitUntilTextMatches(Pattern pattern);
    T waitUntilTextMatches(int waitForTimeoutInMilliseconds, Pattern pattern);

    T waitUntilInvisibility();
    T waitUntilInvisibility(int waitForTimeoutInMilliseconds);

    T shouldBePresent();
    T shouldNotBePresent();
    T shouldBeDisplayed();
    T shouldNotBeDisplayed();
    T shouldBeEnabled();
    T shouldNotBeEnabled();
    T shouldBeSelected();
    T shouldNotBeSelected();
    T shouldHaveFocus();
    T shouldNotHaveFocus();
    T shouldHaveText(String textExpected);
    T shouldHaveIgnoringCaseText(String textExpected);
    T shouldContainsText(String textExpected);
    T shouldContainsIgnoringCaseText(String textExpected);
    T shouldMatchesText(String textExpected);
    T shouldMatchesText(Pattern textExpected);
    T shouldMatchesText(Predicate<? super String > predicate);
    T shouldEqualToNormalizingWhitespaceText(String textExpected);
    T shouldEqualToIgnoringNewLinesText(String textExpected);
    T shouldEqualToIgnoringWhitespaceText(String textExpected);
    T shouldHaveValue(String valueExpected);
    T shouldHaveAttributeValue(String attribute, String attributeValueExpected);
    T shouldHaveCssValue(String css, String cssValueExpected);
    T shouldHaveClass(String className);
    T shouldNotHaveClass(String className);


    AbstractStringAssert<?> hasValue();
    AbstractStringAssert<?> hasText();
    AbstractStringAssert<?> hasTagName();
    AbstractStringAssert<?> hasAttribute(String attributeName);
    AbstractStringAssert<?> hasCssValue(String cssValueName);
    AbstractBooleanAssert<?> hasIsDisplayed();
    AbstractBooleanAssert<?> hasIsEnabled();
    AbstractBooleanAssert<?> hasIsSelected();
}
