package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import ke.co.rafiki.fmis.dto.farmconsumption.CreateFarmConsumptionDto;
import ke.co.rafiki.fmis.dto.farmconsumption.GetFarmConsumptionDto;
import ke.co.rafiki.fmis.dto.farmconsumption.UpdateFarmConsumptionDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmConsumptionMapper;
import ke.co.rafiki.fmis.service.FarmConsumptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.misc.Constants.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-consumptions")
public class FarmConsumptionController {

    private final FarmConsumptionService farmConsumptionService;
    private final FarmConsumptionMapper farmConsumptionMapper;

    public FarmConsumptionController(FarmConsumptionService farmConsumptionService, FarmConsumptionMapper farmConsumptionMapper) {
        this.farmConsumptionService = farmConsumptionService;
        this.farmConsumptionMapper = farmConsumptionMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmConsumptionDto> createFarmConsumption(
            HttpServletRequest request,
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId,
            @Valid @RequestBody CreateFarmConsumptionDto createFarmConsumptionDto
    ) throws Exception {
        if (farmId == null && createFarmConsumptionDto.getFarm() == null)
            throw new BadRequestException();

        if (createFarmConsumptionDto.getFarm() == null)
            createFarmConsumptionDto.setFarm(Farm.builder().id(farmId).build());

        FarmConsumption farmConsumption = farmConsumptionService.save(farmConsumptionMapper.toFarmConsumption(createFarmConsumptionDto));
        GetFarmConsumptionDto getFarmConsumptionDto = farmConsumptionMapper.toGetFarmConsumptionDto(farmConsumption);
        URI location = new URI(request.getRequestURL() + "/" + farmConsumption.getId());
        return ResponseEntity.created(location).body(getFarmConsumptionDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmConsumptionDto>> findAllFarmConsumptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<FarmConsumption> farmConsumptions = farmConsumptionService.findAll(page, size, sort, sortDirection);
        List<GetFarmConsumptionDto> getFarmConsumptionDtos = farmConsumptions.stream()
                .map(farmConsumptionMapper::toGetFarmConsumptionDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmConsumptionDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmConsumptionDto> findFarmConsumptionById(@PathVariable UUID id) throws Exception {
        FarmConsumption farmConsumption = farmConsumptionService.findOne(id);
        GetFarmConsumptionDto getFarmConsumptionDto = farmConsumptionMapper.toGetFarmConsumptionDto(farmConsumption);
        return ResponseEntity.ok(getFarmConsumptionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmConsumptionDto> updateFarmConsumption(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmConsumptionDto updateFarmConsumptionDto
    ) throws Exception {
        FarmConsumption farmConsumption = farmConsumptionMapper.toFarmConsumption(updateFarmConsumptionDto);
        GetFarmConsumptionDto getFarmConsumptionDto = farmConsumptionMapper.toGetFarmConsumptionDto(farmConsumptionService.update(id, farmConsumption));
        return ResponseEntity.ok(getFarmConsumptionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmConsumption(@PathVariable UUID id) {
        farmConsumptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmConsumptionService.getCount(farm));
        }
        return ResponseEntity.ok(farmConsumptionService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
            HttpServletRequest request,
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmConsumptionService.getTotal(farm));
        }
        return ResponseEntity.ok(farmConsumptionService.getTotal());
    }
}
