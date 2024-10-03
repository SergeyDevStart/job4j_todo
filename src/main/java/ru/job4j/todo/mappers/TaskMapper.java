package ru.job4j.todo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "priority.id", target = "priorityId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "categoriesToCategoryIds")
    TaskDto toTaskDto(Task task);

    @Mapping(source = "priorityId", target = "priority.id")
    Task toTask(TaskDto taskDto);

    @Named("categoriesToCategoryIds")
    default List<Integer> categoriesToCategoryIds(List<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
}
