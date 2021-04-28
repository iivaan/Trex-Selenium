package com.paxovision.trex.scripts;

import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.DriverFactory;
import com.paxovision.trex.selenium.api.TestObjectFacade;
import com.paxovision.trex.selenium.api.UIElement;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UIElementTest {

    static{
        System.setProperty("WEB_DRIVER_TYPE","CHROME");
    }

    private WebDriver driver;

    @Before
    public void setUp(){
        //ChromeDriverManager.chromedriver().setup();
        ///driver = new ChromeDriver();
        //driver = DriverFactory.getInstance().getDriver();
        driver = WebDriverFactory.getInstance().getDriver();
        driver.navigate().to("http://spree.shiftedtech.com");
    }

    @Test
    public void test2(){
        TestObjectFacade.ENABLE_HIGHLIGHT = true;

        //UIElement element = TestObjectFacade.getInstance(By.linkText("LOGIN"));
        UIElement element = UIElement.getInstance(driver, By.linkText("LOGIN")) ;
        element.click();

        //driver.navigate().back();
        //element.click();

        UIElement email = UIElement.getInstance(By.id("spree_user_email"));
        email.clear().sendKeys("shiftqa01@gmail.com").hasValue().isEqualTo("shiftqa01@gmail.com");
        email.hasValue().isEqualTo("shiftqa01@gmail.com").endsWith(".com");
        email.hasIsEnabled().isTrue();

        UIElement password = UIElement.getInstance( By.id("spree_user_password"));
        password.clear().sendKeys("shiftedtech");

        UIElement loginButton = UIElement.getInstance(driver.findElement(By.id("content")), By.name("commit"));
        loginButton.click();


    }
    @Test
    public void testElementFindElement(){
        UIElement parent = UIElement.getInstance(By.id("link-to-login"));
        UIElement loginLink = parent.findElement(By.linkText("LOGIN"));
        loginLink.click();
    }

    @After
    public void tearDown(){
        delayFor(3000);
        //driver.quit();
        WebDriverFactory.getInstance().quite();
    }

    public static void delayFor(int timeToWait){
        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
