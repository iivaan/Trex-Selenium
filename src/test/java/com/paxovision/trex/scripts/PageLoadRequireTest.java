package com.paxovision.trex.scripts;

import com.paxovision.trex.framework.pages.HomePageView;
import com.paxovision.trex.framework.pages.LoginPageView;
import com.paxovision.trex.framework.pages.RequiredHomePageView;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.PauseExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class PageLoadRequireTest {
    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver;
    //private HomePageView homePageView;
    private RequiredHomePageView requiredHomePageView;
    //private LoginPageView loginPageView;

    @BeforeEach
    public void setUp(){
        driver = WebDriverFactory.getInstance().getDriver();
        //homePageView = new HomePageView();
        requiredHomePageView = new RequiredHomePageView();
        //loginPageView = new LoginPageView();


        driver.navigate().to("http://spree.shiftedtech.com");
    }

    @Test
    public void test1(){
        //System.out.println("IsHomepageLoaded:" + requiredHomePageView.isLoaded());
        requiredHomePageView.validate();

        requiredHomePageView.navigateToLoginPage();
        //loginPageView.login("shiftqa01@gmail.com","shiftedtech");
    }


    @AfterEach
    public void tearDown(){
        WebDriverFactory.getInstance().quite();
        driver = null;
    }
}
