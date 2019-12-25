package com.karkia.todo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ToDoRestClientProperties class that holds the URL and the basePath information.
 * <p>
 * Spring Boot allows you to create custom-typed properties that can be accessed
 * and mapped from the application.properties file; the only requirement is that you
 * need to mark the class with the @ConfigurationProperties annotation
 */
@Component
@ConfigurationProperties(prefix = "todo")
@Data
public class ToDoRestClientProperties {
    private String url;
    private String basePath;
}
