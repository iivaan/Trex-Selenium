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

public class ForwardingEvent<T> implements Event<T> {
    protected final Event<T> event;
    
    public ForwardingEvent(Event<T> event) {
        this.event = event;
    }

    @Override
    public T waitUpTo(Duration duration) {
        return event.waitUpTo(duration);
    }

    @Override
    public Event<T> describedAs(String description) {
        return event.describedAs(description);
    }

    @Override
    public Event<T> describedAs(Supplier<String> description) {
        return event.describedAs(description);
    }

    @Override
    public String toString() {
        return event.toString();
    }
}
