package org.example.lab9.controller;

import jdk.jfr.Category;
import org.example.lab9.Dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/TeleDrink")
public class TeleController {

    private final CategoryDao categoryDao;

    @Autowired
    public TeleController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping("/categories")
    public String listarCategorias(Model model) {

        model.addAttribute("categories", categoryDao.listar());

        return "categories/list";
    }
}