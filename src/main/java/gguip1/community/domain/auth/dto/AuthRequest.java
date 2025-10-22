package gguip1.community.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
public record AuthRequest(
        @Email
        @NotBlank
        String email,
        String password) {
}

