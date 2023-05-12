package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmProduction;
import ke.co.rafiki.fmis.dto.farmproduction.CreateFarmProductionDto;
import ke.co.rafiki.fmis.dto.farmproduction.GetFarmProductionDto;
import ke.co.rafiki.fmis.dto.farmproduction.UpdateFarmProductionDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmProductionMapper {
    GetFarmProductionDto toGetFarmProductionDto(FarmProduction farmProduction);

    FarmProduction toFarmProduction(CreateFarmProductionDto createFarmProductionDto);

    FarmProduction toFarmProduction(UpdateFarmProductionDto updateFarmProductionDto);
}
