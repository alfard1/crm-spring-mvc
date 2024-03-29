package crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "fancy-login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @GetMapping(value = {"/home", "/"})
    public String showHome() {
        return "home";
    }

    @GetMapping("/more-info")
    public String showMoreInfo() {
        return "more-info";
    }

    @GetMapping("/api")
    public String showApi() {
        return "api";
    }
}
