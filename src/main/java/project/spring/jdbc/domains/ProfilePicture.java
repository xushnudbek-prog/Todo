package project.spring.jdbc.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePicture {
    private int id;
    private String originalName;
    private String generatedName;
    private String mimeType;
}
