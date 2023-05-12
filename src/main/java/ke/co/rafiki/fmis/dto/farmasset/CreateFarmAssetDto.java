package ke.co.rafiki.fmis.dto.farmasset;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import lombok.Data;

@Data
public class CreateFarmAssetDto {
    @NotNull
    private String type;

    private String description;

    private String storageLocation;

    private AssetStatus status;
}
