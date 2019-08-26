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
package io.streamthoughts.kafka.connect.filepulse.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Collection of (typed) values that can be retrieved by name.
 */
public interface SettableByName<T extends SettableByName> extends Serializable {

    /**
     * Put the content for the specified field name.
     *
     * @param field the name of the field.
     * @param object the object to put.
     * @param type the object type.
     */
    T put(final String field, final Type type, final Object object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param value   the object to put.
     */
    T put(final String field, final TypedStruct value)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param value   the object to put.
     */
    <E> T put(final String field, final List<E> value)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param value   the object to put.
     */
    <E> T put(final String field, final E[] value)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    <K, V> T put(final String field, final Map<K, V> object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Boolean object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Short object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Integer object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Long object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Double object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final Float object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final String object)
            throws DataException;

    /**
     * Put the content for the specified field name.
     *
     * @param field   the name of the field.
     * @param object  the object to put.
     */
    T put(final String field, final byte[] object)
            throws DataException;
}