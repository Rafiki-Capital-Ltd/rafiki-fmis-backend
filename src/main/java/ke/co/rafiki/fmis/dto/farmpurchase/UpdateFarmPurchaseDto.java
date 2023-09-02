package ke.co.rafiki.fmis.dto.farmpurchase;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateFarmPurchaseDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal amount;

    private String description;
}
