package project.spring.jdbc.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRegisterDTO(String username, String password, MultipartFile picture) {

}
