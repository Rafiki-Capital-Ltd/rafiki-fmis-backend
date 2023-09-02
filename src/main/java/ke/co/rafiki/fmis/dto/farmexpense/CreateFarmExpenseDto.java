package ke.co.rafiki.fmis.dto.farmexpense;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreateFarmExpenseDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;

    private String description;

    private Farm farm;
}
