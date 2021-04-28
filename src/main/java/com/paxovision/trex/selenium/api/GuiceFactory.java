package com.paxovision.trex.selenium.api;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import com.paxovision.trex.selenium.driver.WebDriverFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.SearchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class GuiceFactory {

    private static WebDriverFactory driverFactory = WebDriverFactory.getInstance();
    public static Injector TestObjectGuiceModuleInjector = Guice.createInjector(new TestObjectGuiceModule());
    public static Injector TestObjectListGuiceModuleInjector = Guice.createInjector(new TestObjectListGuiceModule());

    public static class TestObjectGuiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(SearchContext.class)
                    .toInstance(driverFactory.getDriver());
            //bindInterceptor(Matchers.any(),Matchers.any(),new GuiceFactory.TestObjectMethodInterceptor());
            //bindInterceptor(Matchers.subclassesOf(UIElement.class),Matchers.any(),new GuiceFactory.TestObjectMethodInterceptor());
            bindInterceptor(Matchers.subclassesOf(TestObjectFacade.class),Matchers.any(),new TestObjectMethodInterceptor());

        }
    }

    public static class TestObjectListGuiceModule extends  AbstractModule {
        @Override
        protected void configure() {
            bind(SearchContext.class)
                    .toInstance(driverFactory.getDriver());

            bindInterceptor(Matchers.any(),Matchers.any(),new TestObjectListMethodInterceptor());
            bindInterceptor(Matchers.subclassesOf(TestObjectListFacade.class),Matchers.any(),new TestObjectListMethodInterceptor());
            /*bindInterceptor(
                    Matchers.any(),
                    Matchers.subclassesOf(TestObjectListFacade.class),
                    new GuiceFactory.TestObjectListMethodInterceptor()
            );*/


            /* bindInterceptor(Matchers.any(),
                Matchers.any(),
                new TestObjectListMethodInterceptor()
        );*/


        }

    }

    public static class TestObjectMethodInterceptor implements MethodInterceptor {
        private static Logger logger = LoggerFactory.getLogger(TestObjectFacade.class);

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {

            Object x = methodInvocation.getThis();
            if( Modifier.toString(methodInvocation.getMethod().getModifiers()).contains("public") &&
                methodInvocation.getMethod().getName() != "toString") {

                Object[] args = methodInvocation.getArguments();
                if(args != null && args.length > 0) {
                    String[] parms = new String[args.length];
                    for(int i = 0; i < args.length; i++){
                        if(args[i] == null){
                            parms[i] = "null";
                        }
                        else {
                            if (args[i] instanceof CharSequence[]) {
                                CharSequence[] val = (CharSequence[]) args[i];
                                parms[i] = String.join("", val).toString();
                            } else if (args[i] instanceof CharSequence) {
                                parms[i] = args[i].toString();
                            } else {
                                parms[i] = args[i].toString();
                            }
                        }
                    }
                    logger.trace("Invoke -> " + x.toString() + "."  + methodInvocation.getMethod().getName() + "(" + String.join(",",parms) + ")");
                }
                else{
                    logger.trace("Invoke -> "+ x.toString() + "."  + methodInvocation.getMethod().getName() + "()");
                }
           }
            return methodInvocation.proceed();
        }

        private String[] getParameterNames(Parameter[] parameters) {
            String[] parameterNames = new String[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter param = parameters[i];
                System.out.println(param);
                //if (!param.isNamePresent()) {
                   // return null;
               // }
               // parameterNames[i] = param.;
            }
            return parameterNames;
        }
    }

    public static class TestObjectListMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Object x = methodInvocation.getThis();
            //System.out.println("*** Executing:" + methodInvocation.getMethod().getName());
            return methodInvocation.proceed();
        }
    }

}
