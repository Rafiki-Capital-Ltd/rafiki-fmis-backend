package ke.co.rafiki.fmis.dto.county;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCountyDto {
    @NotNull
    @NotBlank
    private String name;
}
