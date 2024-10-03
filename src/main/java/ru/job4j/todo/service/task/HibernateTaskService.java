package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.mappers.TaskMapper;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.category.CategoryRepository;
import ru.job4j.todo.repository.priority.PriorityRepository;
import ru.job4j.todo.repository.task.TaskRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateTaskService implements TaskService {
    private final TaskRepository hibernataTaskRepository;
    private final PriorityRepository hibernatePriorityRepository;
    private final CategoryRepository hibernateCategoryRepository;
    private final TaskMapper taskMapper;

    @Override
    public Optional<Task> create(TaskDto taskDto) {
        return hibernataTaskRepository.create(getTaskFromTaskDto(taskDto));
    }

    @Override
    public boolean update(TaskDto taskDto) {
        return hibernataTaskRepository.update(getTaskFromTaskDto(taskDto));
    }

    @Override
    public boolean deleteById(Integer id) {
        return hibernataTaskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return hibernataTaskRepository.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return hibernataTaskRepository.findAll();
    }

    @Override
    public Collection<Task> findNew() {
        return hibernataTaskRepository.findNew();
    }

    @Override
    public Collection<Task> findCompleted() {
        return hibernataTaskRepository.findCompleted();
    }

    @Override
    public boolean changeStatus(Integer id) {
        return hibernataTaskRepository.changeStatus(id);
    }

    private Task getTaskFromTaskDto(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        var priority = hibernatePriorityRepository.findById(taskDto.getPriorityId()).orElseThrow();
        List<Category> categories = hibernateCategoryRepository.findAllByIds(taskDto.getCategoryIds());
        task.setPriority(priority);
        task.setCategories(categories);
        return task;
    }
}
