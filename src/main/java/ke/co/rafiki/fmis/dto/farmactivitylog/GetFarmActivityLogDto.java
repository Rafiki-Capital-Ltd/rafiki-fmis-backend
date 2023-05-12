package ke.co.rafiki.fmis.dto.farmactivitylog;

import lombok.Data;

import javax.lang.model.element.Name;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

@Data
public class GetFarmActivityLogDto {
    private UUID id;
    private Year year;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
