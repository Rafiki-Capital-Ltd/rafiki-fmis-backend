package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.dto.farm.CreateFarmDto;
import ke.co.rafiki.fmis.dto.farm.GetFarmDto;
import ke.co.rafiki.fmis.dto.farm.UpdateFarmDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmMapper {
    GetFarmDto toGetFarmDto(Farm farm);

    Farm toFarm(CreateFarmDto createFarmDto);

    Farm toFarm(UpdateFarmDto updateFarmDto);
}
