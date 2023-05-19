package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmCrop;
import ke.co.rafiki.fmis.dto.farmcrop.CreateFarmCropDto;
import ke.co.rafiki.fmis.dto.farmcrop.GetFarmCropDto;
import ke.co.rafiki.fmis.dto.farmcrop.UpdateFarmCropDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmCropMapper {
    GetFarmCropDto toGetFarmCropDto(FarmCrop farmCrop);

    FarmCrop toFarmCrop(CreateFarmCropDto createFarmCropDto);

    FarmCrop toFarmCrop(UpdateFarmCropDto updateFarmCropDto);
}
