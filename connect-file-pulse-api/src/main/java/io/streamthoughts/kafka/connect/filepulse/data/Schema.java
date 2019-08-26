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

import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_BOOLEAN;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_BYTES;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_FLOAT_32;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_FLOAT_64;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_INT_16;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_INT_32;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_INT_64;
import static io.streamthoughts.kafka.connect.filepulse.data.SimpleSchema.SCHEMA_STRING;

public interface Schema {

    static Schema none() {
        return EmptySchema.INSTANCE;
    }

    static Schema of(final Type type) {
        if (type.isPrimitive()) {
            return SimpleSchema.forType(type);
        }
        return null;
    }

    /**
     * Gets a schema for type STRING.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema string() {
        return SCHEMA_STRING;
    }

    /**
     * Gets a schema for type INT_64.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema int64() {
        return SCHEMA_INT_64;
    }

    /**
     * Gets a schema for type INT_16.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema int16() {
        return SCHEMA_INT_16;
    }

    /**
     * Gets a schema for type INT_32.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema int32() {
        return SCHEMA_INT_32;
    }

    /**
     * Gets a schema for type FLOAT_32.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema float32() {
        return SCHEMA_FLOAT_32;
    }

    /**
     * Gets a schema for type FLOAT_64.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema float64() {
        return SCHEMA_FLOAT_64;
    }

    /**
     * Gets a schema for type BOOLEAN.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema bool() {
        return SCHEMA_BOOLEAN;
    }

    /**
     * Gets a schema for type array of BYTES.
     *
     * @return  the {@link Schema} instance.
     */
    static Schema bytes() {
        return SCHEMA_BYTES;
    }

    /**
     * Gets the schema for type STRUCT.
     *
     * @return  the {@link Schema} instance.
     */
    static StructSchema struct() {
        return new StructSchema();
    }

    /**
     * Gets the schema for type MAP.
     *
     * @return  the {@link Schema} instance.
     */
    static MapSchema map(final Schema valueSchema) {
        return new MapSchema(valueSchema);
    }

    /**
     * Gets the schema for type ARRAY.
     *
     * @return  the {@link Schema} instance.
     */
    static ArraySchema array(final Schema valueSchema) {
        return new ArraySchema(valueSchema);
    }

    /**
     * Returns the {@link Type} for this schema.
     * @return the schema {@link Type}.
     */
    Type type() ;

    /**
     * Maps this schema into a new type T.
     *
     * @param mapper    the {@link SchemaMapper} to be used.
     * @param <T>       the new type.
     * @return          the schema mapped to T.
     */
    default <T> T map(final SchemaMapper<T> mapper) {
        throw new UnsupportedOperationException("this method is not supported for schema of type : " + type());
    }

    default <T> T map(final SchemaMapperWithValue<T> mapper, Object object) {
        throw new UnsupportedOperationException("this method is not supported for schema of type : " + type());
    }

    class EmptySchema implements Schema {

        static EmptySchema INSTANCE = new EmptySchema();

        @Override
        public Type type() {
            return Type.NULL;
        }

        @Override
        public String toString() {
            return "[type="+Type.NULL+"]";
        }
    }
}
