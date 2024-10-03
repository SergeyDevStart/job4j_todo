package ru.job4j.todo.service.category;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    Collection<Category> findAll();

    List<Category> findAllByIds(Collection<Integer> ids);
}
