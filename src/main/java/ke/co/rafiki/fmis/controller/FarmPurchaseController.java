package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmPurchase;
import ke.co.rafiki.fmis.dto.farmpurchase.CreateFarmPurchaseDto;
import ke.co.rafiki.fmis.dto.farmpurchase.GetFarmPurchaseDto;
import ke.co.rafiki.fmis.dto.farmpurchase.UpdateFarmPurchaseDto;
import ke.co.rafiki.fmis.mapper.FarmPurchaseMapper;
import ke.co.rafiki.fmis.service.FarmPurchaseService;
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
@RequestMapping("farm-purchases")
public class FarmPurchaseController {

    private final FarmPurchaseService farmPurchaseService;
    private final FarmPurchaseMapper farmPurchaseMapper;

    public FarmPurchaseController(FarmPurchaseService farmPurchaseService, FarmPurchaseMapper farmPurchaseMapper) {
        this.farmPurchaseService = farmPurchaseService;
        this.farmPurchaseMapper = farmPurchaseMapper;
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmPurchaseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection,
            @RequestParam(name = "farm", required = false) UUID farmId
    ) throws Exception {
        Page<FarmPurchase> farmPurchases;

        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            farmPurchases = farmPurchaseService.findAll(farm, page, size, sort, sortDirection);
        } else
            farmPurchases = farmPurchaseService.findAll(page, size, sort, sortDirection);

        List<GetFarmPurchaseDto> getFarmPurchaseDtos = farmPurchases.stream()
                .map(farmPurchaseMapper::toGetFarmPurchaseDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmPurchaseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmPurchaseDto> findOne(@PathVariable String id) throws Exception {
        FarmPurchase farmPurchase = farmPurchaseService.findOne(UUID.fromString(id));
        GetFarmPurchaseDto getFarmPurchaseDto = farmPurchaseMapper.toGetFarmPurchaseDto(farmPurchase);
        return ResponseEntity.ok(getFarmPurchaseDto);
    }

    @PostMapping
    public ResponseEntity<GetFarmPurchaseDto> create(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmPurchaseDto createFarmPurchaseDto
    ) throws Exception {
        FarmPurchase farmPurchase = farmPurchaseService.save(farmPurchaseMapper.toFarmPurchase(createFarmPurchaseDto));
        GetFarmPurchaseDto getFarmPurchaseDto = farmPurchaseMapper.toGetFarmPurchaseDto(farmPurchase);
        URI location = new URI(request.getRequestURL() + "/" + farmPurchase.getId());
        return ResponseEntity.created(location).body(getFarmPurchaseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmPurchaseDto> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmPurchaseDto updateFarmPurchaseDto
    ) throws Exception {
        FarmPurchase farmPurchase = farmPurchaseMapper.toFarmPurchase(updateFarmPurchaseDto);
        GetFarmPurchaseDto getFarmPurchaseDto = farmPurchaseMapper
                .toGetFarmPurchaseDto(farmPurchaseService.update(UUID.fromString(id), farmPurchase));
        return ResponseEntity.ok(getFarmPurchaseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmPurchase(@PathVariable String id) {
        farmPurchaseService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmPurchaseService.getCount(farm));
        }
        return ResponseEntity.ok(farmPurchaseService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmPurchaseService.getTotal(farm).setScale(2, RoundingMode.HALF_UP));
        }
        return ResponseEntity.ok(farmPurchaseService.getTotal().setScale(2, RoundingMode.HALF_UP));
    }
}
