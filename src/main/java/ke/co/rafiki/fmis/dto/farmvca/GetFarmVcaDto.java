package ke.co.rafiki.fmis.dto.farmvca;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmVcaDto {
    private UUID id;
    private String type;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
