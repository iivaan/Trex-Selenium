package com.paxovision.trex.selenium.utils;

import com.paxovision.trex.selenium.exceptions.TrexSeleniumException;

public class PauseExecution {
    final int timeToWait;
    private PauseExecution(int timeToWait){
        this.timeToWait = timeToWait;
    }

    public static PauseExecution forTimeOf(int timeToWait){
        if(timeToWait <= 0){
            throw new TrexSeleniumException("Invalid time. Please provide > 0");
        }
        return new PauseExecution(timeToWait);
    }

    public void minutes(){
        delayFor(timeToWait * 1000 * 60);
    }
    public void seconds(){
        delayFor(timeToWait * 1000);
    }
    public void milliseconds(){
        delayFor(timeToWait);
    }

    private void delayFor(int timeInMillis){
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            throw new TrexSeleniumException(e);
        }
    }

}
