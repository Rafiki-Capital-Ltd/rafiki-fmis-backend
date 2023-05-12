package ke.co.rafiki.fmis.dto.farmproduction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmProductionDto {
    private UUID id;
    private LocalDate date;
    private BigDecimal quantity;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
