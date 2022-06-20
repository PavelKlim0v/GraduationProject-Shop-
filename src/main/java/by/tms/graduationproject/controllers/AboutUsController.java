package by.tms.graduationproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {
    private static final String ABOUT_US = "Страница о нас";
    private static final String ABOUT_US_INFO = "Ну очень интересная информация, а главное просто и понятно!";

    @GetMapping("/about_us")
    public String aboutUs(Model model) {
        model.addAttribute("message", ABOUT_US);
        model.addAttribute("info", ABOUT_US_INFO);
        return "aboutus/aboutUs";
    }
}
