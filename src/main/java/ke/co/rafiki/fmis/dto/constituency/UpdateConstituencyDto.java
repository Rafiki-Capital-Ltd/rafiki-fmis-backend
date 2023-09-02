package ke.co.rafiki.fmis.dto.constituency;

import jakarta.validation.constraints.NotBlank;
import ke.co.rafiki.fmis.domain.County;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateConstituencyDto {
    @NotBlank
    private String name;
    private County county;
}
