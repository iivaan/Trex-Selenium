package com.paxovision.trex.selenium.api;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JavascriptExecutorFacade {
    private SearchContext searchContext = null;

    public JavascriptExecutorFacade(SearchContext searchContext){
        this.searchContext = searchContext;
    }

    /**
     * Execute some Javascript in the underlying WebDriver driver.
     * @param script
     */
    public Object executeScript(final String script) {
        if (javascriptIsSupportedIn(searchContext)) {
            JavascriptExecutor js = (JavascriptExecutor) searchContext;
            return js.executeScript(script);
        } else {
            return null;
        }
    }

    public Object executeScript(final String script, final Object... params) {
        Object parameters[] = extractedWebElementsAndPrimitiveTypesFrom(params);
        if (javascriptIsSupportedIn(searchContext)) {
            JavascriptExecutor js = (JavascriptExecutor) searchContext;
            return js.executeScript(script, parameters);
        } else {
            return null;
        }
    }

    public Object executeAsyncScript(final String script) {
        if (javascriptIsSupportedIn(searchContext)) {
            JavascriptExecutor js = (JavascriptExecutor) searchContext;
            return js.executeAsyncScript(script);
        } else {
            return null;
        }
    }

    public Object executeAsyncScript(final String script, final Object... params) {
        if (javascriptIsSupportedIn(searchContext)) {
            JavascriptExecutor js = (JavascriptExecutor) searchContext;
            return js.executeAsyncScript(script, params);
        } else {
            return null;
        }
    }


    private Object[] extractedWebElementsAndPrimitiveTypesFrom(Object[] params) {
        return Arrays.stream(params).map(
                param -> webElementOrPrimitiveTypeOf(param)
        ).collect(Collectors.toList()).toArray();
    }
    private Object webElementOrPrimitiveTypeOf(Object param) {
        if (param != null && param instanceof AbstractTestObject) {
            return ((AbstractTestObject) param).getResolvedElement();
        }
        return param;
    }
    private boolean javascriptIsSupportedIn(SearchContext searchContext) {
        if (searchContext == null) {
            return false;
        }
        if (searchContext instanceof JavascriptExecutor) {
            return true;
        }
        else{
            return false;
        }

    }
}
