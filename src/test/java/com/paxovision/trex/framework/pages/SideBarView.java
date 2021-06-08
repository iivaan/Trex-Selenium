package com.paxovision.trex.framework.pages;

import com.paxovision.trex.selenium.annotations.NotRequired;
import com.paxovision.trex.selenium.annotations.Require;
import com.paxovision.trex.selenium.api.AbstractView;
import com.paxovision.trex.selenium.api.TestObjectFacade;
import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SideBarView  extends AbstractView<SideBarView> {

    @Require
    @FindBy(xpath = "//*[@id='taxonomies']/h6[text()='Shop by Categories']/following-sibling::ul[1]//a")
    private List<UIElement> shopByCategories;

    @Require
    //@NotRequired
    @FindBy(xpath = "//a[text()='sidebar']")
    private List<UIElement> shopByCategories2;

    @Require
    //@NotRequired
    @FindBy(linkText = "Sidebar_Not_Present_1")
    private TestObjectFacade logoutLink1;


    public SideBarView(){
        super(SideBarView.class);
    }


    public void printList(){
        for (UIElement el : shopByCategories){
            System.out.println(el.getText());
        }
    }

}
