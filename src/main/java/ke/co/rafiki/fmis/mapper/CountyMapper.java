package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.dto.county.CreateCountyDto;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.county.UpdateCountyDto;
import org.mapstruct.Mapper;

@Mapper
public interface CountyMapper {

    GetCountyDto toGetCountyDto(County county);

    County toCounty(CreateCountyDto createCountyDto);

    County toCounty(UpdateCountyDto updateCountyDto);

}
