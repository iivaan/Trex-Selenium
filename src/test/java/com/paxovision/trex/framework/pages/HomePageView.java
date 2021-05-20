package com.paxovision.trex.framework.pages;

import com.paxovision.trex.selenium.annotations.NotRequired;
import com.paxovision.trex.selenium.annotations.RequireAll;
import com.paxovision.trex.selenium.api.AbstractView;
import com.paxovision.trex.selenium.api.TestObjectFacade;
import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@RequireAll
public class HomePageView extends AbstractView<HomePageView> {

    @FindBy(linkText = "LOGIN")
    private TestObjectFacade loginLink;

    //@NotRequired
    @FindBy(linkText = "LOGOUT")
    private TestObjectFacade logoutLink;

    //@NotRequired
    @FindBy(linkText = "LOGOUT2")
    private TestObjectFacade logoutLink2;

    @FindBy(tagName = "a")
    private List<UIElement> allLinks;

    public HomePageView(){
        super(HomePageView.class);
    }

    public List<UIElement> getAllLinks(){
        return allLinks;
    }

    public void navigateToLoginPage(){
        loginLink.click();
    }



}
