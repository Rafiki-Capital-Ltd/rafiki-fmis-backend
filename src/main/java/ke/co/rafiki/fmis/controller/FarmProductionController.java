package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmProduction;
import ke.co.rafiki.fmis.dto.farmproduction.CreateFarmProductionDto;
import ke.co.rafiki.fmis.dto.farmproduction.GetFarmProductionDto;
import ke.co.rafiki.fmis.dto.farmproduction.UpdateFarmProductionDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmProductionMapper;
import ke.co.rafiki.fmis.service.FarmProductionService;
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
@RequestMapping("farm-productions")
public class FarmProductionController {

    private final FarmProductionService farmProductionService;
    private final FarmProductionMapper farmProductionMapper;

    public FarmProductionController(FarmProductionService farmProductionService, FarmProductionMapper farmProductionMapper) {
        this.farmProductionService = farmProductionService;
        this.farmProductionMapper = farmProductionMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmProductionDto> createFarmProduction(
            HttpServletRequest request,
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId,
            @Valid @RequestBody CreateFarmProductionDto createFarmProductionDto
    ) throws Exception {
        if (farmId == null && createFarmProductionDto.getFarm() == null)
            throw new BadRequestException();

        if (createFarmProductionDto.getFarm() == null)
            createFarmProductionDto.setFarm(Farm.builder().id(farmId).build());

        FarmProduction farmProduction = farmProductionService.save(farmProductionMapper.toFarmProduction(createFarmProductionDto));
        GetFarmProductionDto getFarmProductionDto = farmProductionMapper.toGetFarmProductionDto(farmProduction);
        URI location = new URI(request.getRequestURL() + "/" + farmProduction.getId());
        return ResponseEntity.created(location).body(getFarmProductionDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmProductionDto>> findAllFarmProductions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<FarmProduction> farmProductions = farmProductionService.findAll(page, size, sort, sortDirection);
        List<GetFarmProductionDto> getFarmProductionDtos = farmProductions.stream()
                .map(farmProductionMapper::toGetFarmProductionDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmProductionDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmProductionDto> findFarmProductionById(@PathVariable UUID id) throws Exception {
        FarmProduction farmProduction = farmProductionService.findOne(id);
        GetFarmProductionDto getFarmProductionDto = farmProductionMapper.toGetFarmProductionDto(farmProduction);
        return ResponseEntity.ok(getFarmProductionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmProductionDto> updateFarmProduction(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmProductionDto updateFarmProductionDto
    ) throws Exception {
        FarmProduction farmProduction = farmProductionMapper.toFarmProduction(updateFarmProductionDto);
        GetFarmProductionDto getFarmProductionDto = farmProductionMapper.toGetFarmProductionDto(farmProductionService.update(id, farmProduction));
        return ResponseEntity.ok(getFarmProductionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFarmProduction(@PathVariable UUID id) {
        farmProductionService.delete(id);
    }
}
