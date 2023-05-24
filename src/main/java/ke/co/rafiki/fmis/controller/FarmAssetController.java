package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
import ke.co.rafiki.fmis.dto.farmasset.CreateFarmAssetDto;
import ke.co.rafiki.fmis.dto.farmasset.GetFarmAssetDto;
import ke.co.rafiki.fmis.dto.farmasset.UpdateFarmAssetDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmAssetMapper;
import ke.co.rafiki.fmis.service.FarmAssetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-assets")
public class FarmAssetController {

    private final FarmAssetService farmAssetService;
    private final FarmAssetMapper farmAssetMapper;

    public FarmAssetController(FarmAssetService farmAssetService, FarmAssetMapper farmAssetMapper) {
        this.farmAssetService = farmAssetService;
        this.farmAssetMapper = farmAssetMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmAssetDto> createFarmAsset(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmAssetDto createFarmAssetDto
    ) throws Exception {
        FarmAsset farmAsset = farmAssetService.save(farmAssetMapper.toFarmAsset(createFarmAssetDto));
        GetFarmAssetDto getFarmAssetDto = farmAssetMapper.toGetFarmAssetDto(farmAsset);
        URI location = new URI(request.getRequestURL() + "/" + farmAsset.getId());
        return ResponseEntity.created(location).body(getFarmAssetDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmAssetDto>> findAllFarmAssets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        Farm farm = Farm.builder().id(farmId).build();
        Page<FarmAsset> farmAssets = farmAssetService.findAll(farm, page, size, sort, sortDirection);
        List<GetFarmAssetDto> getFarmAssetDtos = farmAssets.stream()
                .map(farmAssetMapper::toGetFarmAssetDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmAssetDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmAssetDto> findFarmAssetById(@PathVariable String id) throws Exception {
        FarmAsset farmAsset = farmAssetService.findOne(UUID.fromString(id));
        GetFarmAssetDto getFarmAssetDto = farmAssetMapper.toGetFarmAssetDto(farmAsset);
        return ResponseEntity.ok(getFarmAssetDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmAssetDto> updateFarmAsset(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmAssetDto updateFarmAssetDto
    ) throws Exception {
        FarmAsset farmAsset = farmAssetMapper.toFarmAsset(updateFarmAssetDto);
        GetFarmAssetDto getFarmAssetDto = farmAssetMapper
                .toGetFarmAssetDto(farmAssetService.update(UUID.fromString(id), farmAsset));
        return ResponseEntity.ok(getFarmAssetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmAsset(@PathVariable String id) {
        farmAssetService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmAssetService.getCount(farm));
        }
        return ResponseEntity.ok(farmAssetService.getCount());
    }
}
