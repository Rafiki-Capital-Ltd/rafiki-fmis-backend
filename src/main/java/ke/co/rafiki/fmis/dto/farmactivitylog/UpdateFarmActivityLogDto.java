package ke.co.rafiki.fmis.dto.farmactivitylog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Year;

@Data
public class UpdateFarmActivityLogDto {
    @NotNull
    private Year year;

    @NotNull
    private String name;
}
