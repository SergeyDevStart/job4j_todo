package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class HibernateCategoryService implements CategoryService {
    private final CategoryRepository hibernateCategoryRepository;

    @Override
    public Collection<Category> findAll() {
        return hibernateCategoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByIds(Collection<Integer> ids) {
        return hibernateCategoryRepository.findAllByIds(ids);
    }
}
