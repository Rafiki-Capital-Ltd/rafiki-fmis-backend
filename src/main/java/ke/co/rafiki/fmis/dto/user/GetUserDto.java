package ke.co.rafiki.fmis.dto.user;

import ke.co.rafiki.fmis.domain.PhoneNumber;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class GetUserDto {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private List<PhoneNumber> phoneNumbers;
    private String gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
