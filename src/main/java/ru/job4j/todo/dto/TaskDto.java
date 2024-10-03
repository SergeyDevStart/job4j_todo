package ru.job4j.todo.dto;

import lombok.*;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime created = LocalDateTime.now().withNano(0);
    private boolean done;
    private User user;
    private Integer priorityId;
    private List<Integer> categoryIds;
}
