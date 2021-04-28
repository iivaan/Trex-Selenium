package com.paxovision.trex.selenium.api;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.util.List;

public interface UIElements <T> extends List<T> {

    public static List<UIElement> getInstance(By by){
        UIElements<UIElement> elements = GuiceFactory.TestObjectListGuiceModuleInjector.getInstance(TestObjectListFacade.class);
        ((TestObjectListFacade<UIElement>) elements).setBy(by);
        return elements;
    }

    public static List<UIElement> getInstance(SearchContext context, By by){
        UIElements<UIElement> elements = GuiceFactory.TestObjectListGuiceModuleInjector.getInstance(TestObjectListFacade.class);
        ((TestObjectListFacade<UIElement>) elements).setSearchContext(context);
        ((TestObjectListFacade<UIElement>) elements).setBy(by);
        return elements;
    }
}
