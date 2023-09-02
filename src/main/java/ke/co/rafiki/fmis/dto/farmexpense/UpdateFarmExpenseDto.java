package ke.co.rafiki.fmis.dto.farmexpense;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateFarmExpenseDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;

    private String description;
}
