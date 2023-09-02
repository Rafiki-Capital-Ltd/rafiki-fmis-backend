package ke.co.rafiki.fmis.dto.farmpurchase;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmPurchaseDto {
    private UUID id;
    private LocalDate date;
    private String type;
    private BigDecimal quantity;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
