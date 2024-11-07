package ru.job4j.todo.service.task;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Optional<Task> create(TaskDto taskDto);

    boolean update(TaskDto taskDto);

    boolean deleteById(Integer id);

    Optional<Task> findById(Integer id);

    Collection<Task> findAll();

    Collection<Task> findNew();

    Collection<Task> findCompleted();

    Collection<Task> taskWrapper(Collection<Task> tasks, User user);

    boolean changeStatus(Integer id);
}
