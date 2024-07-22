package project.spring.jdbc.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TODO {
    private int id;
    private String title;
    private String priority;
    private LocalDateTime createdAt;
    private int createdBy;
}
