package ke.co.rafiki.fmis.dto.farmconsumption;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmConsumptionDto {
    private UUID id;
    private BigDecimal quantity;
    private LocalDate date;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
