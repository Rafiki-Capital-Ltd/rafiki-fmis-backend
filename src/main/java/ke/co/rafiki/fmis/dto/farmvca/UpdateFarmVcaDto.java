package ke.co.rafiki.fmis.dto.farmvca;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFarmVcaDto {
    @NotNull
    private String type;


    private String description;
}
