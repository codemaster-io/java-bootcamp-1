package com.codemaster.io.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        if(principal != null) model.addAttribute("username", principal.getName());
        return "index"; // This will point to index.html
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public String dashboard(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "dashboard"; // This will point to index.html
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    public String admin(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    public String user(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user";
    }

    @DeleteMapping("/file/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('ADMIN:ALL_PERMISSION')")
    public String fileDelete(@PathVariable String id) {
        System.out.println("File DeleteRequest landed " + id );
        return "Success";
    }

    @GetMapping("/error_403")
    public String accessError() {
        System.out.println("error_403");
        return "error_403";
    }
}
