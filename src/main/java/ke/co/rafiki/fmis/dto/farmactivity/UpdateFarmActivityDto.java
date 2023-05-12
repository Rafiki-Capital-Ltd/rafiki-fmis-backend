package ke.co.rafiki.fmis.dto.farmactivity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateFarmActivityDto {
    @NotBlank
    private String activities;
}
