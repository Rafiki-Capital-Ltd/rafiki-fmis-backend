package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.dto.farmactivitylog.CreateFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.GetFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.UpdateFarmActivityLogDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmActivityLogMapper {
    GetFarmActivityLogDto toGetFarmActivityLogDto(FarmActivityLog farmActivityLog);

    FarmActivityLog toFarmActivityLog(CreateFarmActivityLogDto createFarmActivityLogDto);

    FarmActivityLog toFarmActivityLog(UpdateFarmActivityLogDto updateFarmActivityLogDto);
}
