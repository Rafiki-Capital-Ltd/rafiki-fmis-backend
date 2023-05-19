package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmAnimal;
import ke.co.rafiki.fmis.dto.farmanimal.CreateFarmAnimalDto;
import ke.co.rafiki.fmis.dto.farmanimal.GetFarmAnimalDto;
import ke.co.rafiki.fmis.dto.farmanimal.UpdateFarmAnimalDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmAnimalMapper {
    GetFarmAnimalDto toGetFarmAnimalDto(FarmAnimal farmAnimal);

    FarmAnimal toFarmAnimal(CreateFarmAnimalDto createFarmAnimalDto);

    FarmAnimal toFarmAnimal(UpdateFarmAnimalDto updateFarmAnimalDto);
}
