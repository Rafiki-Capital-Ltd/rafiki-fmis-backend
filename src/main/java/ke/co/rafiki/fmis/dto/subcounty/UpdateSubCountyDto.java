package ke.co.rafiki.fmis.dto.subcounty;

import jakarta.validation.constraints.NotBlank;
import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSubCountyDto {
    @NotBlank
    private String name;
    private County county;
    private Constituency constituency;
}
