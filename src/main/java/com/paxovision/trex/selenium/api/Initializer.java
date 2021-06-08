/*
 Copyright 2014 Red Hat, Inc. and/or its affiliates.

 This file is part of darcy-ui.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.paxovision.trex.selenium.api;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.List;

public class Initializer {
    private final View view;
    private final List<Field> fields;

    public Initializer(View view, List<Field> fields) {
        this.view = view;
        this.fields = fields;
    }

    public void initializeFindByAnnotatedFields(SearchContext context, List<Field> fields) {
        for(Field field : fields){

            FindBy findBy = field.getAnnotation(FindBy.class);
            //System.out.println(findBy);
            By by = buildByFromShortFindBy(findBy);
            if (by == null) {
                by = this.buildByFromLongFindBy(findBy);
            }
            //System.out.println(by);
            if(UIElement.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(view, UIElement.getInstance(by));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(List.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(view, UIElements.getInstance(by));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void initializeViewModelAnnotatedFields(SearchContext context, List<Field> fields) {
        for(Field field : fields){
            if(AbstractView.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(view, View.getInstance(field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    protected By buildByFromShortFindBy(FindBy findBy) {
        if (!"".equals(findBy.className())) {
            return By.className(findBy.className());
        } else if (!"".equals(findBy.css())) {
            return By.cssSelector(findBy.css());
        } else if (!"".equals(findBy.id())) {
            return By.id(findBy.id());
        } else if (!"".equals(findBy.linkText())) {
            return By.linkText(findBy.linkText());
        } else if (!"".equals(findBy.name())) {
            return By.name(findBy.name());
        } else if (!"".equals(findBy.partialLinkText())) {
            return By.partialLinkText(findBy.partialLinkText());
        } else if (!"".equals(findBy.tagName())) {
            return By.tagName(findBy.tagName());
        } else {
            return !"".equals(findBy.xpath()) ? By.xpath(findBy.xpath()) : null;
        }
    }

    protected By buildByFromLongFindBy(FindBy findBy) {
        return findBy.how().buildBy(findBy.using());
    }

    public void initializeFields(SearchContext context) {
        setContext(context);
        assignContext(context);
    }


    private void setContext(SearchContext context) {
        /*fields.stream()
                .filter(f -> HasElementContext.class.isAssignableFrom(f.getType())
                        || Element.class.isAssignableFrom(f.getType())
                        || List.class.isAssignableFrom(f.getType()))
                // TODO: .filter(f -> f.getAnnotation(IndependentContext.class) == null)
                .map(f -> {
                    try {
                        return f.get(view);
                    } catch (IllegalAccessException e) {
                        throw new TrexSeleniumException(String.format("Couldn't retrieve get object " +
                                "from field, %s, in view, %s", f, view), e);
                    }
                })
                .filter(o -> o instanceof HasElementContext)
                .map(e -> (HasElementContext) e)
                .forEach(e -> e.setContext(context));*/
    }


    private void assignContext(SearchContext context) {
        /*fields.stream()
                .filter(f -> f.getAnnotation(Context.class) != null)
                .forEach(f -> {
                    try {
                        f.set(view, context);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        throw new TrexSeleniumException("Couldn't assign context to field," + f, e);
                    }
                });*/
    }
}
