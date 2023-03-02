package be.technobel.materialloc.models.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthDTO {
    private String token, username, role;
}
