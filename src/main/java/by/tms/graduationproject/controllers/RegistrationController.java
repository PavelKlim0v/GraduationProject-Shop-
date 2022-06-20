package by.tms.graduationproject.controllers;

import by.tms.graduationproject.model.Role;
import by.tms.graduationproject.model.User;
import by.tms.graduationproject.repository.UserRepo;
import by.tms.graduationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String phoneNumber,
                          @RequestParam String email, Model model, Map<String, Object> mod) {
        User user = new User(username, password, phoneNumber, email);
        if (userService.findByUsername(user.getUsername()) != null) {
            mod.put("message", "User exists!");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER_ROLE));
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }
}
