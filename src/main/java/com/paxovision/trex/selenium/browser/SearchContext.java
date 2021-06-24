package com.paxovision.trex.selenium.browser;

import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.By;

import java.util.List;

public interface SearchContext {
    UIElement<?> findElement(By by);
    List<UIElement<?>> findElements(By by);
}
