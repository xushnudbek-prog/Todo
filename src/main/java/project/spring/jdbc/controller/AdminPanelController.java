package project.spring.jdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.spring.jdbc.dao.AuthUserDao;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPanelController {

    private final AuthUserDao authUserDao;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    public String users(Model model) {
        model.addAttribute(authUserDao.getAllSimpleUsers());
        return "admin/users_list";
    }

    @GetMapping("/block/{id}")
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    public String blockUser(@PathVariable String id) {
        authUserDao.blockUser(Integer.parseInt(id));
        return "redirect:/admin/users";
    }
    @GetMapping("/unblock/{id}")
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    public String unblockUser(@PathVariable String id) {
        authUserDao.unblockUser(Integer.parseInt(id));
        return "redirect:/admin/users";
    }


}
