package com.paxovision.trex.framework.pages;

import com.paxovision.trex.selenium.annotations.NotRequired;
import com.paxovision.trex.selenium.annotations.Require;
import com.paxovision.trex.selenium.annotations.RequireAll;
import com.paxovision.trex.selenium.annotations.ViewModel;
import com.paxovision.trex.selenium.api.AbstractView;
import com.paxovision.trex.selenium.api.TestObjectFacade;
import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//@RequireAll
public class HomePageView extends AbstractView<HomePageView> {

    @Require
    @FindBy(linkText = "LOGIN")
    private TestObjectFacade loginLink;

    @FindBy(linkText = "LOGOUT")
    private TestObjectFacade logoutLink;

    @FindBy(tagName = "a")
    private List<UIElement> allLinks;

    @ViewModel
    private SideBarView sideBarView;

    public HomePageView(){
        super(HomePageView.class);
        sideBarView = new SideBarView();
    }

    public List<UIElement> getAllLinks(){
        return allLinks;
    }

    public void navigateToLoginPage(){
        loginLink.click();
    }

    public SideBarView sideBarView(){
        return sideBarView;
    }


}
