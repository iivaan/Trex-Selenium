package com.paxovision.trex.selenium.guice;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import com.google.inject.util.Modules;
import net.jmob.guice.conf.core.ConfigurationModule;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import com.google.inject.Module;
import java.util.List;
import java.util.Optional;

public class InjectionPoint implements BeforeTestExecutionCallback {
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {

        List<Module> modules = Lists.newArrayList(new ConfigurationModule());

        Optional<Object> test = context.getTestInstance();

        if (test.isPresent()) {
            RequiresInjection requiresInjection = test.get().getClass().getAnnotation(RequiresInjection.class);

            if (requiresInjection != null) {
                for (Class c : requiresInjection.values()) {
                    modules.add((Module) c.newInstance());
                }
            }

            Module aggregate = Modules.combine(modules);
            Injector injector = Guice.createInjector(aggregate);

            injector.injectMembers(test.get());
            getStore(context).put(injector.getClass(), injector);
        }

    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass()));
    }
}
