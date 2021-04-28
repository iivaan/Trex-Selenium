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

import java.util.function.Supplier;

/**
 * Provides default implementations of {@link #describedAs(String)} and {@link #toString()}. If an
 * implementation can provide a somewhat useful description automatically, it should do so by
 * calling {@link #describedAs(String)} itself.
 */
public abstract class AbstractEvent<T> implements Event<T> {
    private Supplier<String> description = super::toString;

    @Override
    public Event<T> describedAs(Supplier<String> description) {
        this.description = description;

        return this;
    }

    @Override
    public String toString() {
        return description.get();
    }
}
