package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.category.CategoryService;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService hibernateTaskService;
    private final PriorityService hibernatePriorityService;
    private final CategoryService hibernateCategoryService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("priorities", hibernatePriorityService.findAll());
        model.addAttribute("categories", hibernateCategoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TaskDto taskDto, Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        taskDto.setUser(user);
        var savedTask = hibernateTaskService.create(taskDto);
        if (savedTask.isEmpty()) {
            model.addAttribute("message", "Не удалось создать задачу");
            return "errors/error";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") Integer id) {
        var taskOptional = hibernateTaskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/error";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") Integer id) {
        var taskOptional = hibernateTaskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/error";
        }
        model.addAttribute("priorities", hibernatePriorityService.findAll());
        model.addAttribute("categories", hibernateCategoryService.findAll());
        model.addAttribute("task", taskOptional.get());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TaskDto taskDto, Model model) {
        try {
            boolean isUpdated = hibernateTaskService.update(taskDto);
            if (!isUpdated) {
                model.addAttribute("message", "Не удалось обновить задачу");
                return "errors/error";
            }
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/error";
        }
    }

    @GetMapping("/complete/{id}")
    public String done(Model model, @PathVariable("id") Integer id) {
        boolean isUpdated = hibernateTaskService.changeStatus(id);
        if (!isUpdated) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/error";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") Integer id) {
        boolean isDeleted = hibernateTaskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/error";
        }
        return "redirect:/tasks";
    }

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", hibernateTaskService.taskWrapper(hibernateTaskService.findAll(), user));
        return "tasks/list";
    }

    @GetMapping("/completed")
    public String getCompleted(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", hibernateTaskService.taskWrapper(hibernateTaskService.findCompleted(), user));
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNew(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", hibernateTaskService.taskWrapper(hibernateTaskService.findNew(), user));
        return "tasks/list";
    }
}
