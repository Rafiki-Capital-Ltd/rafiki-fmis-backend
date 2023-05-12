package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmVca;
import ke.co.rafiki.fmis.dto.farmvca.CreateFarmVcaDto;
import ke.co.rafiki.fmis.dto.farmvca.GetFarmVcaDto;
import ke.co.rafiki.fmis.dto.farmvca.UpdateFarmVcaDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmVcaMapper {
    GetFarmVcaDto toGetFarmVcaDto(FarmVca farmVca);

    FarmVca toFarmVca(CreateFarmVcaDto createFarmVcaDto);

    FarmVca toFarmVca(UpdateFarmVcaDto updateFarmVcaDto);
}
