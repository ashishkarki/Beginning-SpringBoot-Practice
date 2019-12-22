package com.karkia.todo.domain;

/**
 * a Fluent API class that helps create a ToDo instance. You can see this class
 * a factory that creates a ToDo with a description or with a particular ID
 */
public class ToDoBuilder {

    private static ToDoBuilder instance = new ToDoBuilder();
    private String id = null;
    private String description = "";

    private ToDoBuilder() {
    }

    public static ToDoBuilder create() {
        return instance;
    }

    public ToDoBuilder withDescription(String description) {
        this.description = description;
        return instance;
    }

    public ToDoBuilder withId(String id) {
        this.id = id;
        return instance;
    }

    public ToDo build() {
        ToDo result = new ToDo(this.description);

        if (id != null) {
            result.setId(id);
        }

        return result;
    }
}
