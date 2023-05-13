package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import ke.co.rafiki.fmis.dto.farmsale.CreateFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.GetFarmSaleDto;
import ke.co.rafiki.fmis.dto.farmsale.UpdateFarmSaleDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmSaleMapper;
import ke.co.rafiki.fmis.service.FarmSaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.Constants.*;


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
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId,
            @Valid @RequestBody CreateFarmSaleDto createFarmSaleDto
    ) throws Exception {
        if (farmId == null && createFarmSaleDto.getFarm() == null)
            throw new BadRequestException();

        if (createFarmSaleDto.getFarm() == null)
            createFarmSaleDto.setFarm(Farm.builder().id(farmId).build());

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
    ) {
        Page<FarmSale> farmSales = farmSaleService.findAll(page, size, sort, sortDirection);
        List<GetFarmSaleDto> getFarmSaleDtos = farmSales.stream()
                .map(farmSaleMapper::toGetFarmSaleDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmSaleDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmSaleDto> findFarmSaleById(@PathVariable UUID id) throws Exception {
        FarmSale farmSale = farmSaleService.findOne(id);
        GetFarmSaleDto getFarmSaleDto = farmSaleMapper.toGetFarmSaleDto(farmSale);
        return ResponseEntity.ok(getFarmSaleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmSaleDto> updateFarmSale(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmSaleDto updateFarmSaleDto
    ) throws Exception {
        FarmSale farmSale = farmSaleMapper.toFarmSale(updateFarmSaleDto);
        GetFarmSaleDto getFarmSaleDto = farmSaleMapper.toGetFarmSaleDto(farmSaleService.update(id, farmSale));
        return ResponseEntity.ok(getFarmSaleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFarmSale(@PathVariable UUID id) {
        farmSaleService.delete(id);
    }
}
