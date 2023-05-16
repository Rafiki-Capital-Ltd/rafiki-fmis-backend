package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.farm.CreateFarmDto;
import ke.co.rafiki.fmis.dto.farm.GetFarmDto;
import ke.co.rafiki.fmis.dto.farm.UpdateFarmDto;
import ke.co.rafiki.fmis.dto.farmlocation.GetFarmLocationDto;
import ke.co.rafiki.fmis.dto.ward.GetWardDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmMapper {
    default GetFarmDto toGetFarmDto(Farm farm) {
        return GetFarmDto.builder()
                .id(farm.getId())
                .name(farm.getName())
                .size(farm.getSize())
                .county(GetCountyDto.builder()
                        .id(farm.getCounty().getId())
                        .name(farm.getCounty().getName())
                        .createdAt(farm.getCounty().getCreatedAt())
                        .updatedAt(farm.getCounty().getUpdatedAt())
                        .build())
                .ward(GetWardDto.builder()
                        .id(farm.getWard().getId())
                        .name(farm.getWard().getName())
                        .createdAt(farm.getWard().getCreatedAt())
                        .updatedAt(farm.getWard().getUpdatedAt())
                        .build())
                .nearestShoppingCenter(farm.getNearestShoppingCenter())
                .location(farm.getLocation() != null ? GetFarmLocationDto.builder()
                        .latitude(farm.getLocation().getLat())
                        .longitude(farm.getLocation().getLng())
                        .build() : null)
                .createdAt(farm.getCreatedAt())
                .updatedAt(farm.getCreatedAt())
                .build();
    }

    Farm toFarm(CreateFarmDto createFarmDto);

    default Farm toFarm(UpdateFarmDto updateFarmDto) {
        return Farm.builder()
                .name(updateFarmDto.getName())
                .size(updateFarmDto.getSize())
                .county(updateFarmDto.getCounty())
                .ward(updateFarmDto.getWard())
                .nearestShoppingCenter(updateFarmDto.getNearestShoppingCenter())
                .location(updateFarmDto.getLocation())
                .build();
    };
}
