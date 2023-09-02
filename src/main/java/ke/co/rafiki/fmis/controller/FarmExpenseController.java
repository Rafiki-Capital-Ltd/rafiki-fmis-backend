package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmExpense;
import ke.co.rafiki.fmis.dto.farmexpense.CreateFarmExpenseDto;
import ke.co.rafiki.fmis.dto.farmexpense.GetFarmExpenseDto;
import ke.co.rafiki.fmis.dto.farmexpense.UpdateFarmExpenseDto;
import ke.co.rafiki.fmis.mapper.FarmExpenseMapper;
import ke.co.rafiki.fmis.service.FarmExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.UUID;


@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-expenses")
public class FarmExpenseController {

    private final FarmExpenseService farmExpenseService;
    private final FarmExpenseMapper farmExpenseMapper;

    public FarmExpenseController(FarmExpenseService farmExpenseService, FarmExpenseMapper farmExpenseMapper) {
        this.farmExpenseService = farmExpenseService;
        this.farmExpenseMapper = farmExpenseMapper;
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmExpenseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection,
            @RequestParam(name = "farm", required = false) UUID farmId
    ) throws Exception {
        Page<FarmExpense> farmExpenses;

        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            farmExpenses = farmExpenseService.findAll(farm, page, size, sort, sortDirection);
        } else
            farmExpenses = farmExpenseService.findAll(page, size, sort, sortDirection);

        List<GetFarmExpenseDto> getFarmExpenseDtos = farmExpenses.stream()
                .map(farmExpenseMapper::toGetFarmExpenseDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmExpenseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmExpenseDto> findOne(@PathVariable String id) throws Exception {
        FarmExpense farmExpense = farmExpenseService.findOne(UUID.fromString(id));
        GetFarmExpenseDto getFarmExpenseDto = farmExpenseMapper.toGetFarmExpenseDto(farmExpense);
        return ResponseEntity.ok(getFarmExpenseDto);
    }

    @PostMapping
    public ResponseEntity<GetFarmExpenseDto> create(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmExpenseDto createFarmExpenseDto
    ) throws Exception {
        FarmExpense farmExpense = farmExpenseService.save(farmExpenseMapper.toFarmExpense(createFarmExpenseDto));
        GetFarmExpenseDto getFarmExpenseDto = farmExpenseMapper.toGetFarmExpenseDto(farmExpense);
        URI location = new URI(request.getRequestURL() + "/" + farmExpense.getId());
        return ResponseEntity.created(location).body(getFarmExpenseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmExpenseDto> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmExpenseDto updateFarmExpenseDto
    ) throws Exception {
        FarmExpense farmExpense = farmExpenseMapper.toFarmExpense(updateFarmExpenseDto);
        GetFarmExpenseDto getFarmExpenseDto = farmExpenseMapper
                .toGetFarmExpenseDto(farmExpenseService.update(UUID.fromString(id), farmExpense));
        return ResponseEntity.ok(getFarmExpenseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmExpense(@PathVariable String id) {
        farmExpenseService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmExpenseService.getCount(farm));
        }
        return ResponseEntity.ok(farmExpenseService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmExpenseService.getTotal(farm).setScale(2, RoundingMode.HALF_UP));
        }
        return ResponseEntity.ok(farmExpenseService.getTotal().setScale(2, RoundingMode.HALF_UP));
    }
}
