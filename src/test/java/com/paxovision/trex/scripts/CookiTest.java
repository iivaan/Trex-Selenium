package com.paxovision.trex.scripts;

import com.paxovision.trex.selenium.api.UIElement;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.PauseExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CookiTest {
    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver = null;

    @BeforeEach
    public void setUp(){
        driver = WebDriverFactory.getInstance().getDriver();
        driver.navigate().to("https://www.currys.co.uk/gbuk/index.html");
    }
    @Test
    public void test1(){
        UIElement loginLink = UIElement.getInstance(By.id("onetrust-accept-btn-handler"));
        loginLink.waitUntilClickable().highlight().click();



    }

    @AfterEach
    public void tearDown(){
        WebDriverFactory.getInstance().quite();
        driver = null;
    }
}
