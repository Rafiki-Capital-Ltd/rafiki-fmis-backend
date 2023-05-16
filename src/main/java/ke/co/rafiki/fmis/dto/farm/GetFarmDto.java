package ke.co.rafiki.fmis.dto.farm;

import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.farmlocation.GetFarmLocationDto;
import ke.co.rafiki.fmis.dto.ward.GetWardDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetFarmDto {
    private UUID id;
    private String name;
    private BigDecimal size;
    private GetCountyDto county;
    private GetWardDto ward;
    private String nearestShoppingCenter;
    private GetFarmLocationDto location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
