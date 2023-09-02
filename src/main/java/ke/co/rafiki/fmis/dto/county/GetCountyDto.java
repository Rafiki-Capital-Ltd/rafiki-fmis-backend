package ke.co.rafiki.fmis.dto.county;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetCountyDto {
    private Integer id;
    private String name;
}
