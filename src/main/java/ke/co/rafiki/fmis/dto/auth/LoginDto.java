package ke.co.rafiki.fmis.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
