package com.paxovision.trex.framework.utils;

import com.google.inject.AbstractModule;
import com.paxovision.trex.framework.pages.HomePageView;
import com.paxovision.trex.framework.pages.LoginPageView;
import org.openqa.selenium.SearchContext;

public class PageObjectGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HomePageView.class)
                .toInstance(new HomePageView());

        bind(LoginPageView.class)
                .toInstance(new LoginPageView());
    }
}
