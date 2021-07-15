package com.paxovision.trex.framework.utils;

import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

public interface MyServiceConfiguration {

    @Length(min = 5)
    String getHostname();

    Map<String, String> getAMap();

    List<String> getAList();
}
