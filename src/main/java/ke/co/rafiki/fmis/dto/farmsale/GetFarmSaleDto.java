package ke.co.rafiki.fmis.dto.farmsale;

import ke.co.rafiki.fmis.domain.SaleType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmSaleDto {
    private UUID id;
    private LocalDate date;
    private SaleType type;
    private BigDecimal quantity;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
