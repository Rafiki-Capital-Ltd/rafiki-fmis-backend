package ke.co.rafiki.fmis.dto.farmcrop;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFarmCropDto {
    private String name;
    private String description;
    private Integer quantity;
}
