package ke.co.rafiki.fmis.dto.farmproduction;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateFarmProductionDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal quantity;

    private String description;
}
