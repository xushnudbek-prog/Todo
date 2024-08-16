package project.spring.jdbc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO{
    @NotBlank(message = "Username can't be empty")
    String username;
    @NotBlank(message = "Password can't be empty")
    String password;
    @NotNull
    MultipartFile picture;
}
