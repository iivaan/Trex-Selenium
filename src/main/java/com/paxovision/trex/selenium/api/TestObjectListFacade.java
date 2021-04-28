package com.paxovision.trex.selenium.api;

import com.google.inject.Inject;
import com.paxovision.trex.selenium.utils.Caching;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.*;

public class TestObjectListFacade<T> implements UIElements<T>, Caching {
    @Inject
    protected SearchContext searchContext = null;
    private By by = null;
    private List<T> cachedList;
    //private final Supplier<List<T>> list;


    TestObjectListFacade(){
    }

    public TestObjectListFacade(SearchContext searchContext, By by){
        this.searchContext = searchContext;
        this.by = by;
    }

    void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }
    void setBy(By by) {
        this.by = by;
    }


    @Override
    public void invalidateCache() {
        cachedList = null;
    }

    @Override
    public int size() {
        return list().size();
    }

    @Override
    public boolean isEmpty() {
        return list().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list().iterator();
    }

    @Override
    public Object[] toArray() {
        return list().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return list().add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list().retainAll(c);
    }

    @Override
    public void clear() {
        list().clear();
    }

    @Override
    public T get(int index) {
        return list().get(index);
    }

    @Override
    public T set(int index, T element) {
        return list().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list().add(index, element);
    }

    @Override
    public T remove(int index) {
        return list().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list().subList(fromIndex, toIndex);
    }

    private List<T> list() {
        if (cachedList == null) {
            cachedList =  new ArrayList<>(); //list.get();
            List<WebElement> elements = searchContext.findElements(by);
            for(WebElement element : elements){
                //cachedList.add((T) new TestObjectFacade(element,searchContext,by));
                cachedList.add((T) UIElement.getInstance(element,searchContext,by));
            }
        }
        return cachedList;
    }
}
