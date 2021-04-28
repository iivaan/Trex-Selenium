package com.paxovision.trex.selenium.api;

import com.google.inject.Inject;
import com.paxovision.trex.selenium.exceptions.UIElementNotDisplayedException;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractTestObject <T extends UIElement<T>> implements UIElement<T>{
    public static boolean ENABLE_HIGHLIGHT = false;
    protected final T SELF;
    protected WebElement resolvedElement = null;

    @Inject
    protected SearchContext searchContext = null;
    protected By by = null;


    protected AbstractTestObject(final Class<T> selfClass){
        SELF = selfClass.cast(this);
    }

    T setResolvedElement(WebElement resolvedElement) {
        this.resolvedElement = resolvedElement;
        return SELF;
    }

    T setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
        return SELF;
    }

    T setBy(By by) {
        this.by = by;
        return SELF;
    }

    public UIElement findElement(By by){
        UIElement element = UIElement.getInstance(webElement(),by);
        return  element;
    }
    public List<UIElement> findElements(By by) {
        List<UIElement> list = UIElements.getInstance(webElement(), by);
        return list;
    }

    public T click(){
        attempt(WebElement::click);
        return SELF;
    }

    public T submit() {
        attempt(WebElement::submit);
        return (T) this;
    }

    public T clear() {
        attempt(WebElement::clear);
        return SELF;
    }

    public T sendKeys(CharSequence... charSequence){
        attempt(e -> e.sendKeys(charSequence));
        return SELF;
    }

    public String getText() {
        return attemptAndGet(WebElement::getText);
    }

    public String getTagName() {
        return attemptAndGet(WebElement::getTagName);
    }


    public String getAttribute(String attribute) {
        return attemptAndGet(e -> e.getAttribute(attribute));
    }
    public String getCssValue(String property) {
        return attemptAndGet(e -> e.getCssValue(property));
    }


    public boolean isSelected() {
        return attemptAndGet(WebElement::isSelected);
    }


    public boolean isEnabled() {
        return attemptAndGet(WebElement::isEnabled);
    }



    public boolean isDisplayed() {
        getResolvedElement();
        if(resolvedElement != null){
            return true;
        }
        else{
            return false;
        }
    }

    //TODO: how we fix it
    @Override
    public boolean isPresent() {
        try {
            getResolvedElement();
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }



    public Point getLocation() {
        return attemptAndGet(WebElement::getLocation);
    }

    public Dimension getSize() {
        return attemptAndGet(WebElement::getSize);
    }

    public Rectangle getRect() {
        return attemptAndGet(WebElement::getRect);
    }

    public <X> X getScreenshotAs(OutputType<X> outputType)  {
        return attemptAndGet(e -> e.getScreenshotAs(outputType));
    }




    public T highlight() {
        JavascriptExecutor js = null;
        for (int i = 0; i < 2; i++) {
            try {
                if (searchContext instanceof RemoteWebElement) {
                    js = (JavascriptExecutor) ((RemoteWebElement) searchContext).getWrappedDriver();
                } else {
                    js = (JavascriptExecutor) searchContext;
                }
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            if( js != null) {
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", resolvedElement, "border: 2px solid red;");
                delayFor(100);
                js.executeScript(
                        "arguments[0].setAttribute('style', arguments[1]);",
                        resolvedElement, "");
                delayFor(100);
            }
        }
        return SELF;
    }

    public T setHighlightBorder(boolean highlight) {
        JavascriptExecutor js = null;

        try {
            if (searchContext instanceof RemoteWebElement) {
                js = (JavascriptExecutor) ((RemoteWebElement) searchContext).getWrappedDriver();
            } else {
                js = (JavascriptExecutor) searchContext;
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        if( js != null && highlight) {
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", resolvedElement, "border: 2px solid red;");
        }
        else if( js != null && !highlight) {
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", resolvedElement, "");
        }

        return SELF;
    }


    public T and(){
        return SELF;
    }
    public T then() {
        return SELF;
    }


    public AbstractStringAssert<?> hasValue(){
        return Assertions.assertThat(this.getAttribute("value")).as("Has test object attribute value");
    }
    public AbstractStringAssert<?> hasText(){
        return Assertions.assertThat(this.getText()).as("Has test object text");
    }
    public AbstractStringAssert<?> hasTagName(){
        return Assertions.assertThat(this.getTagName()).as("Has test object tag name");
    }
    public AbstractStringAssert<?> hasAttribute(String attributeName){
        return Assertions.assertThat(this.getAttribute(attributeName)).as("Has test object attribute " + attributeName);
    }
    public AbstractStringAssert<?> hasCssValue(String cssValueName){
        return Assertions.assertThat(this.getCssValue(cssValueName)).as("Has test object CSS " + cssValueName);
    }
    public AbstractBooleanAssert<?> hasIsDisplayed(){
        return Assertions.assertThat(this.isDisplayed()).as("Has test object isDisplayed");
    }
    public AbstractBooleanAssert<?> hasIsEnabled(){
        return Assertions.assertThat(this.isEnabled()).as("Has test object isEnabled");
    }
    public AbstractBooleanAssert<?> hasIsSelected(){
        return Assertions.assertThat(this.isSelected()).as("Has test object isSelected");
    }




    protected void invalidateCache() {
        resolvedElement = null;
    }

    protected void delayFor(int timeInMili){
        try {
            Thread.sleep(timeInMili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Expected way for sub element types to interact with their corresponding WebElement. This
     * attempts the desired action, and will throw appropriate exceptions should the element not be
     * able to be interacted with, for whatever reason. If the WebElement is stale when the action
     * is attempted, the cached WebElement will be cleared and looked up again, which may lookup a
     * fresh reference to the equivalent element.
     *
     * @param action A function that wraps the action to be performed. Accepts the source WebElement
     * as its only parameter and returns nothing.
     */
    protected void attempt(Consumer<WebElement> action) {
        try {
            action.accept(webElement());
        } catch (StaleElementReferenceException e) {
            invalidateCache();
            try {
                action.accept(webElement());
            } catch (ElementNotVisibleException e1) {
                throw new UIElementNotDisplayedException(this, e1);
            }
        } catch (ElementNotVisibleException e) {
            throw new UIElementNotDisplayedException(this, e);
        }
    }

    /**
     * Expected way for sub element types to interact with their corresponding WebElement. This
     * attempts the desired action, and will throw appropriate exceptions should the element not be
     * able to be interacted with, for whatever reason. If the WebElement is stale when the action
     * is attempted, the cached WebElement will be cleared and looked up again, which may lookup a
     * fresh reference to the equivalent element.
     *
     * @param action A function that wraps the action to be performed. Accepts the source WebElement
     * as its only parameter and returns the result of this action.
     * @param <T> Return type of the action.
     * @return Whatever the action returns.
     */
    protected <T> T attemptAndGet(Function<WebElement, T> action) {
        try {
            return action.apply(webElement());
        } catch (StaleElementReferenceException e) {
            invalidateCache();

            try {
                return action.apply(webElement());
            } catch (ElementNotVisibleException e1) {
                throw new UIElementNotDisplayedException(this, e1);
            }
        } catch (ElementNotVisibleException e) {
            throw new UIElementNotDisplayedException(this, e);
        }
    }

    private WebElement webElement() {
        if (resolvedElement == null) {
            try {
                getResolvedElement();
            } catch (NotFoundException e) {
                //TODO: fix the exception
                throw new NotFoundException(e);
            }
        }

        return resolvedElement;
    }

    protected void getResolvedElement() {
        Objects.requireNonNull(searchContext,"SearchContext is required.");
        Objects.requireNonNull(by,"By is required.");

        if (resolvedElement == null) {
            resolvedElement = searchContext.findElement(by);
            if (ENABLE_HIGHLIGHT)  highlight();
        }
        else{
            try {
                resolvedElement.isDisplayed();
            }
            catch (StaleElementReferenceException ex){
                System.out.println(ex.getMessage());
                System.out.println("Refreshing object...");
                resolvedElement = searchContext.findElement(by);
                if (ENABLE_HIGHLIGHT)  highlight();
            }
        }
    }

}
