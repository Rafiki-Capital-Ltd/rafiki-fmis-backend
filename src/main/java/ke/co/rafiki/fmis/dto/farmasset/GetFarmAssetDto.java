package ke.co.rafiki.fmis.dto.farmasset;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmAssetDto {
    private UUID id;
    private String type;
    private String description;
    private String storageLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
