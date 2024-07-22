package project.spring.jdbc.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {
    private int id;
    private String username;
    private String password;
    private boolean isBlocked = false;
    private List<AuthRole> roles;
    private int profilePictureId;
}
