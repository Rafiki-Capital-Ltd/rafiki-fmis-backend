package ke.co.rafiki.fmis.dto.farmactivity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmActivityDto {
    private UUID id;
    private LocalDate date;
    private String activities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
