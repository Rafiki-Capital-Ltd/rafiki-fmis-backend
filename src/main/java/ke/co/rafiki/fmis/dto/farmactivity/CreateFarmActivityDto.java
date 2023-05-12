package ke.co.rafiki.fmis.dto.farmactivity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateFarmActivityDto {
    @NotNull
    private LocalDate date;

    @NotBlank
    private String activities;

}
