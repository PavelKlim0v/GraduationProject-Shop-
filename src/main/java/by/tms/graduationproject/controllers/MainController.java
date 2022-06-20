package by.tms.graduationproject.controllers;

import by.tms.graduationproject.model.Item;
import by.tms.graduationproject.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {
    private static final String TITLE = "Главная страница";
    private static final String GROUP_ONE = "Материалы";
    private static final String GROUP_TWO = "Лаки и Краски";
    private static final String GROUP_THREE = "Инструменты";

    public static final int G_INT_1 = 1;
    public static final int G_INT_2 = 2;
    public static final int G_INT_3 = 3;

    @Autowired
    private ItemRepo itemRep;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("TITLE", TITLE);
        model.addAttribute("GROUP_ONE", GROUP_ONE);
        model.addAttribute("GROUP_TWO", GROUP_TWO);
        model.addAttribute("GROUP_THREE", GROUP_THREE);
        return "home/main";
    }

    @GetMapping("/group1")
    public String group(Model model) {
        Iterable<Item> items = itemRep.findAll();
        ArrayList<Item> list = new ArrayList<>();
        for (Item i : items) {
            if (G_INT_1 == i.getProductCode()) {
                list.add(i);
            }
        }
        model.addAttribute("items", list);
        return "home/group1";
    }

    @GetMapping("/group2")
    public String group2(Model model) {
        Iterable<Item> items = itemRep.findAll();
        ArrayList<Item> list = new ArrayList<>();
        for (Item i : items) {
            if (G_INT_2 == i.getProductCode()) {
                list.add(i);
            }
        }
        model.addAttribute("items", list);
        return "home/group2";
    }

    @GetMapping("/group3")
    public String group3(Model model) {
        Iterable<Item> items = itemRep.findAll();
        ArrayList<Item> list = new ArrayList<>();
        for (Item i : items) {
            if (G_INT_3 == i.getProductCode()) {
                list.add(i);
            }
        }
        model.addAttribute("items", list);
        return "home/group3";
    }

    @GetMapping("/group1/{id}")
    public String addItemGroupOne(@PathVariable(value = "id") long id, Model model) {
        if (!itemRep.existsById(id)) {
            return "redirect:/";
        }
        model.addAttribute("productCode", G_INT_1);
        Optional<Item> item = itemRep.findById(id);
        ArrayList<Item> list = new ArrayList<>();
        item.ifPresent(list::add);
        model.addAttribute("item", list);
        return "shop/item-details";
    }

    @GetMapping("/group2/{id}")
    public String addItemGroupTwo(@PathVariable(value = "id") long id, Model model) {
        if (!itemRep.existsById(id)) {
            return "redirect:/";
        }
        model.addAttribute("productCode", G_INT_2);
        Optional<Item> item = itemRep.findById(id);
        ArrayList<Item> list = new ArrayList<>();
        item.ifPresent(list::add);
        model.addAttribute("item", list);
        return "shop/item-details";
    }

    @GetMapping("/group3/{id}")
    public String addItemGroupThree(@PathVariable(value = "id") long id, Model model) {
        if (!itemRep.existsById(id)) {
            return "redirect:/";
        }
        model.addAttribute("productCode", G_INT_3);
        Optional<Item> item = itemRep.findById(id);
        ArrayList<Item> list = new ArrayList<>();
        item.ifPresent(list::add);
        model.addAttribute("item", list);
        return "shop/item-details";
    }
}
