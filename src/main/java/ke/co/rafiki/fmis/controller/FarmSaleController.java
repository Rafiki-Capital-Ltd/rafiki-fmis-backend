package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import ke.co.rafiki.fmis.dto.farmsale.CreateFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.GetFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.UpdateFarmSaleDto;
import ke.co.rafiki.fmis.mapper.FarmSaleMapper;
import ke.co.rafiki.fmis.service.FarmSaleService;
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
@RequestMapping("farm-sales")
public class FarmSaleController {

    private final FarmSaleService farmSaleService;
    private final FarmSaleMapper farmSaleMapper;

    public FarmSaleController(FarmSaleService farmSaleService, FarmSaleMapper farmSaleMapper) {
        this.farmSaleService = farmSaleService;
        this.farmSaleMapper = farmSaleMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmSaleDto> createFarmSale(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmSaleDto createFarmSaleDto
    ) throws Exception {
        FarmSale farmSale = farmSaleService.save(farmSaleMapper.toFarmSale(createFarmSaleDto));
        GetFarmSaleDto getFarmSaleDto = farmSaleMapper.toGetFarmSaleDto(farmSale);
        URI location = new URI(request.getRequestURL() + "/" + farmSale.getId());
        return ResponseEntity.created(location).body(getFarmSaleDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmSaleDto>> findAllFarmSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<FarmSale> farmSales = farmSaleService.findAll(page, size, sort, sortDirection);
        List<GetFarmSaleDto> getFarmSaleDtos = farmSales.stream()
                .map(farmSaleMapper::toGetFarmSaleDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmSaleDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmSaleDto> findFarmSaleById(@PathVariable String id) throws Exception {
        FarmSale farmSale = farmSaleService.findOne(UUID.fromString(id));
        GetFarmSaleDto getFarmSaleDto = farmSaleMapper.toGetFarmSaleDto(farmSale);
        return ResponseEntity.ok(getFarmSaleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmSaleDto> updateFarmSale(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmSaleDto updateFarmSaleDto
    ) throws Exception {
        FarmSale farmSale = farmSaleMapper.toFarmSale(updateFarmSaleDto);
        GetFarmSaleDto getFarmSaleDto = farmSaleMapper
                .toGetFarmSaleDto(farmSaleService.update(UUID.fromString(id), farmSale));
        return ResponseEntity.ok(getFarmSaleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmSale(@PathVariable String id) {
        farmSaleService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmSaleService.getCount(farm));
        }
        return ResponseEntity.ok(farmSaleService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmSaleService.getTotal(farm).setScale(2, RoundingMode.HALF_UP));
        }
        return ResponseEntity.ok(farmSaleService.getTotal().setScale(2, RoundingMode.HALF_UP));
    }
}
