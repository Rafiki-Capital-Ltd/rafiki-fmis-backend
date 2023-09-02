package ke.co.rafiki.fmis.dto.county;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCountyDto {
    @NotBlank
    private String name;
}
