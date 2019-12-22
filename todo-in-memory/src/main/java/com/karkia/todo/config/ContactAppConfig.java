package com.karkia.todo.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * Unfortunately, adding spring properties application.properties/yml doesn't work with the Java 8 date types,
 * like LocalDate and LocalDateTime – we can use it only to format fields of the type java.util.Date or the java.util.Calendar.
 * The Solution::::
 * So, if we want to use Java 8 date types and set a default date format,
 * then we need to look at creating a Jackson2ObjectMapperBuilderCustomizer bean
 */
@Configuration
public class ContactAppConfig {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }
}
