package com.paxovision.trex.selenium.utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static DriverFactory instance = null;
    private WebDriver driver;

    private DriverFactory(){
        ChromeDriverManager.chromedriver().setup();
    }
    public static DriverFactory getInstance(){
        if(instance == null){
            instance = new DriverFactory();
        }
        return instance;
    }

    public WebDriver getDriver(){
        if(driver == null){
            driver = new ChromeDriver();
        }
        return driver;
    }
}
