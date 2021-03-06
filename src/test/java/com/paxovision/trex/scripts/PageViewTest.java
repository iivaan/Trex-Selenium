package com.paxovision.trex.scripts;

import com.paxovision.trex.framework.pages.HomePageView;
import com.paxovision.trex.framework.pages.LoginPageView;
import com.paxovision.trex.selenium.api.UIElement;
import com.paxovision.trex.selenium.api.UIElements;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.DriverFactory;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class PageViewTest {
    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver;
    private HomePageView homePageView;
    private LoginPageView loginPageView;

    @BeforeEach
    public void setUp(){
        driver = WebDriverFactory.getInstance().getDriver();
        driver.navigate().to("http://spree.shiftedtech.com");

        homePageView = new HomePageView();
        loginPageView = new LoginPageView();
    }

    @Test
    public void test1_0(){
        homePageView.isLoaded();
        homePageView.sideBarView().printList();
    }

    @Test
    public void test1(){
        homePageView.isLoaded();
        homePageView.navigateToLoginPage();
        loginPageView.login("shiftqa01@gmail.com","shiftedtech");
    }


    @AfterEach
    public void tearDown(){
        delayFor(3000);
        driver.quit();
    }

    public static void delayFor(int timeToWait){
        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
