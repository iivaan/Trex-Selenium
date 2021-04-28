package com.paxovision.trex.selenium.api;

import org.openqa.selenium.By;

import java.util.List;

public interface UISearchContext {
    List<UIElement> findElements(By var1);
    UIElement findElement(By var1);
}
