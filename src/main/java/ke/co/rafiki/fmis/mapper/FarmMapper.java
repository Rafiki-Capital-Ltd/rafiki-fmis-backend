package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.Ward;
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
                .county(farm.getCounty().getName())
                .ward(farm.getWard().getName())
                .nearestShoppingCenter(farm.getNearestShoppingCenter())
                .location(farm.getLocation() != null ? GetFarmLocationDto.builder()
                        .latitude(farm.getLocation().getLat())
                        .longitude(farm.getLocation().getLng())
                        .build() : null)
                .createdAt(farm.getCreatedAt())
                .updatedAt(farm.getCreatedAt())
                .build();
    }

    default Farm toFarm(CreateFarmDto createFarmDto) {
        return Farm.builder()
                .name(createFarmDto.getName())
                .size(createFarmDto.getSize())
                .county(County.builder().name(createFarmDto.getCounty()).build())
                .ward(Ward.builder().name(createFarmDto.getWard()).build())
                .nearestShoppingCenter(createFarmDto.getNearestShoppingCenter())
                .location(createFarmDto.getLocation())
                .build();
    };

    default Farm toFarm(UpdateFarmDto updateFarmDto) {
        return Farm.builder()
                .name(updateFarmDto.getName())
                .size(updateFarmDto.getSize())
                .county(County.builder().name(updateFarmDto.getCounty()).build())
                .ward(Ward.builder().name(updateFarmDto.getWard()).build())
                .nearestShoppingCenter(updateFarmDto.getNearestShoppingCenter())
                .location(updateFarmDto.getLocation())
                .build();
    };
}
