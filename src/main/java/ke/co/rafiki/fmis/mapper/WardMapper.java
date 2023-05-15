package ke.co.rafiki.fmis.mapper;


import ke.co.rafiki.fmis.domain.Ward;
import ke.co.rafiki.fmis.dto.ward.GetWardDto;
import org.mapstruct.Mapper;

@Mapper
public interface WardMapper {

    GetWardDto toGetWardDto(Ward ward);

}
