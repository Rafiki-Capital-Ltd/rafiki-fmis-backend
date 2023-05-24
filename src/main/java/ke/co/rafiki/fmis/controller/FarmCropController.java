package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmCrop;
import ke.co.rafiki.fmis.dto.farmcrop.CreateFarmCropDto;
import ke.co.rafiki.fmis.dto.farmcrop.GetFarmCropDto;
import ke.co.rafiki.fmis.dto.farmcrop.UpdateFarmCropDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmCropMapper;
import ke.co.rafiki.fmis.service.FarmCropService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-crops")
public class FarmCropController {

    private final FarmCropService farmCropService;
    private final FarmCropMapper farmCropMapper;

    public FarmCropController(FarmCropService farmCropService, FarmCropMapper farmCropMapper) {
        this.farmCropService = farmCropService;
        this.farmCropMapper = farmCropMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmCropDto> createFarmCrop(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmCropDto createFarmCropDto
    ) throws Exception {
        FarmCrop farmCrop = farmCropService.save(farmCropMapper.toFarmCrop(createFarmCropDto));
        GetFarmCropDto getFarmCropDto = farmCropMapper.toGetFarmCropDto(farmCrop);
        URI location = new URI(request.getRequestURL() + "/" + farmCrop.getId());
        return ResponseEntity.created(location).body(getFarmCropDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmCropDto>> findAllFarmCrops(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<FarmCrop> farmCrops = farmCropService.findAll(page, size, sort, sortDirection);
        List<GetFarmCropDto> getFarmCropDtos = farmCrops.stream()
                .map(farmCropMapper::toGetFarmCropDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmCropDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmCropDto> findFarmCropById(@PathVariable UUID id) throws Exception {
        FarmCrop farmCrop = farmCropService.findOne(id);
        GetFarmCropDto getFarmCropDto = farmCropMapper.toGetFarmCropDto(farmCrop);
        return ResponseEntity.ok(getFarmCropDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmCropDto> updateFarmCrop(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmCropDto updateFarmCropDto
    ) throws Exception {
        FarmCrop farmCrop = farmCropMapper.toFarmCrop(updateFarmCropDto);
        GetFarmCropDto getFarmCropDto = farmCropMapper.toGetFarmCropDto(farmCropService.update(id, farmCrop));
        return ResponseEntity.ok(getFarmCropDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmCrop(@PathVariable UUID id) {
        farmCropService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmCropService.getCount(farm));
        }
        return ResponseEntity.ok(farmCropService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmCropService.getTotal(farm));
        }
        return ResponseEntity.ok(farmCropService.getTotal());
    }
}
