package com.paxovision.trex.selenium.config;

import com.paxovision.trex.selenium.driver.Configs;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrexSeleniumConfigFactory {
    private static TrexSeleniumConfigFactory instance = null;

    private static Logger logger = LoggerFactory.getLogger(TrexSeleniumConfigFactory.class);
    private static Config config = null;

    private TrexSeleniumConfigFactory(){
        logger.info("Creating TrexSeleniumConfigFactory instance from config file");
        config = Configs.newBuilder()
                .withResource("trex-selenium.conf")
                .withSystemEnvironment()
                .withSystemProperties()
                .build();
    }

    public static TrexSeleniumConfigFactory getInstance(){
        if(instance == null){
            instance = new TrexSeleniumConfigFactory();
        }
        return instance;
    }

    public Config config(){
        return config;
    }


}
