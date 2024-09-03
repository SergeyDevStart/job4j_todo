package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index"})
    public String getIndexPage() {
        return "index";
    }
}
