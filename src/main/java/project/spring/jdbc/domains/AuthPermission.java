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
public class AuthPermission {
    private int id;
    private String name;
    private String code;
    private List<AuthPermission> permissions;
}
