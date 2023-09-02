package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.SubCounty;
import ke.co.rafiki.fmis.dto.subcounty.CreateSubCountyDto;
import ke.co.rafiki.fmis.dto.subcounty.GetSubCountyDto;
import ke.co.rafiki.fmis.dto.subcounty.UpdateSubCountyDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubCountyMapper {

    GetSubCountyDto toGetSubCountyDto(SubCounty subCounty);

    SubCounty toSubCounty(CreateSubCountyDto createSubCountyDto);

    SubCounty toSubCounty(UpdateSubCountyDto updateSubCountyDto);

}
