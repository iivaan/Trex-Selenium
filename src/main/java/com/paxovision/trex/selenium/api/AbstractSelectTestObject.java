package com.paxovision.trex.selenium.api;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSelectTestObject implements SelectUIElement{
    UIElement<?> element;

    public AbstractSelectTestObject(UIElement<?> element){
        element.hasTagName().containsIgnoringCase("select");
        this.element = element;
    }

    protected Select select(){
        return new Select(((AbstractTestObject)element).getResolvedElement());
    }

    @Override
    public boolean isMultiple() {
        return select().isMultiple();
    }

    @Override
    public List<UIElement<?>> getOptions() {
        List<UIElement<?>> result = new ArrayList<>();
        List<WebElement> list = select().getOptions();
        for (WebElement item : list){
            By by = By.xpath("//select/option[@text()='" + item.getText() + "']");
            WebElement context =  ((AbstractTestObject)element).getResolvedElement();
            result.add(UIElement.getInstance(item,context,by));
        }
        return result;
    }

    @Override
    public List<UIElement<?>> getAllSelectedOptions() {
        List<UIElement<?>> result = new ArrayList<>();
        List<WebElement> list = select().getAllSelectedOptions();
        for (WebElement item : list){
            By by = By.xpath("//select/option[@text()='" + item.getText() + "']");
            WebElement context =  ((AbstractTestObject)element).getResolvedElement();
            result.add(UIElement.getInstance(item,context,by));
        }
        return result;
    }

    @Override
    public UIElement<?> getFirstSelectedOption() {
        UIElement<?> result = null;
        WebElement item = select().getFirstSelectedOption();
        if(item != null){
            By by = By.xpath("//select/option[@text()='" + item.getText() + "']");
            WebElement context =  ((AbstractTestObject)element).getResolvedElement();
            result =UIElement.getInstance(item,context,by);
        }
        return result;
    }

    @Override
    public SelectUIElement selectAll() {
        List<WebElement> list = select().getOptions();
        list.forEach(
                option -> setSelected(option, true)
        );
        return this;
    }

    @Override
    public SelectUIElement selectByVisibleText(String visibleText) {
        select().selectByVisibleText(visibleText);
        return this;
    }

    @Override
    public SelectUIElement selectByIndex(int index) {
        select().selectByIndex(index);
        return this;
    }

    @Override
    public SelectUIElement selectByValue(String value) {
        select().selectByValue(value);
        return this;
    }

    @Override
    public SelectUIElement deselectAll() {
        select().deselectAll();
        return this;
    }

    @Override
    public SelectUIElement deselectByValue(String value) {
        select().deselectByValue(value);
        return this;
    }

    @Override
    public SelectUIElement deselectByIndex(int index) {
        select().deselectByIndex(index);
        return this;
    }

    @Override
    public SelectUIElement deselectByVisibleText(String visibleText) {
        select().deselectByVisibleText(visibleText);
        return this;
    }


    public SelectUIElement shouldHaveOptions(String... expectedOptions){
        hasOptions().containsExactlyInAnyOrder(expectedOptions);
        return this;
    }

    public SelectUIElement shouldHaveSelectedOptions(String... expectedOptions){
        hasSelectedOptions().containsExactlyInAnyOrder(expectedOptions);
        return this;
    }

    public SelectUIElement shouldHaveFirstSelectedOption(String expectedOptions){
        hasFirstSelectedOption().containsIgnoringCase(expectedOptions);
        return this;
    }
    public SelectUIElement shouldHaveMultipleSelectionOption(boolean expectedOptions){
        hasMultipleSelectionOption().isEqualTo(expectedOptions);
        return this;
    }


    public ListAssert<String> hasOptions(){
        List<String> options  = new ArrayList<>();
        List<WebElement> list = select().getOptions();
        for (WebElement item : list){
            options.add(item.getText());
        }
        return Assertions.assertThat(options).as("Has options");
    }
    public ListAssert<String> hasSelectedOptions(){
        List<String> options  = new ArrayList<>();
        List<WebElement> list = select().getAllSelectedOptions();
        for (WebElement item : list){
            options.add(item.getText());
        }
        return Assertions.assertThat(options).as("Has selected options");
    }

    public AbstractStringAssert<?> hasFirstSelectedOption(){
        WebElement option = select().getFirstSelectedOption();
        return Assertions.assertThat(option.getText()).as("Has first selected option");
    }

    public AbstractBooleanAssert<?> hasMultipleSelectionOption(){
        boolean isMultipleSelectItem = select().isMultiple();
        return Assertions.assertThat(isMultipleSelectItem).as("Has multiple selection option");
    }

    private void setSelected(WebElement option, boolean select) {
        if (option.isSelected() != select) {
            option.click();
        }
    }
}
