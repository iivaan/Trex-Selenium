package com.paxovision.trex.framework.pages;

import com.paxovision.trex.selenium.api.AbstractView;
import com.paxovision.trex.selenium.api.UIElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageView extends AbstractView<LoginPageView> {

    @FindBy(how = How.ID, using ="spree_user_email")
    private UIElement emailAddressTextbox;
    @FindBy(id="spree_user_password")
    private UIElement passwordTextbox;
    @FindBy(name = "commit")
    private UIElement loginButton;

    public LoginPageView(){
        super(LoginPageView.class);
    }

    public void login(String emai, String password){
        emailAddressTextbox.sendKeys(emai);
        passwordTextbox.sendKeys(password);
        loginButton.click();
    }



}
