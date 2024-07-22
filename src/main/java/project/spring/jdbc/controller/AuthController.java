package project.spring.jdbc.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.spring.jdbc.dao.AuthUserDao;
import project.spring.jdbc.dao.ProfilePictureDao;
import project.spring.jdbc.domains.AuthRole;
import project.spring.jdbc.domains.AuthUser;
import project.spring.jdbc.domains.ProfilePicture;
import project.spring.jdbc.dto.UserRegisterDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final ProfilePictureDao profilePictureDao;


    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMessage ", error);
        return modelAndView;

    }
    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPage(@ModelAttribute UserRegisterDTO dto) throws IOException {
        MultipartFile multipartFile = dto.picture();
        String generatedName = String.valueOf(UUID.randomUUID());
        ProfilePicture profilePicture = ProfilePicture.builder()
                .originalName(multipartFile.getOriginalFilename())
                .generatedName(generatedName)
                .mimeType(multipartFile.getContentType())
                .build();
        int id = profilePictureDao.save(profilePicture);
        AuthUser user = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .roles(List.of(new AuthRole()))
                .profilePictureId(id)
                .build();
        authUserDao.save(user);
        Path path = Path.of("/home/xushnudbek/Desktop/uploads/" + generatedName + "." +StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "redirect:/auth/login";
    }
}
