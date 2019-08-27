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

import io.streamthoughts.kafka.connect.filepulse.config.MultiRowFilterConfig;
import io.streamthoughts.kafka.connect.filepulse.data.TypedStruct;
import io.streamthoughts.kafka.connect.filepulse.source.SourceMetadata;
import io.streamthoughts.kafka.connect.filepulse.reader.RecordsIterable;
import io.streamthoughts.kafka.connect.filepulse.source.FileRecordOffset;
import io.streamthoughts.kafka.connect.filepulse.source.TypedFileRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiRowFilterTest {

    private FilterContext context;

    private MultiRowFilter filter;

    private Map<String, String> configs;

    private static List<List<String>> INPUTS = new ArrayList<List<String>>() {{
        add(Collections.singletonList("[INFO] dummy log 1"));
        add(Collections.singletonList("[INFO] dummy log 2"));
        add(Arrays.asList("[ERROR] java.lang.RuntimeException: Big Error", "\tStackTrace-1", "\tStackTrace-2"));
        add(Collections.singletonList("[INFO] dummy log 3"));
        add(Arrays.asList("[ERROR] java.lang.RuntimeException: Second Big Error", "\tStackTrace-3", "\tStackTrace-4"));
    }};

    private static List<String> EXPECTED = INPUTS.stream()
            .map(ml -> String.join(MultiRowFilterConfig.MULTI_ROW_LINE_SEPARATOR_DEFAULT, ml))
            .collect(Collectors.toList());


    private RecordsIterable<TypedStruct> generates() {
        List<TypedStruct> records  = INPUTS.stream()
                .flatMap(Collection::stream)
                .map(m -> TypedStruct.struct().put("message", m))
                .collect(Collectors.toList());
        return new RecordsIterable<>(records);
    }

    @Before
    public void setUp() {
        filter = new MultiRowFilter();
        configs = new HashMap<>();
        context = FilterContextBuilder.newBuilder()
                .withMetadata(new SourceMetadata("", "", 0L, 0L, 0L, -1L))
                .withOffset(FileRecordOffset.invalid())
                .build();
    }

    @Test
    public void shouldMatchMultiLinesGivenNegateParamsEqualsToFalse() {
        configs.put(MultiRowFilterConfig.MULTI_ROW_NEGATE_CONFIG, "false");
        configs.put(MultiRowFilterConfig.MULTI_ROW_PATTERN_CONFIG, "^[\\t]");
        filter.configure(configs);

        List<TypedStruct> output = new LinkedList<>();
        Iterator<TypedStruct> iterator = generates().iterator();
        while (iterator.hasNext()) {
            output.addAll(filter.apply(context, iterator.next(), iterator.hasNext()).collect());
        }
        assertOutput(output);
    }

    @Test
    public void shouldMatchMultiLinesGivenNegateParamsEqualsToTrue() {
        configs.put(MultiRowFilterConfig.MULTI_ROW_NEGATE_CONFIG, "true");
        configs.put(MultiRowFilterConfig.MULTI_ROW_PATTERN_CONFIG, "^\\[INFO|ERROR\\]");
        filter.configure(configs);

        List<TypedStruct> output = new LinkedList<>();
        Iterator<TypedStruct> iterator = generates().iterator();
        while (iterator.hasNext()) {
            output.addAll(filter.apply(context, iterator.next(), iterator.hasNext()).collect());
        }
        assertOutput(output);
    }

    private void assertOutput(List<TypedStruct> records) {
        Assert.assertEquals(EXPECTED.size(), records.size());

        for (int i = 0; i < EXPECTED.size(); i++) {
            String value = records.get(i).getString(TypedFileRecord.DEFAULT_MESSAGE_FIELD);
            Assert.assertEquals(EXPECTED.get(i), value);
        }
    }
}