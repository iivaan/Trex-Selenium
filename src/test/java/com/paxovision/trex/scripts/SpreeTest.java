package com.paxovision.trex.scripts;

import com.paxovision.trex.selenium.api.UIElement;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SpreeTest {
    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver = null;

   @BeforeEach
    public void setUp(){
        driver = WebDriverFactory.getInstance().getDriver();
        driver.navigate().to("http://spree.shiftedtech.com");
    }
    @Test
    public void test1(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.waitUntilClickable().shouldHaveText("LOGIN").click();

        UIElement email = UIElement.getInstance(By.id("spree_user_email"));
        email.clear().shouldHaveFocus().sendKeys("shiftqa01@gmail.com").hasValue().isEqualTo("shiftqa01@gmail.com");

        UIElement password = UIElement.getInstance( By.id("spree_user_password"));
        password.highlight().setFocus().shouldHaveFocus().sendKeys("shiftedtech");

        UIElement loginButton = UIElement.getInstance(driver.findElement(By.id("content")), By.name("commit"));
        loginButton.highlight().click();

    }

    @AfterEach
    public void tearDown(){
        WebDriverFactory.getInstance().quite();
        driver = null;
    }
}
