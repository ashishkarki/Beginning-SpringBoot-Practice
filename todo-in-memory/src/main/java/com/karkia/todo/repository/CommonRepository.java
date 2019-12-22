package com.karkia.todo.repository;

import java.util.Collection;

/**
 * create an interface that has common persistence actions. This interface is generic,
 * so it easy to use any other implementation, making the repo an extensible solution
 */
public interface CommonRepository<T> {
    public T save(T domain);

    public Iterable<T> save(Collection<T> domains);

    public void delete(T domain);

    public T findById(String id);

    public Iterable<T> findAll();
}
