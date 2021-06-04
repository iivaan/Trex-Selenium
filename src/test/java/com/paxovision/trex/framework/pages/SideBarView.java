package com.paxovision.trex.framework.pages;

import com.paxovision.trex.selenium.annotations.Require;
import com.paxovision.trex.selenium.api.AbstractView;
import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SideBarView  extends AbstractView<SideBarView> {

    @Require
    @FindBy(xpath = "//*[@id='taxonomies']/h6[text()='Shop by Categories']/following-sibling::ul[1]//a")
    private List<UIElement> shopByCategories;

    public SideBarView(){
        super(SideBarView.class);
    }


    public void printList(){
        for (UIElement el : shopByCategories){
            System.out.println(el.getText());
        }
    }

}
