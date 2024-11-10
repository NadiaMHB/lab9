package org.example.lab9.controller;

import jdk.jfr.Category;
import org.example.lab9.Dao.CategoryDao;
import org.example.lab9.Entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("isSearch", false);
        return "categories/list";
    }

    @GetMapping("/search")
    public String buscarComida(@RequestParam("value") String value, Model model) {
        List<Meal> meals = categoryDao.buscarComidaPorNombre(value);
        model.addAttribute("meals", meals);
        model.addAttribute("isSearch", true);
        return "categories/list";
    }

    @GetMapping("/meal/detail")
    public String mostrarDetalleComida(@RequestParam("id") String idMeal, Model model) {
        Meal meal = categoryDao.obtenerDetalleComida(idMeal);
        model.addAttribute("meal", meal);
        return "meal/details";
    }
}