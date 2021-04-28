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
import java.util.function.Supplier;

public class MultiEventWithPollEvent<T> extends MultiEvent<T> implements PollEvent<T> {
    
    public MultiEventWithPollEvent(Event<? extends T> original, PollEvent<? extends T> additional) {
        super(original, additional);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PollEvent<T> pollingEvery(Duration pollingInterval) {
        ((PollEvent<T>) additional).pollingEvery(pollingInterval);
        
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PollEvent<T> ignoring(Class<? extends Exception> exception) {
        ((PollEvent<T>) additional).ignoring(exception);
        
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PollEvent<T> describedAs(String description) {
        super.describedAs(description);

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PollEvent<T> describedAs(Supplier<String> description) {
        super.describedAs(description);

        return this;
    }
}
