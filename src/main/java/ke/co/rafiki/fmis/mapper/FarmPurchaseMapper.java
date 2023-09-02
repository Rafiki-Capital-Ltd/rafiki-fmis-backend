package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmPurchase;
import ke.co.rafiki.fmis.dto.farmpurchase.CreateFarmPurchaseDto;
import ke.co.rafiki.fmis.dto.farmpurchase.GetFarmPurchaseDto;
import ke.co.rafiki.fmis.dto.farmpurchase.UpdateFarmPurchaseDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmPurchaseMapper {
    GetFarmPurchaseDto toGetFarmPurchaseDto(FarmPurchase farmPurchase);

    FarmPurchase toFarmPurchase(CreateFarmPurchaseDto createFarmPurchaseDto);

    FarmPurchase toFarmPurchase(UpdateFarmPurchaseDto updateFarmPurchaseDto);
}
