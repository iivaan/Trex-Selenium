package com.paxovision.trex.selenium.api;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestObjectListMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object x = methodInvocation.getThis();
        System.out.println("*** Executing:" + methodInvocation.getMethod().getName());
        return methodInvocation.proceed();
    }
}
