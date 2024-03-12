package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleDao;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleRepository roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping()
    public String showAdminPage(){
        return "admin";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping(value = "/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        List<Role> roleList = roleDao.findAll();
        model.addAttribute("allRoles", roleList);
        return "newUser";
    }

    @GetMapping(value = "/users/edit")
    public String showEditUserForm(Model model, @RequestParam int id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        List<Role> roleList = roleDao.findAll();
        model.addAttribute("allRoles", roleList);
        return "editUser";
    }

    @PostMapping(value = "/users/create")
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(userService.passwordEncoder().encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        user.setPassword(userService.passwordEncoder().encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
