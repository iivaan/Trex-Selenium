package com.paxovision.trex.selenium.api;

import com.google.inject.Inject;
import com.paxovision.trex.selenium.exceptions.UIElementNotDisplayedException;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class AbstractTestObject <T extends UIElement<T>> implements UIElement<T>{
    public static int DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS = 1000;
    public static boolean ENABLE_HIGHLIGHT = false;
    protected final T SELF;
    protected WebElement resolvedElement = null;

    private static final int WAIT_FOR_ELEMENT_PAUSE_LENGTH = 100;
    private final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;;
    private final Clock driverClock = Clock.systemDefaultZone();;

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

    public String getValue() {
        return getAttribute("value");
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


    @Override
    public boolean isSelected() {
        return attemptAndGet(WebElement::isSelected);
    }
    @Override
    public boolean isNotSelected() {
        return !isSelected();
    }

    @Override
    public boolean isEnabled() {
        return attemptAndGet(WebElement::isEnabled);
    }
    @Override
    public boolean isNotEnabled() {
        return !isEnabled();
    }

    @Override
    public boolean isDisplayed() {
        try {
            return attemptAndGet(WebElement::isDisplayed);
        } catch (NotFoundException e) {
            return false;
        }
    }
    @Override
    public boolean isNotDisplayed() {
        return !isDisplayed();
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
    @Override
    public boolean isNotPresent() {
        return !isPresent();
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
        if(isDisplayed()) {
            JavascriptExecutor js = null;
            for (int i = 0; i < 2; i++) {
                try {
                    if (searchContext instanceof RemoteWebElement) {
                        js = (JavascriptExecutor) ((RemoteWebElement) searchContext).getWrappedDriver();
                    } else {
                        js = (JavascriptExecutor) searchContext;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                if (js != null) {
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", resolvedElement, "border: 2px solid red;");
                    delayFor(100);
                    js.executeScript(
                            "arguments[0].setAttribute('style', arguments[1]);",
                            resolvedElement, "");
                    delayFor(100);
                }
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


    public T shouldBePresent(){
        Assertions.assertThat(isPresent()).as("Element [" + by.toString() + "] should be present").isTrue();
        return SELF;
    }
    public T shouldNotBePresent(){
        Assertions.assertThat(!isPresent()).as("Element [" + by.toString() + "] should not be present").isTrue();
        return SELF;
    }

    public T shouldBeDisplayed(){
        Assertions.assertThat(isDisplayed()).as("Element [" + by.toString() + "] should be displayed").isTrue();
        return SELF;
    }
    public T shouldNotBeDisplayed(){
        Assertions.assertThat(!isDisplayed()).as("Element [" + by.toString() + "] should not be displayed").isTrue();
        return SELF;
    }

    public T shouldBeEnabled(){
        Assertions.assertThat(isEnabled()).as("Element [" + by.toString() + "] should be enabled").isTrue();
        return SELF;
    }
    public T shouldNotBeEnabled(){
        Assertions.assertThat(!isEnabled()).as("Element [" + by.toString() + "] should not be enabled").isTrue();
        return SELF;
    }

    public T shouldBeSelected(){
        Assertions.assertThat(isSelected()).as("Element [" + by.toString() + "] should be selected").isTrue();
        return SELF;
    }
    public T shouldNotBeSelected(){
        Assertions.assertThat(!isSelected()).as("Element [" + by.toString() + "] should not be selected").isTrue();
        return SELF;
    }

    public T shouldHaveText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should have text").isEqualTo(textExpected);
        return SELF;
    }
    public T shouldHaveIgnoringCaseText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should have ignoring case text").isEqualToIgnoringCase(textExpected);
        return SELF;
    }
    public T shouldContainsText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should contains text").contains(textExpected);
        return SELF;
    }
    public T shouldContainsIgnoringCaseText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should contains ignoring case text").containsIgnoringCase(textExpected);
        return SELF;
    }
    public T shouldMatchesText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should matches text").matches(textExpected);
        return SELF;
    }
    public T shouldMatchesText(Pattern textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should matches text").matches(textExpected);
        return SELF;
    }
    public T shouldMatchesText(Predicate<? super String > predicate){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should matches text").matches(predicate);
        return SELF;
    }
    public T shouldEqualToNormalizingWhitespaceText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should equal to normalizing whitespace text").isEqualToNormalizingWhitespace(textExpected);
        return SELF;
    }
    public T shouldEqualToIgnoringNewLinesText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should equal to ignoring new lines text").isEqualToIgnoringNewLines(textExpected);
        return SELF;
    }
    public T shouldEqualToIgnoringWhitespaceText(String textExpected){
        Assertions.assertThat(getText()).as("Element [" + by.toString() + "] should equal to ignoring white space text").isEqualToIgnoringWhitespace(textExpected);
        return SELF;
    }


    public T shouldHaveValue(String valueExpected){
        Assertions.assertThat(getValue()).as("Element [" + by.toString() + "] should have value").isEqualTo(valueExpected);
        return SELF;
    }

    public T shouldHaveAttributeValue(String attribute, String attributeValueExpected){
        Assertions.assertThat(getAttribute(attribute)).as("Element [" + by.toString() + "] should have attribute(" + attribute + ") value").isEqualTo(attributeValueExpected);
        return SELF;
    }
    public T shouldHaveCssValue(String css, String cssValueExpected){
        Assertions.assertThat(getCssValue(css)).as("Element [" + by.toString() + "] should have css(" + css + ") value").isEqualTo(cssValueExpected);
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


    public T waitUntilEnabled(){
        waitUntilEnabled(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS);
        return SELF;
    }
    public T waitUntilEnabled(int waitForTimeoutInMilliseconds){
        if(isPresent()) {
            try {
                waitForCondition(waitForTimeoutInMilliseconds).until((ExpectedCondition<Boolean>) driver -> resolvedElement.isEnabled());
            }
            catch (TimeoutException ex){
                    throw new TimeoutException("Element [" + by.toString() + "] is not enabled");
            }
        }
        else{
            throw new ElementNotVisibleException(by.toString() + " not present");
        }
        return SELF;
    }

    public T waitUntilDisabled(){
        waitUntilDisabled(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS);
        return SELF;
    }
    public T waitUntilDisabled(int waitForTimeoutInMilliseconds){
        if(isPresent()) {
            try {
                waitForCondition(waitForTimeoutInMilliseconds).until((ExpectedCondition<Boolean>) driver -> !resolvedElement.isEnabled());
            }
            catch (TimeoutException ex){
                throw new TimeoutException("Element [" + by.toString() + "] is not disable");
            }
        }
        else{
            throw new ElementNotVisibleException(by.toString() + " not present");
        }
        return SELF;
    }


    public T waitUntilClickable(){
        resolvedElement = waitForCondition(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS).until(ExpectedConditions.elementToBeClickable(by));
        return SELF;
    }
    public T waitUntilClickable(int waitForTimeoutInMilliseconds){
        resolvedElement = waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.elementToBeClickable(by));
        return SELF;
    }

    public T waitUntilVisible(){
        resolvedElement = waitForCondition(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS).until(ExpectedConditions.visibilityOfElementLocated(by));
        return SELF;
    }
    public T waitUntilVisible(int waitForTimeoutInMilliseconds){
        resolvedElement = waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.visibilityOfElementLocated(by));
        return SELF;
    }

    public T waitUntilPresence(){
        resolvedElement = waitForCondition(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS).until(ExpectedConditions.presenceOfElementLocated(by));
        return SELF;
    }
    public T waitUntilPresence(int waitForTimeoutInMilliseconds){
        resolvedElement = waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.presenceOfElementLocated(by));
        return SELF;
    }

    public T waitUntilAttribute(String attribute,String value){
        waitUntilAttribute(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS, attribute, value);
        return SELF;
    }
    public T waitUntilAttribute(int waitForTimeoutInMilliseconds,String attribute,String value){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.attributeContains(by,attribute,value));
        return SELF;
    }

    public T waitUntilToBeSelected(){
        waitUntilToBeSelected(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS);
        return SELF;
    }
    public T waitUntilToBeSelected(int waitForTimeoutInMilliseconds){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.elementToBeSelected(by));
        return SELF;
    }

    public T waitUntilSelectionStateToBe(boolean value){
        waitUntilSelectionStateToBe(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS,value);
        return SELF;
    }
    public T waitUntilSelectionStateToBe(int waitForTimeoutInMilliseconds, boolean value){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.elementSelectionStateToBe(by, value));
        return SELF;
    }

    public T waitUntilTextToBe(String value){
        waitUntilTextToBe(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS,value);
        return SELF;
    }
    public T waitUntilTextToBe(int waitForTimeoutInMilliseconds, String value){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.textToBe(by, value));
        return SELF;
    }

    public T waitUntilValueToBe(String value){
        waitUntilValueToBe(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS,value);
        return SELF;
    }
    public T waitUntilValueToBe(int waitForTimeoutInMilliseconds, String value){
        waitUntilAttribute(waitForTimeoutInMilliseconds,"value",value);
        return SELF;
    }

    public T waitUntilTextMatches(Pattern pattern){
        waitUntilTextMatches(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS,pattern);
        return SELF;
    }
    public T waitUntilTextMatches(int waitForTimeoutInMilliseconds, Pattern pattern){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.textMatches(by,pattern));
        return SELF;
    }

    public T waitUntilInvisibility(){
        waitUntilInvisibility(DEFAULT_WAIT_FOR_TIMEOUT_IN_MILLISECONDS);
        return SELF;
    }
    public T waitUntilInvisibility(int waitForTimeoutInMilliseconds){
        waitForCondition(waitForTimeoutInMilliseconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
        return SELF;
    }

    //@Override
    private Wait<WebDriver> waitForCondition(int waitForTimeoutInMilliseconds) {
        Objects.requireNonNull(searchContext,"SearchContext is required.");

        return new FluentWait<>((WebDriver)searchContext, driverClock, sleeper)
                .withTimeout(Duration.ofMillis(waitForTimeoutInMilliseconds))
                .pollingEvery(Duration.ofMillis(WAIT_FOR_ELEMENT_PAUSE_LENGTH))
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
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

    private WebElement waitForWebElement() {
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
