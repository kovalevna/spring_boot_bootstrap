package com.example.spring_boot.controllers;

import com.example.spring_boot.models.Role;
import com.example.spring_boot.models.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("my_role") String my_role) {
        Role role = new Role((my_role.equals("ADMIN") ? 1L : 2L), "ROLE" + my_role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap modelMap, @PathVariable("id") Long id) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @ModelAttribute("new_role") String new_role) {
        Role role = new Role((new_role.equals("ADMIN") ? 1L : 2L), "ROLE" + new_role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
