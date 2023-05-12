package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmAsset;
import ke.co.rafiki.fmis.dto.farmasset.CreateFarmAssetDto;
import ke.co.rafiki.fmis.dto.farmasset.GetFarmAssetDto;
import ke.co.rafiki.fmis.dto.farmasset.UpdateFarmAssetDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmAssetMapper {
    GetFarmAssetDto toGetFarmAssetDto(FarmAsset farmAsset);
    
    FarmAsset toFarmAsset(CreateFarmAssetDto createFarmAssetDto);
    
    FarmAsset toFarmAsset(UpdateFarmAssetDto updateFarmAssetDto);
}
