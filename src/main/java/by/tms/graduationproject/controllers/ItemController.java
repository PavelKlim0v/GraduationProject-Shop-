package by.tms.graduationproject.controllers;

import by.tms.graduationproject.model.Item;
import by.tms.graduationproject.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ItemController {
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/item/add")
    public String createItem(Model model) {
        return "shop/item-action";
    }

    @PostMapping("/item/add")
    public String showCreateItem(@RequestParam String name, @RequestParam double price, @RequestParam int amount,
                                 @RequestParam int productCode, @RequestParam String description, Model model) {
        itemRepo.save(new Item(name, price, amount, productCode, description, false));
        return "redirect:/";
    }

    @GetMapping("/item/{id}/edit")
    public String editItem(@PathVariable(value = "id") long id, Model model) {
        if (!itemRepo.existsById(id)) {
            return "redirect:/main";
        }
        Optional<Item> item = itemRepo.findById(id);
        ArrayList<Item> list = new ArrayList<>();
        item.ifPresent(list::add);
        model.addAttribute("item", list);
        return "shop/item-edit";
    }

    @PostMapping("/item/{id}/edit")
    public String showEditItem(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam double price,
                               @RequestParam int amount, @RequestParam int productCode, @RequestParam String description, Model model) {
        Item item = itemRepo.findById(id).orElseThrow();
        item.setName(name);
        item.setPrice(price);
        item.setAmount(amount);
        item.setProductCode(productCode);
        item.setDescription(description);
        item.setItemStatus(false);
        itemRepo.save(item);
        return "redirect:/";
    }

    @GetMapping("/item/{id}/delete")
    public String deleteItem(@PathVariable(value = "id") long id, Model model) {
        itemRepo.deleteById(id);
        return "redirect:/";
    }
}
