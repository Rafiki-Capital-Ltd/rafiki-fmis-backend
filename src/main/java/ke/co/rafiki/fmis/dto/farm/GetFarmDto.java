package ke.co.rafiki.fmis.dto.farm;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.Ward;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetFarmDto {
    private UUID id;
    private BigDecimal size;
    private County county;
    private Ward ward;
    private String nearestShoppingCenter;
    private LocationDto location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
