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
import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    public void isPresentTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        assertThat(loginLink.isPresent()).isTrue();

        UIElement loginLink2 = UIElement.getInstance(By.linkText("LOGIN2"));
        assertThat(loginLink2.isPresent()).isFalse();
    }

    @Test
    public void shoutBePresentTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.shouldBePresent().click();
    }
    @Test
    public void shoutNotBePresentTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGINx"));
        loginLink.shouldNotBePresent();
    }
    @Test
    public void shouldHaveTextTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.shouldHaveText("LOGIN").click();
    }
    @Test
    public void shouldHaveValueTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.click();

        UIElement loginButton = UIElement.getInstance(By.name("commit"));
        loginButton.shouldHaveValue("Login").click();

    }

    @Test
    public void isNotPresentTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        assertThat(loginLink.isNotPresent()).isFalse();

        UIElement loginLink2 = UIElement.getInstance(By.linkText("LOGIN2"));
        assertThat(loginLink2.isNotPresent()).isTrue();
    }

    @Test
    public void isDisplayedTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        assertThat(loginLink.isDisplayed()).isTrue();

        UIElement loginLink2 = UIElement.getInstance(By.linkText("LOGIN2"));
        assertThat(loginLink2.isDisplayed()).isFalse();
    }
    @Test
    public void isNotDisplayedTest(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        assertThat(loginLink.isNotDisplayed()).isFalse();

        UIElement loginLink2 = UIElement.getInstance(By.linkText("LOGIN2"));
        assertThat(loginLink2.isNotDisplayed()).isTrue();
    }

    @Test
    public void waitUntilEnabled(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.waitUntilDisabled(3000).click();
    }

    @Test
    public void waitUntillClickable(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.waitUntilClickable().click();
    }

    @Test
    public void waitUntilTextToBe(){
        UIElement loginLink = UIElement.getInstance(By.linkText("LOGIN"));
        loginLink.waitUntilTextToBe(30000,"LOGIN").click();

        UIElement loginButton = UIElement.getInstance(By.name("commit"));
        loginButton.waitUntilAttribute(30000,"value","Loginx").click();

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
