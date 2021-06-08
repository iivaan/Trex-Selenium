package com.paxovision.trex.selenium.api;

import com.google.inject.Injector;
import com.paxovision.trex.selenium.annotations.Context;
import com.paxovision.trex.selenium.matchers.Condition;
import com.paxovision.trex.selenium.utils.ReflectionUtil;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.SearchContext;
import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractView <T extends AbstractView<T>>  implements View{

    //reference to self as the subclass type
    protected final T SELF;
    @Context
    private SearchContext searchContext;

    /**
     * Analyzes annotations and fields for load conditions. Intentionally package scope for
     */
    private final Analyzer analyzer;
    /**
     * Sets context on fields that have a context whatever context is assigned to this view.
     */
    private final Initializer initializer;

    //private static Injector injector = Guice.createInjector(new TestObjectGuiceModule());
    //private static Injector injector = GuiceFactory.TestObjectGuiceModuleInjector;
 /*   public static <D> D getInstance() {
        //injector.getInstance(HomePageView.class);
    return null;
    }*/

    protected AbstractView(final Class<T> selfClass){
        SELF = selfClass.cast(this);

        List<Field> declaredFields = ReflectionUtil.getAllDeclaredFields(this);

        analyzer = new Analyzer(this, declaredFields);
        initializer = new Initializer(this, declaredFields);
        initializer.initializeFindByAnnotatedFields(null,analyzer.findByAnnotadedFields());
        initializer.initializeViewModelAnnotatedFields(null,analyzer.viewModelAnnotadedFields());
    }


    public void validate(){
        SoftAssertions softAssertions = new SoftAssertions();
        List<Condition<?>> list = analyzer.getLoadConditions();
        for(Condition<?> item : list){
            softAssertions.assertThat(item.isMet()).as(item.toString()).isTrue();
            if(item.lastResult() instanceof AbstractView){
                ((AbstractView)item.lastResult()).validate(softAssertions);
            }
        }
        softAssertions.assertAll();
    }

    void validate(SoftAssertions softAssertions){
        List<Condition<?>> list = analyzer.getLoadConditions();
        for(Condition<?> item : list){
            softAssertions.assertThat(item.isMet()).as(item.toString()).isTrue();
            if(item.lastResult() instanceof AbstractView){
                ((AbstractView)item.lastResult()).validate(softAssertions);
            }
        }
    }


    @Override
    public boolean isLoaded() {
        boolean loaded = true;
        loaded =  analyzer.getLoadConditions()
                .stream()
                .allMatch(Condition::isMet);
        return loaded;
    }

    @Override
    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
        initializer.initializeFields(searchContext);
        onSetContext();
    }

    @Override
    public SearchContext getSearchContext() {
        return searchContext;
    }

    /**
     * Called after any call to {@link #setSearchContext(SearchContext)}. Useful if you need to set up
     * some fields that depend on this view having a context.
     */
    protected void onSetContext() {
        //TODO: onSetContext
    }

    @Override
    public String toString(){
        return SELF.getClass().getName();
    }


    public T doThat(){
        return SELF;
    }
}





//https://www.andygibson.net/blog/article/implementing-chained-methods-in-subclasses/