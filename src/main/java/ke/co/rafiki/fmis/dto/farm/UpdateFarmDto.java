package ke.co.rafiki.fmis.dto.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.FarmLocation;
import ke.co.rafiki.fmis.domain.Ward;
import lombok.Data;

import javax.xml.stream.Location;
import java.math.BigDecimal;

@Data
public class UpdateFarmDto {
    @NotNull
    private BigDecimal size;

    @NotBlank
    private County county;

    @NotBlank
    private Ward ward;

    private String nearestShoppingCenter;

    @NotNull
    private FarmLocation location;
}
