package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmConsumption;
import ke.co.rafiki.fmis.dto.farmconsumption.CreateFarmConsumptionDto;
import ke.co.rafiki.fmis.dto.farmconsumption.GetFarmConsumptionDto;
import ke.co.rafiki.fmis.dto.farmconsumption.UpdateFarmConsumptionDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmConsumptionMapper {
    GetFarmConsumptionDto toGetFarmConsumptionDto(FarmConsumption farmConsumption);

    FarmConsumption toFarmConsumption(CreateFarmConsumptionDto createFarmConsumptionDto);

    FarmConsumption toFarmConsumption(UpdateFarmConsumptionDto updateFarmConsumptionDto);
}
