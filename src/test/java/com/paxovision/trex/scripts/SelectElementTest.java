package com.paxovision.trex.scripts;

import com.paxovision.trex.selenium.api.UIElement;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.paxovision.trex.selenium.utils.PauseExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SelectElementTest {
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
    public void selectByIndex(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.waitUntilClickable().select().selectByIndex(1);
        PauseExecution.forTimeOf(5).seconds();
    }
    @Test
    public void selectByVisibleText(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.waitUntilClickable().select().selectByVisibleText("Brand");
        PauseExecution.forTimeOf(5).seconds();
    }
    @Test
    public void selectByValue(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.waitUntilClickable().select().selectByValue("1");
        PauseExecution.forTimeOf(5).seconds();
    }

    @Test
    public void getOptions(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        List<UIElement<?>> list = loginLink.waitUntilClickable().select().getOptions();
        for(UIElement item : list){
            System.out.println(item.getText());
        }

        PauseExecution.forTimeOf(5).seconds();
    }

    @Test
    public void hasOptions(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.waitUntilClickable().select().hasOptions().hasSizeLessThanOrEqualTo(3).containsExactly("All departments","Categories","Brand");
        PauseExecution.forTimeOf(5).seconds();
    }
    @Test
    public void shouldHaveOptions(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.waitUntilClickable()
                .select()
                .shouldHaveOptions("All departments","Categories","Brand")
                .selectByVisibleText("Brand")
                .shouldHaveSelectedOptions("Brand");
        PauseExecution.forTimeOf(5).seconds();
    }

    @Test
    public void hasFirstSelectedOption(){
        UIElement loginLink = UIElement.getInstance(By.id("taxon"));
        loginLink.select().selectByVisibleText("Brand").hasFirstSelectedOption().isEqualTo("Brand");
        PauseExecution.forTimeOf(5).seconds();
    }


    @AfterEach
    public void tearDown(){
        WebDriverFactory.getInstance().quite();
        driver = null;
    }
}
