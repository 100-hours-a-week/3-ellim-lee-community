package gguip1.community.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthRequest {
    @Email
    @NotBlank
    private String email;
    private String password;
}
