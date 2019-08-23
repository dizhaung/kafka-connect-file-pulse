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
package io.streamthoughts.kafka.connect.filepulse.config;

import io.streamthoughts.kafka.connect.filepulse.filter.config.CommonFilterConfig;
import io.streamthoughts.kafka.connect.filepulse.internal.LocaleUtils;
import org.apache.kafka.common.config.ConfigDef;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DateFilterConfig extends CommonFilterConfig {

    public static final String DATE_FIELD_CONFIG = "field";
    private static final String DATE_FIELD_DOC = "The field to get the date from.";

    public static final String DATE_TARGET_CONFIG = "target";
    private static final String DATE_TARGET_DOC = "The target field.";

    public static final String DATE_TIMEZONE_CONFIG = "timezone";
    private static final String DATE_TIMEZONE_DOC = "The timezone to use for parsing date.";

    public static final String DATE_LOCALE_CONFIG = "locale";
    private static final String DATE_LOCALE_DOC = "The locale to use for parsing date.";

    public static final String DATE_FORMATS_CONFIG = "formats";
    private static final String DATE_FORMAT_DOC = "List of the expected date formats.";

    /**
     * Creates a new {@link DateFilterConfig} instance.
     *
     * @param originals the configuration.
     */
    public DateFilterConfig(final Map<?, ?> originals) {
        super(configDef(), originals);
    }

    public List<String> formats() {
        return getList(DATE_FORMATS_CONFIG);
    }

    public String field() {
        return getString(DATE_FIELD_CONFIG);
    }

    public String target() {
        return getString(DATE_TARGET_CONFIG);
    }

    public Locale locale() {
        String localeStr = getString(DATE_LOCALE_CONFIG);
        return (localeStr == null) ? Locale.ENGLISH : LocaleUtils.parse(localeStr);
    }

    public ZoneId timezone() {
        String zoneStr = getString(DATE_TIMEZONE_CONFIG);
        return (zoneStr == null) ? ZoneOffset.UTC : ZoneId.of(zoneStr);
    }

    public static ConfigDef configDef() {
        return CommonFilterConfig.configDef()
                .define(DATE_TIMEZONE_CONFIG, ConfigDef.Type.STRING, null,
                        ConfigDef.Importance.HIGH, DATE_TIMEZONE_DOC)
                .define(DATE_LOCALE_CONFIG, ConfigDef.Type.STRING,  null,
                        ConfigDef.Importance.HIGH, DATE_LOCALE_DOC)
                .define(DATE_FIELD_CONFIG, ConfigDef.Type.STRING,
                        ConfigDef.Importance.HIGH, DATE_FIELD_DOC)
                .define(DATE_TARGET_CONFIG, ConfigDef.Type.STRING,
                        ConfigDef.Importance.HIGH, DATE_TARGET_DOC)
                .define(DATE_FORMATS_CONFIG, ConfigDef.Type.LIST,
                        ConfigDef.Importance.HIGH, DATE_FORMAT_DOC);

    }
}

