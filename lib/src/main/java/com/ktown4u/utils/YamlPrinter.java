package com.ktown4u.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFilter;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.ser.FilterProvider;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.util.Arrays;

public enum YamlPrinter {
    ;
    private static final YAMLMapper mapper;

    static {
        mapper = YAMLMapper.builder()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                // Jackson 3에서 기본값이 true로 변경됨. Jackson 2와 동일한 필드 순서 유지를 위해 비활성화
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .changeDefaultVisibility(vc -> vc
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY))
                .addMixIn(Object.class, PropertyFilterMixIn.class)
                .build();
    }

    public static String print(final Object object) {
        return printWithExclusions(object);
    }

    public static String printWithExclusions(final Object object, final String... fieldNamesToExclude) {
        final SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(fieldNamesToExclude);
        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PropertyFilter", filter);
        final ObjectWriter writer = mapper.writer(filterProvider);
        return writer.writeValueAsString(object);
    }

    public static String printWithInclusions(final Object object, final String... fieldPathToInclude) {
        final SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(splitAndFlatten(fieldPathToInclude));
        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PropertyFilter", filter);
        final ObjectWriter writer = mapper.writer(filterProvider);
        return writer.writeValueAsString(object);
    }

    private static String[] splitAndFlatten(final String[] fieldNamesToInclude) {
        return Arrays.stream(fieldNamesToInclude)
                .flatMap(s -> Arrays.stream(s.split("\\.")))
                .toArray(String[]::new);
    }

    @JsonFilter("PropertyFilter")
    private static class PropertyFilterMixIn {}
}
