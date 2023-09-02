package ke.co.rafiki.fmis.dto.farminput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFarmInputDto {
    @NotBlank
    private String type;

    @NotBlank
    private String description;

    @NotNull
    private Integer quantity;

    private Farm farm;
}
