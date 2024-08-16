package project.spring.jdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.spring.jdbc.config.security.AuthUserDetails;
import project.spring.jdbc.config.security.SessionUser;
import project.spring.jdbc.dao.TODODao;
import project.spring.jdbc.domains.AuthUser;
import project.spring.jdbc.domains.TODO;
import project.spring.jdbc.exceptions.NotFoundException;
import project.spring.jdbc.services.ProfilePictureService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Controller
public class ToDoController {

    private static final AtomicInteger counter = new AtomicInteger();
    private final TODODao todoDao;
    private final SessionUser sessionUser;
    private final ProfilePictureService profilePictureService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<TODO> todos = todoDao.getList(sessionUser.getUser().getId());
        modelAndView.addObject("todos", todos);
        AuthUser authUser = sessionUser.getUser();

        String profilePictureUrl = profilePictureService.getProfilePictureUrl(authUser.getProfilePictureId());

        modelAndView.addObject("profilePictureUrl", profilePictureUrl);
        return modelAndView;
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String addPost(@ModelAttribute TODO todo) {
        todo.setId(counter.incrementAndGet());
        todo.setCreatedAt(LocalDateTime.now());
        int id = sessionUser.getUser().getId();
        todo.setCreatedBy(id);
        todoDao.save(todo);
        return "redirect:/";
    }


    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String add(Model model) {
        model.addAttribute("todo", new TODO());
        return "add";
    }

    @GetMapping("/todo/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String delete(@PathVariable String id) {
        TODO currentTodo = todoDao.getById(Integer.parseInt(id));
        if (currentTodo == null) {
            throw new NotFoundException("TODO with id %s not found".formatted(id), "/");
        }
        if (currentTodo.getCreatedBy() != sessionUser.getUser().getId())
            return "error/403";
        todoDao.delete(Integer.parseInt(id));
        return "redirect:/";
    }

    @GetMapping("/todo/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String updateViewShow(Model model, @PathVariable String id) {
        TODO currentTodo = todoDao.getById(Integer.parseInt(id));
        if (currentTodo.getCreatedBy() != sessionUser.getUser().getId())
            return "error/403";
        model.addAttribute("todo", currentTodo);
        return "update";
    }

    @PostMapping("/todo/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String update(@PathVariable String id, @RequestParam String title, @RequestParam String priority) {
        TODO todo = TODO.builder()
                .id(Integer.parseInt(id))
                .title(title)
                .priority(priority)
                .build();
        todoDao.update(todo);
        return "redirect:/";
    }


}
