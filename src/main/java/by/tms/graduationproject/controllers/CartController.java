package by.tms.graduationproject.controllers;

import by.tms.graduationproject.model.Item;
import by.tms.graduationproject.model.User;
import by.tms.graduationproject.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/cart")
    public String showCart(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<Item> items = user.getItems();
        model.addAttribute("items", items);
        return "cart/cart-main";
    }

    @GetMapping("/cart_add/{id}")
    public String addItemInCart(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepo.existsById(id)) {
            return "redirect:/cart";
        }
        int itemGroup = 0;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = itemRepo.findById(id).orElseThrow();
        Collection<Item> items = user.getItems();
        for (Item i : items) {
            if (item.getName().equals(i.getName())) {
                if (i.getProductCode() == MainController.G_INT_1) {
                    itemGroup = MainController.G_INT_1;
                } else if (i.getProductCode() == MainController.G_INT_2) {
                    itemGroup = MainController.G_INT_2;
                } else if (i.getProductCode() == MainController.G_INT_3) {
                    itemGroup = MainController.G_INT_3;
                }
                model.addAttribute("item", item);
                return "redirect:/group" + itemGroup;
            }
        }
        items.add(item);
        for (Item i : items) {
            if (item.getName().equals(i.getName())) {
                i.setAmount(1);
                if (i.getProductCode() == MainController.G_INT_1) {
                    itemGroup = MainController.G_INT_1;
                } else if (i.getProductCode() == MainController.G_INT_2) {
                    itemGroup = MainController.G_INT_2;
                } else if (i.getProductCode() == MainController.G_INT_3) {
                    itemGroup = MainController.G_INT_3;
                }
            }
        }
        user.setItems(items);
        model.addAttribute("item", item);
        return "redirect:/group" + itemGroup;
    }

    @GetMapping("/buy_item/{id}")
    public String buyItemInCart(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepo.existsById(id)) {
            return "redirect:/cart";
        }
        Optional<Item> item = itemRepo.findById(id);
        ArrayList<Item> list = new ArrayList<>();
        item.ifPresent(list::add);
        model.addAttribute("item", list);
        return "cart/cart-item-buy";
    }

    @PostMapping("/buy_item/{id}")
    public String buyItemInCart(@PathVariable(value = "id") long id, @RequestParam int amount, Model model) {
        if (!itemRepo.existsById(id)) {
            return "redirect:/cart";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = itemRepo.findById(id).orElseThrow();
        Collection<Item> items = user.getItems();
        for (Item i : items) {
            if (item.getName().equals(i.getName())) {
                if (item.getAmount() > amount) {
                    item.setAmount(item.getAmount() - amount);
                } else if (item.getAmount() < amount) {
                    model.addAttribute("amount", "столько нет в наличии");
                    return "redirect:/cart";
                }
                i.setAmount(amount);
                i.setPrice(amount * item.getPrice());
                i.setItemStatus(true);
            }
        }
        itemRepo.save(item);
        user.setItems(items);
        model.addAttribute("item", item);
        return "redirect:/cart";
    }

    @GetMapping("/cart/{id}/delete")
    public String deleteItemPost(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepo.existsById(id)) {
            return "redirect:/cart";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<Item> items = user.getItems();
        Item item = itemRepo.findById(id).orElseThrow();
        items.removeIf(i -> i.getName().equals(item.getName()));
        user.setItems(items);
        model.addAttribute("items", items);
        return "redirect:/cart";
    }
}
