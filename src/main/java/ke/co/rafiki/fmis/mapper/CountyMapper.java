package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import org.mapstruct.Mapper;

@Mapper
public interface CountyMapper {

    GetCountyDto toGetCountyDto(County county);


}
