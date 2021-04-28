package com.paxovision.trex.selenium.matchers;


import com.paxovision.trex.selenium.api.Text;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ElementText<T extends Text> extends TypeSafeMatcher<T> {
    
    private final Matcher<? super String> matcher;
    
    public ElementText(Matcher<? super String> matcher) {
        this.matcher = matcher;
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("an Element whose text is");
        matcher.describeTo(description);
    }
    
     @Override
    protected boolean matchesSafely(T element) {
        return element.isPresent() && matcher.matches(element.getText());
    }
}
