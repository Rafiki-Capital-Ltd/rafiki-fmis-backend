package ke.co.rafiki.fmis.dto.farm;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {
    private BigDecimal latitiude;
    private BigDecimal longitude;
}
