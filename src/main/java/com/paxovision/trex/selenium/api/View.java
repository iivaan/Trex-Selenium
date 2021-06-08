package com.paxovision.trex.selenium.api;

import com.paxovision.trex.selenium.guice.GuiceFactory;

public interface View extends HasSearchContext {
    static View getInstance(Class k){
        View view = (View) GuiceFactory.ViewModelGuiceModuleInjector.getInstance(k);
        return view;
    }
    /*public static <M> M getInstance(){
        View view = GuiceFactory.ViewModelGuiceModuleInjector.getInstance(M);
        return (M) view;
    }*/

    boolean isLoaded();
    void validate();




}
