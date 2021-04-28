package com.paxovision.trex.selenium.matchers;

import com.paxovision.trex.selenium.api.Requireable;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RequireableIsNotRequired<T extends Requireable> extends TypeSafeMatcher<T> {

    @Override
    protected boolean matchesSafely(T requireable) {
        return !requireable.isRequired();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" an element that is not required ");
    }
    
    @Override
    public void describeMismatchSafely(T requireable, Description mismatchDescription) {
        mismatchDescription.appendText(" isRequired was ").appendValue(requireable.isRequired());
    }

}
