package ke.co.rafiki.fmis.dto.subcounty;

import jakarta.validation.constraints.NotBlank;
import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CreateSubCountyDto {
    @NonNull
    @NotBlank
    private String name;

    @NonNull
    private County county;

    @NonNull
    private Constituency constituency;
}
