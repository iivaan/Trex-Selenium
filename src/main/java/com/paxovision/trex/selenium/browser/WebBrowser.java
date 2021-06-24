package com.paxovision.trex.selenium.browser;

import org.openqa.selenium.*;
import org.openqa.selenium.logging.Logs;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface WebBrowser extends SearchContext{
    void get(String var1);
    String getCurrentUrl();
    String getTitle();
    String getPageSource();
    void close();
    void quit();
    Set<String> getWindowHandles();
    String getWindowHandle();
    WebDriver.TargetLocator switchTo();
    WebDriver.Navigation navigate();
    WebDriver.Options manage();

    @Beta
    public interface Window {
        void setSize(Dimension var1);
        void setPosition(Point var1);
        Dimension getSize();
        Point getPosition();
        void maximize();
        void fullscreen();
    }

    public interface ImeHandler {
        List<String> getAvailableEngines();
        String getActiveEngine();
        boolean isActivated();
        void deactivate();
        void activateEngine(String var1);
    }

    public interface Navigation {
        void back();
        void forward();
        void to(String var1);
        void to(URL var1);
        void refresh();
    }

    public interface TargetLocator {
        WebDriver frame(int var1);
        WebDriver frame(String var1);
        WebDriver frame(WebElement var1);
        WebDriver parentFrame();
        WebDriver window(String var1);
        WebDriver defaultContent();
        WebElement activeElement();
        Alert alert();
    }

    public interface Timeouts {
        WebDriver.Timeouts implicitlyWait(long var1, TimeUnit var3);
        WebDriver.Timeouts setScriptTimeout(long var1, TimeUnit var3);
        WebDriver.Timeouts pageLoadTimeout(long var1, TimeUnit var3);
    }

    public interface Options {
        void addCookie(Cookie var1);
        void deleteCookieNamed(String var1);
        void deleteCookie(Cookie var1);
        void deleteAllCookies();
        Set<Cookie> getCookies();
        Cookie getCookieNamed(String var1);
        WebDriver.Timeouts timeouts();
        WebDriver.ImeHandler ime();
        WebDriver.Window window();

        @Beta
        Logs logs();
    }
}
