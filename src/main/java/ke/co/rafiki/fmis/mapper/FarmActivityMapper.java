package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.dto.farmactivity.CreateFarmActivityDto;
import ke.co.rafiki.fmis.dto.farmactivity.GetFarmActivityDto;
import ke.co.rafiki.fmis.dto.farmactivity.UpdateFarmActivityDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmActivityMapper {
    GetFarmActivityDto toGetFarmActivityDto(FarmActivity farmActivity);

    FarmActivity toFarmActivity(CreateFarmActivityDto createFarmActivityDto);

    FarmActivity toFarmActivity(UpdateFarmActivityDto updateFarmActivityDto);

}
