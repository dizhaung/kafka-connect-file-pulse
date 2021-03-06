/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.streamthoughts.kafka.connect.filepulse.filter;

import java.util.Objects;

public class FilterError {

    private final String message;

    private final String filter;

    /**
     * Creates a new {@link FilterError} instance.
     *
     * @param error     the error message message.
     * @param filter    the filter name that failed.
     */
    FilterError(final String error, final String filter) {
        Objects.requireNonNull(error, "The message message");
        Objects.requireNonNull(filter, "The filter message");
        this.message = error;
        this.filter = filter;
    }

    public String message() {
        return message;
    }

    public String filter() {
        return filter;
    }

    @Override
    public String toString() {
        return "[" +
                "message=" + message +
                ", filter=" + filter +
                ']';
    }
}
