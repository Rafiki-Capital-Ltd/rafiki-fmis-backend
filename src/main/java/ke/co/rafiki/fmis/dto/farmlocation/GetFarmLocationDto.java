package ke.co.rafiki.fmis.dto.farmlocation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GetFarmLocationDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
