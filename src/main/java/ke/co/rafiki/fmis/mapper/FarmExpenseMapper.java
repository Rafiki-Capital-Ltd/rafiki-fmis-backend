package ke.co.rafiki.fmis.mapper;

import ke.co.rafiki.fmis.domain.FarmExpense;
import ke.co.rafiki.fmis.dto.farmexpense.CreateFarmExpenseDto;
import ke.co.rafiki.fmis.dto.farmexpense.GetFarmExpenseDto;
import ke.co.rafiki.fmis.dto.farmexpense.UpdateFarmExpenseDto;
import org.mapstruct.Mapper;

@Mapper
public interface FarmExpenseMapper {
    GetFarmExpenseDto toGetFarmExpenseDto(FarmExpense farmExpense);

    FarmExpense toFarmExpense(CreateFarmExpenseDto createFarmExpenseDto);

    FarmExpense toFarmExpense(UpdateFarmExpenseDto updateFarmExpenseDto);
}
