package ke.co.rafiki.fmis.dto.farmasset;

import lombok.Data;

@Data
public class UpdateFarmAssetDto {
    private String type;
    private String description;
    private String storageLocation;
}
