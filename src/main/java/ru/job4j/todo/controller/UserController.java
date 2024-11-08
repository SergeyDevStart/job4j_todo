package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.user.UserService;
import ru.job4j.todo.util.TimeZoneList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("zones", TimeZoneList.getTimeZone());
        model.addAttribute("defZone", TimeZoneList.getDefaultTimeZone());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким логином уже существует.");
            return "users/register";
        }
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно.");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/tasks";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
