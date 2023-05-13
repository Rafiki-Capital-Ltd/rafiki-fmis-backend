package ke.co.rafiki.fmis.dto.farmactivitylog;

import jakarta.validation.constraints.NotNull;
import ke.co.rafiki.fmis.domain.Farm;
import lombok.Data;

import java.time.Year;

@Data
public class CreateFarmActivityLogDto {
    @NotNull
    private Year year;

    @NotNull
    private String name;

    private Farm farm;
}
