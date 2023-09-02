package ke.co.rafiki.fmis.dto.constituency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetConstituecyDto {
    private Integer id;
    private String name;
}
