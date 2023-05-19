package ke.co.rafiki.fmis.dto.farmanimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFarmAnimalDto {
    private String name;
    private String description;
    private Integer quantity;
}
