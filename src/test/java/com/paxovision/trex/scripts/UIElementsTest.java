package com.paxovision.trex.scripts;

import com.paxovision.trex.selenium.api.UIElement;
import com.paxovision.trex.selenium.api.UIElements;
import com.paxovision.trex.selenium.utils.DriverFactory;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class UIElementsTest {
    private WebDriver driver;

    @Before
    public void setUp(){
        ChromeDriverManager.chromedriver().setup();
        ///driver = new ChromeDriver();
        driver = DriverFactory.getInstance().getDriver();
        driver.navigate().to("http://spree.shiftedtech.com");
    }

    @Test
    public void test1(){
        // List<UIElement> list = TestObjectListFacade.getInstance(By.tagName("a"));
        List<UIElement> list = UIElements.getInstance(By.linkText("LOGIN"));
        System.out.println(list.size());
        for(UIElement to : list){
            System.out.println(to.getText());
        }
        list.get(0).click();
    }


    @After
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
