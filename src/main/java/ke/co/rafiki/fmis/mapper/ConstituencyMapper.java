package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.dto.constituency.CreateConstituencyDto;
import ke.co.rafiki.fmis.dto.constituency.GetConstituecyDto;
import ke.co.rafiki.fmis.dto.constituency.UpdateConstituencyDto;
import org.mapstruct.Mapper;

@Mapper
public interface ConstituencyMapper {

    GetConstituecyDto toGetConstituencyDto(Constituency constituency);

    Constituency toConstituency(CreateConstituencyDto createConstituencyDto);

    Constituency toConstituency(UpdateConstituencyDto updateConstituencyDto);

}
