package ke.co.rafiki.fmis.dto.subcounty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSubCountyDto {
    private Integer id;
    private String name;
}
