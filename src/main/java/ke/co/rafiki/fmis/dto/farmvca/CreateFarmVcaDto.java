package ke.co.rafiki.fmis.dto.farmvca;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.Data;

@Data
public class CreateFarmVcaDto {
    @NotNull
    private String type;

    private String description;

    private Farm farm;
}
