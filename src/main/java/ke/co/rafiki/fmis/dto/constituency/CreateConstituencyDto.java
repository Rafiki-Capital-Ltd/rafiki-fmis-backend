package ke.co.rafiki.fmis.dto.constituency;

import jakarta.validation.constraints.NotBlank;
import ke.co.rafiki.fmis.domain.County;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CreateConstituencyDto {
    @NonNull
    @NotBlank
    private String name;

    @NonNull
    private County county;
}
