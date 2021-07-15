package com.paxovision.trex.scripts;

import com.google.inject.Inject;
import com.paxovision.trex.framework.pages.HomePageView;
import com.paxovision.trex.framework.pages.LoginPageView;
import com.paxovision.trex.framework.utils.MyServiceConfiguration;
import com.paxovision.trex.framework.utils.PageObjectGuiceModule;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.guice.RequiresInjection;
import net.jmob.guice.conf.core.BindConfig;
import net.jmob.guice.conf.core.InjectConfig;
import net.jmob.guice.conf.core.Syntax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

@RequiresInjection(values = {PageObjectGuiceModule.class})
@BindConfig(value = "app", syntax = Syntax.JSON)
public class GoogleGuiceTest {

    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver;
    @Inject
    private HomePageView homePageView;
    @Inject
    private LoginPageView loginPageView;

    @InjectConfig
    private Optional<Integer> port;

    @InjectConfig("complexEntries")
    private MyServiceConfiguration config;


    @BeforeEach
    public void setUp(){
        driver = WebDriverFactory.getInstance().getDriver();
        driver.navigate().to("http://spree.shiftedtech.com");
    }

    @Test
    public void test1() throws InterruptedException {
        homePageView.navigateToLoginPage();
        loginPageView.login("shiftqa01@gmail.com","shiftedtech");
        Thread.sleep(4000);
    }

    @Test
    public void test2() throws InterruptedException {
        homePageView.navigateToLoginPage();
        loginPageView.login("shiftqa01@gmail.com","shiftedtech");
        Thread.sleep(4000);
    }

    @Test
    public void test3() throws InterruptedException {
        homePageView.navigateToLoginPage();
        loginPageView.login("shiftqa01@gmail.com","shiftedtech");
        Thread.sleep(4000);
    }
    @Test
    public void test4() throws InterruptedException {
        System.out.println(port);
        System.out.println(config.getHostname());
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
