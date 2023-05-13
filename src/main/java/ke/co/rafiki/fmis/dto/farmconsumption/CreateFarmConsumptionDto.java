package ke.co.rafiki.fmis.dto.farmconsumption;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateFarmConsumptionDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private BigDecimal quantity;

    private String description;

    private Farm farm;
}
