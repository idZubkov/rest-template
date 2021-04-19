package edu.zubkov.crudapp.controllers;

import edu.zubkov.crudapp.models.Role;
import edu.zubkov.crudapp.models.User;
import edu.zubkov.crudapp.services.RoleService;
import edu.zubkov.crudapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(name = "id") long id, Model model) {
        User user = userService.getById(id);
        List<Role> roles = roleService.listOfRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String editUser(@Validated(User.class) @ModelAttribute("user") User user,
                           @RequestParam("authorities") List<String> listOfStrings) {
        Set<Role> roleSet = userService.getAllRoles(listOfStrings);
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        List<Role> listOfRoles = roleService.listOfRoles();
        model.addAttribute(new User());
        model.addAttribute("roles", listOfRoles);
        return "create";
    }

    @PostMapping("/new")
    public String create(@Validated(User.class) @ModelAttribute("user") User user,
                         @RequestParam("authorities") List<String> listOfStrings) {
        Set<Role> setOfRoles = userService.getAllRoles(listOfStrings);
        user.setRoles(setOfRoles);
        userService.add(user);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/";
    }
}