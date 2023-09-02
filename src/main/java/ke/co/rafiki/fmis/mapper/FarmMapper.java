package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.dto.constituency.GetConstituecyDto;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.farm.CreateFarmDto;
import ke.co.rafiki.fmis.dto.farm.GetFarmDto;
import ke.co.rafiki.fmis.dto.farm.UpdateFarmDto;
import ke.co.rafiki.fmis.dto.farmlocation.GetFarmLocationDto;
import ke.co.rafiki.fmis.dto.subcounty.GetSubCountyDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmMapper {
    default GetFarmDto toGetFarmDto(Farm farm) {
        return GetFarmDto.builder()
                .id(farm.getId())
                .name(farm.getName())
                .size(farm.getSize())
                .county(GetCountyDto
                        .builder()
                        .id(farm.getCounty().getId())
                        .name(farm.getCounty().getName())
                        .build())
                .constituency(GetConstituecyDto
                        .builder()
                        .id(farm.getConstituency().getId())
                        .name(farm.getConstituency().getName())
                        .build())
                .subCounty(GetSubCountyDto
                        .builder()
                        .id(farm.getSubCounty().getId())
                        .name(farm.getSubCounty().getName())
                        .build())
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
                .county(createFarmDto.getCounty())
                .constituency(createFarmDto.getConstituency())
                .subCounty(createFarmDto.getSubCounty())
                .location(createFarmDto.getLocation())
                .build();
    }

    default Farm toFarm(UpdateFarmDto updateFarmDto) {
        return Farm.builder()
                .name(updateFarmDto.getName())
                .size(updateFarmDto.getSize())
                .county(updateFarmDto.getCounty())
                .constituency(updateFarmDto.getConstituency())
                .subCounty(updateFarmDto.getSubCounty())
                .location(updateFarmDto.getLocation())
                .build();
    }
}
