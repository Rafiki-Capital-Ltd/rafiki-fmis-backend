package ke.co.rafiki.fmis.dto.farmanimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFarmAnimalDto {
    @NotBlank
    private String type;

    @NotBlank
    private String description;

    @NotNull
    private Integer quantity;
}
