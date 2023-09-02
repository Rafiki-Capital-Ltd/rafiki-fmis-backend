package ke.co.rafiki.fmis.dto.farmexpense;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmExpenseDto {
    private UUID id;
    private LocalDate date;
    private String type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
