package com.paxovision.trex.selenium.api;

import com.paxovision.trex.selenium.annotations.Context;
import com.paxovision.trex.selenium.annotations.NotRequired;
import com.paxovision.trex.selenium.annotations.Require;
import com.paxovision.trex.selenium.annotations.RequireAll;
import com.paxovision.trex.selenium.exceptions.NoRequiredElementsException;
import com.paxovision.trex.selenium.exceptions.TrexSeleniumException;
import com.paxovision.trex.selenium.matchers.LoadConditionMatcher;
import com.paxovision.trex.selenium.synq.Condition;
import com.paxovision.trex.selenium.utils.RequiredList;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.paxovision.trex.selenium.matchers.RequiredListMatcher.hasCorrectNumberOfItemsMatching;
import static com.paxovision.trex.selenium.matchers.TrexMatchers.displayed;
import static com.paxovision.trex.selenium.matchers.TrexMatchers.present;
import static com.paxovision.trex.selenium.synq.HamcrestCondition.match;

public class Analyzer {
    private final Object view;
    private final List<Field> required;

    private final List<Field> findByAnnotadedFields;

    private List<RequiredList<Object>> requiredLists;
    private List<Object> requiredObjects;

    private List<Condition<?>> isLoaded;
    private List<Condition<?>> isDisplayed;
    private List<Condition<?>> isPresent;

    /**
     * @param view A view with at least one field that is an
     * {@link UIElement}, {@link View},
     * {@link Findable}, or {@link List} of those types,
     * and is annotated as required.
     * @param fields All of the fields declared for the specified View (including fields in parent
     * classes}. Fields are expected to be accessible.
     */
    public Analyzer(Object view, List<Field> fields) {
        this.view = Objects.requireNonNull(view, "view");
        this.required = filterRequired(Objects.requireNonNull(fields, "fields"));

        this.findByAnnotadedFields = filterFindByAnnotatedFields(Objects.requireNonNull(fields, "fields"));
    }

    public List<Field> findByAnnotadedFields(){
        return this.findByAnnotadedFields;
    }

    public List<Condition<?>> getLoadConditions() {
        if (isLoaded == null) {
            isLoaded = new ArrayList<>();

            analyze();

            isLoaded.addAll(requiredObjects.stream()
                                            .map(o -> match(o, new LoadConditionMatcher()))
                                            .collect(Collectors.toList()));

            isLoaded.addAll(requiredLists.stream()
                                        .map(l -> match(l.list(),
                                            hasCorrectNumberOfItemsMatching(l.atLeast(), l.atMost(),
                                            new LoadConditionMatcher())))
                                        .collect(Collectors.toList()));

            if(isLoaded.isEmpty()) {
                throw new NoRequiredElementsException(view);
            }
        }

        return isLoaded;
    }

    public List<Condition<?>> getDisplayConditions() {
        if (isDisplayed == null) {
            isDisplayed = new ArrayList<>();

            analyze();

            isDisplayed.addAll(requiredObjects.stream()
                    .filter(o -> o instanceof Element) // Should check instance or field type?
                    .map(e -> match((Element) e, displayed()))
                    .collect(Collectors.toList()));

            isDisplayed.addAll(requiredLists.stream()
                    .filter(l -> Element.class.isAssignableFrom(l.genericType()))
                    .map(l -> match(l.list(),
                            hasCorrectNumberOfItemsMatching(l.atLeast(), l.atMost(), displayed())))
                    .collect(Collectors.toList()));

            if(isDisplayed.isEmpty()) {
                throw new NoRequiredElementsException(view);
            }
        }

        return isDisplayed;
    }

    public List<Condition<?>> getIsPresentConditions() {
        if (isPresent == null) {
            isPresent = new ArrayList<>();

            analyze();

            isPresent.addAll(requiredObjects.stream()
                    .filter(o -> o instanceof Findable) // Should check instance or field type?
                    .map(f -> match((Findable) f, present()))
                    .collect(Collectors.toList()));

            isPresent.addAll(requiredLists.stream()
                    .filter(l -> Findable.class.isAssignableFrom(l.genericType()))
                    .map(l -> match(l.list(),
                            hasCorrectNumberOfItemsMatching(l.atLeast(), l.atMost(), present())))
                    .collect(Collectors.toList()));

            if(isPresent.isEmpty()) {
                throw new NoRequiredElementsException(view);
            }
        }

        return isPresent;
    }

    /**
     * Reflectively examine the view, gathering, filtering, and sorting fields. The results are
     * assigned to {@link #requiredLists} and {@link #requiredObjects}; fields that are lists and
     * objects of fields that are not lists, respectively. This method is idempotent; subsequent
     * calls after the first have no effect (fields need only be analyzed once).
     *
     * <p>Fields cannot be analyzed before they are assigned, which is why this analyze is delayed
     * until needed. This way you can instantiate an Analyzer in a constructor or {@code <init>}
     * without worrying about whether your class or subclass fields are assigned yet.
     */
    private void analyze() {
        if (requiredLists != null && requiredObjects != null) {
            return;
        }

        requiredLists = required.stream()
                .filter(this::isList)
                .map(f -> new RequiredList<>(f, view))
                .filter(l -> Element.class.isAssignableFrom(l.genericType()) ||
                        View.class.isAssignableFrom(l.genericType()) ||
                        Findable.class.isAssignableFrom(l.genericType()))
                .collect(Collectors.toList());

        requiredObjects = required.stream()
                .filter(f -> !isList(f))
                .map(this::fieldToObject)
                .collect(Collectors.toList());

        if (requiredLists.isEmpty() && requiredObjects.isEmpty()) {
            throw new NoRequiredElementsException(view);
        }
    }

    private List<Field> filterRequired(List<Field> fields) {
        return fields.stream()
                .filter(this::isViewElementFindableOrList)
                .filter(this::isNotAnnotatedWithContext)
                .filter(this::isRequired)
                .collect(Collectors.toList());
    }

    private List<Field> filterFindByAnnotatedFields(List<Field> fields) {
        return fields.stream()
                .filter(this::isViewElementFindableOrList)
                .filter(this::isFindByAnnotated)
                .collect(Collectors.toList());
    }

    private boolean isList(Field f) {
        return List.class.isAssignableFrom(f.getType());
    }

    private Object fieldToObject(Field f) {
        try {
            return f.get(view);
        } catch (IllegalAccessException e) {
            throw new TrexSeleniumException("Couldn't analyze required fields.", e);
        }
    }

    /**
     * Those are only supported types which make sense to look at.
     */
    private boolean isViewElementFindableOrList(Field field) {
        Class<?> fieldType = field.getType();
        boolean result =  View.class.isAssignableFrom(fieldType)
                    || TestObjectFacade.class.isAssignableFrom(fieldType)
                    //|| Findable.class.isAssignableFrom(fieldType)
                    || UIElement.class.isAssignableFrom(fieldType)
                    || List.class.isAssignableFrom(fieldType);
        return result;
    }

    /**
     * Contexts must be implicitly present if anything in this view is is to be present.
     */
    private boolean isNotAnnotatedWithContext(Field field) {
        boolean result = field.getAnnotation(Context.class) == null;
        return result;
    }

    /**
     * Determines whether a field is required or not based on combination of Require, RequireAll,
     * and NotRequired annotations.
     */
    private boolean isRequired(Field field) {
        boolean result;
            result = field.getAnnotation(Require.class) != null
                    // Use the field's declaring class for RequireAll; may be a super class
                    || (field.getDeclaringClass().getAnnotation(RequireAll.class) != null
                    && field.getAnnotation(NotRequired.class) == null);
            return result;
    }

    private boolean isFindByAnnotated(Field field) {
        boolean result;
        result = field.getAnnotation(FindBy.class) != null;
        return result;
    }
}
