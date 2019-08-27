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
package io.streamthoughts.kafka.connect.filepulse.expression.function.impl;

import io.streamthoughts.kafka.connect.filepulse.data.Type;
import io.streamthoughts.kafka.connect.filepulse.data.TypedValue;
import io.streamthoughts.kafka.connect.filepulse.expression.function.Arguments;
import io.streamthoughts.kafka.connect.filepulse.expression.function.ExpressionFunction;

/**
 * Simple function to retrieve the size of a array or a string field.
 */
public class Length implements ExpressionFunction<Arguments> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Arguments prepare(final TypedValue[] args) {
        return Arguments.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final TypedValue value) {
        return value.type() == Type.ARRAY || value.type() == Type.STRING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypedValue apply(final TypedValue field, final Arguments args) {

        int size = (field.type() == Type.ARRAY) ? field.getArray().size() : ((String)field.value()).length();
        return TypedValue.int32(size);
    }
}
