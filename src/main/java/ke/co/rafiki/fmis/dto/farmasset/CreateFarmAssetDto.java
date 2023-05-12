package ke.co.rafiki.fmis.dto.farmasset;

import lombok.Data;

@Data
public class CreateFarmAssetDto {
    private String type;
    private String description;
    private String storageLocation;
}
