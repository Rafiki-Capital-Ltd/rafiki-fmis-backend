package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmSale;
import ke.co.rafiki.fmis.dto.farmsale.CreateFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.GetFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.UpdateFarmSaleDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmSaleMapper {
    GetFarmSaleDto toGetFarmSaleDto(FarmSale farmSale);

    FarmSale toFarmSale(CreateFarmSaleDto createFarmSaleDto);

    FarmSale toFarmSale(UpdateFarmSaleDto updateFarmSaleDto);
}
