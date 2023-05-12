package ke.co.rafiki.fmis.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ke.co.rafiki.fmis.domain.PhoneNumber;
import ke.co.rafiki.fmis.domain.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateUserDto {
    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotEmpty
    private Set<PhoneNumber> phoneNumbers;

    @NotEmpty
    private Set<Role> roles;
}
