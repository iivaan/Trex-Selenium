/*
 Copyright 2014 Red Hat, Inc. and/or its affiliates.

 This file is part of synq.

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

package com.paxovision.trex.selenium.synq;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForwardingFailPollEvent<T> extends ForwardingFailEvent<T> implements FailPollEvent<T> {
    
    public ForwardingFailPollEvent(PollEvent<?> original) {
        super(original.ignoring(Exception.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public FailPollEvent<T> pollingEvery(Duration pollingInterval) {
        ((PollEvent<T>) original).pollingEvery(pollingInterval);
        
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FailPollEvent<T> ignoring(Class<? extends Exception> exception) {
        ((PollEvent<T>) original).ignoring(exception);
        
        return this;
    }
    
    @Override
    public FailPollEvent<T> throwing(Throwable throwable) {
        super.throwing(throwable);

        return this;
    }

    @Override
    public FailPollEvent<T> throwing(Function<AssertionError, Throwable> throwable) {
        super.throwing(throwable);

        return this;
    }

    @Override
    public FailPollEvent<T> describedAs(String description) {
        super.describedAs(description);

        return this;
    }

    @Override
    public FailPollEvent<T> describedAs(Supplier<String> description) {
        super.describedAs(description);

        return this;
    }
}
