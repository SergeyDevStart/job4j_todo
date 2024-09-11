package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.task.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateTaskService implements TaskService {
    private final TaskRepository hibernataTaskRepository;

    @Override
    public Optional<Task> create(Task task) {
        return hibernataTaskRepository.create(task);
    }

    @Override
    public boolean update(Task task) {
        return hibernataTaskRepository.update(task);
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
}
