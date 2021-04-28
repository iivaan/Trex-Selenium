package com.paxovision.trex.selenium.api;

import org.openqa.selenium.SearchContext;

public interface HasSearchContext {
    void setSearchContext(SearchContext searchContext);
    SearchContext getSearchContext();
}
