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

@RequireAll
public class RequiredHomePageView extends AbstractView<RequiredHomePageView> {

    @FindBy(linkText = "LOGIN")
    private TestObjectFacade loginLink;

    //@NotRequired
    @FindBy(linkText = "LOGOUT_Not_Present_1")
    private TestObjectFacade logoutLink1;

    @NotRequired
    @FindBy(linkText = "LOGOUT_Not_Present_2")
    private TestObjectFacade logoutLink2;

    @NotRequired
    @FindBy(linkText = "LOGOUT_Not_Present_3")
    private TestObjectFacade logoutLink3;

    //@NotRequired
    //@FindBy(tagName = "a")
    //private List<UIElement> allLinks;

    @ViewModel
    private SideBarView sideBarView;

    private List<String> names;

    public RequiredHomePageView(){
        super(RequiredHomePageView.class);
        //sideBarView = new SideBarView();
    }

   // public List<UIElement> getAllLinks(){
   //     return allLinks;
   // }

    public void navigateToLoginPage(){
        loginLink.click();
    }

    public SideBarView sideBarView(){
        return sideBarView;
    }


}
