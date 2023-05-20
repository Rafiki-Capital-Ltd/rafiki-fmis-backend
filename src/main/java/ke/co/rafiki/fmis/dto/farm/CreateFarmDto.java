package ke.co.rafiki.fmis.dto.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.FarmLocation;
import ke.co.rafiki.fmis.domain.Ward;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateFarmDto {
    @NotNull
    private String name;

    @NotNull
    private BigDecimal size;

    @NotNull
    private County county;

    @NotNull
    private Ward ward;

    private String nearestShoppingCenter;

    private FarmLocation location;
}
