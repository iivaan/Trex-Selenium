package com.paxovision.trex.selenium.api;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestObjectFacade extends AbstractTestObject<TestObjectFacade>{

    TestObjectFacade(){
        super(TestObjectFacade.class);
    }
    TestObjectFacade(WebElement element,SearchContext searchContext, By by){
        this();
        this.resolvedElement = element;
        this.searchContext = searchContext;
        this.by = by;
    }
    TestObjectFacade(SearchContext searchContext, By by){
        this();
        this.searchContext = searchContext;
        this.by = by;
    }



   /* public List<WebElement> findElements(By by) {
        getResolvedElement();
        return null;
    }*/
    /*public WebElement findElement(By by) {
        getResolvedElement();
        return null;
    }*/












    public void check() {
        if (!isChecked()) {
            click();
        }
    }

    public void uncheck() {
        if (isChecked()) {
            click();
        }
    }
    public void toggle() {
        click();
    }
    public boolean isChecked() {
        return attemptAndGet(WebElement::isSelected);
    }
    public String getFor() {
        return getAttribute("for");
    }
    public List<String> getClasses() {
        String classList = getAttribute("class");

        if (classList == null || classList.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(classList.split(" "))
                .collect(Collectors.toList());
    }


    public String toString() {
        return "UIElement(" + by + ")";
    }


}
