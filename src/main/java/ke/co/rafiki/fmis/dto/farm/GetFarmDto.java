package ke.co.rafiki.fmis.dto.farm;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.SubCounty;
import ke.co.rafiki.fmis.dto.constituency.GetConstituecyDto;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.farmlocation.GetFarmLocationDto;
import ke.co.rafiki.fmis.dto.subcounty.GetSubCountyDto;
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
    private GetConstituecyDto constituency;
    private GetSubCountyDto subCounty;
    private GetFarmLocationDto location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
