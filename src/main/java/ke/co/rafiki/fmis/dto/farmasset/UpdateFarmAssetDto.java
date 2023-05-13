package ke.co.rafiki.fmis.dto.farmasset;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import lombok.Data;

@Data
public class UpdateFarmAssetDto {
    @NotNull
    private String type;

    private String description;

    private String storageLocation;

    private String status;
}
