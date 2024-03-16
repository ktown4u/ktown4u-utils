package com.ktown4u.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Arrays;

public enum YamlPrinter {
    ;
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // Disable auto-detection for all methods
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // Enable visibility for fields
        // @JsonFilter가 붙은 클래스만 필터링할 수 있는 것 같음. 모든 클래스에 쉽게 필터링을 적용하기 위해 Object에 mix-in 적용.
        mapper.addMixIn(Object.class, PropertyFilterMixIn.class);
    }

    public static String print(final Object object) {
        return printWithExclusions(object);
    }

    public static String printWithExclusions(final Object object, final String... fieldNamesToExclude) {
        final SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(fieldNamesToExclude);
        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PropertyFilter", filter);
        final ObjectWriter writer = mapper.writer(filterProvider);
        return writeValueAsString(writer, object);
    }

    public static String printWithInclusions(final Object object, final String... fieldPathToInclude) {
        final SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(splitAndFlatten(fieldPathToInclude));
        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("PropertyFilter", filter);
        final ObjectWriter writer = mapper.writer(filterProvider);
        return writeValueAsString(writer, object);
    }

    private static String[] splitAndFlatten(final String[] fieldNamesToInclude) {
        return Arrays.stream(fieldNamesToInclude)
                .flatMap(s -> Arrays.stream(s.split("\\.")))
                .toArray(String[]::new);
    }

    private static String writeValueAsString(final ObjectWriter writer, final Object object) {
        try {
            return writer.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonFilter("PropertyFilter")
    private static class PropertyFilterMixIn {}
}