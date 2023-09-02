package ke.co.rafiki.fmis.dto.farm;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.FarmLocation;
import ke.co.rafiki.fmis.domain.SubCounty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateFarmDto {
    private String name;

    @NotNull
    private BigDecimal size;

    private County county;

    private Constituency constituency;

    private SubCounty subCounty;

    private FarmLocation location;
}
