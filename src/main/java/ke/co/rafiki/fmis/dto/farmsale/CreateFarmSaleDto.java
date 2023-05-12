package ke.co.rafiki.fmis.dto.farmsale;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.enums.SaleType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateFarmSaleDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private SaleType type;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal amount;

    private String description;
}
