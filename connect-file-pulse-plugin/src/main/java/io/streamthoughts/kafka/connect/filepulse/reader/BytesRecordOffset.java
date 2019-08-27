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
package io.streamthoughts.kafka.connect.filepulse.reader;

import io.streamthoughts.kafka.connect.filepulse.source.SourceOffset;
import io.streamthoughts.kafka.connect.filepulse.source.TimestampedRecordOffset;
import org.apache.kafka.common.utils.SystemTime;

import java.util.Objects;

public class BytesRecordOffset extends TimestampedRecordOffset {

    private final long startPosition;

    private final long endPosition;


    public static BytesRecordOffset empty() {
        return new BytesRecordOffset(
                -1,
                -1,
                SystemTime.SYSTEM.milliseconds());
    }

    public static BytesRecordOffset with(long startPosition, long endPosition) {
        return new BytesRecordOffset(
                startPosition,
                endPosition,
                SystemTime.SYSTEM.milliseconds());
    }

    /**
     * Creates a new {@link BytesRecordOffset} instance.
     *
     * @param startPosition the starting position.
     * @param endPosition   the ending position.
     * @param timestamp     the current timestamp.
     */
    public BytesRecordOffset(long startPosition,
                             long endPosition,
                             long timestamp) {
        super(timestamp);
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
    /**
     * Returns the starting position.
     *
     * @return the long position.
     */
    public long startPosition() {
        return this.startPosition;
    }


    /**
     * Returns the ending position.
     *
     * @return the long position.
     */
    public long endPosition() {
        return this.endPosition;
    }

    public SourceOffset toSourceOffset() {
        return new SourceOffset(endPosition, -1, timestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BytesRecordOffset)) return false;
        if (!super.equals(o)) return false;
        BytesRecordOffset that = (BytesRecordOffset) o;
        return startPosition == that.startPosition &&
                endPosition == that.endPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startPosition, endPosition);
    }

    @Override
    public String toString() {
        return "[" +
                "startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", timestamp=" + timestamp() +
                ']';
    }
}
