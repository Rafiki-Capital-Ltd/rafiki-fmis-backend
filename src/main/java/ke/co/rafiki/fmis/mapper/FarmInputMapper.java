package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmInput;
import ke.co.rafiki.fmis.dto.farminput.CreateFarmInputDto;
import ke.co.rafiki.fmis.dto.farminput.GetFarmInputDto;
import ke.co.rafiki.fmis.dto.farminput.UpdateFarmInputDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmInputMapper {
    GetFarmInputDto toGetFarmInputDto(FarmInput farmInput);

    FarmInput toFarmInput(CreateFarmInputDto createFarmInputDto);

    FarmInput toFarmInput(UpdateFarmInputDto updateFarmInputDto);
}
