package ke.co.rafiki.fmis.dto.farmasset;

import ke.co.rafiki.fmis.domain.enums.AssetStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmAssetDto {
    private UUID id;
    private String type;
    private String description;
    private String storageLocation;
    private AssetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
