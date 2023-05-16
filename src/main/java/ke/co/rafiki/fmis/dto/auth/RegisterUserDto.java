package ke.co.rafiki.fmis.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ke.co.rafiki.fmis.domain.PhoneNumber;
import ke.co.rafiki.fmis.domain.Role;

import java.util.Set;

public class RegisterUserDto {
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

    private Set<PhoneNumber> phoneNumbers;

    private String gender;

    private Set<Role> roles;
}
