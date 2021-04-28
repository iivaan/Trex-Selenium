package com.paxovision.trex.selenium.api;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    UIElement findElement(By by);
    List<UIElement> findElements(By by);

    T click();
    T submit();
    T sendKeys(CharSequence... charSequence);
    T clear();

    AbstractStringAssert<?> hasValue();
    AbstractStringAssert<?> hasText();
    AbstractStringAssert<?> hasTagName();
    AbstractStringAssert<?> hasAttribute(String attributeName);
    AbstractStringAssert<?> hasCssValue(String cssValueName);
    AbstractBooleanAssert<?> hasIsDisplayed();
    AbstractBooleanAssert<?> hasIsEnabled();
    AbstractBooleanAssert<?> hasIsSelected();
}
