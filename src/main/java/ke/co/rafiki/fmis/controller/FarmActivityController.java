package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.FarmActivity;
import ke.co.rafiki.fmis.dto.farmactivity.CreateFarmActivityDto;
import ke.co.rafiki.fmis.dto.farmactivity.GetFarmActivityDto;
import ke.co.rafiki.fmis.dto.farmactivity.UpdateFarmActivityDto;
import ke.co.rafiki.fmis.mapper.FarmActivityMapper;
import ke.co.rafiki.fmis.service.FarmActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-activities")
public class FarmActivityController {

    private final FarmActivityService farmActivityService;
    private final FarmActivityMapper farmActivityMapper;

    public FarmActivityController(FarmActivityService farmActivityService, FarmActivityMapper farmActivityMapper) {
        this.farmActivityService = farmActivityService;
        this.farmActivityMapper = farmActivityMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmActivityDto> createFarmActivity(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmActivityDto createFarmActivityDto
    ) throws Exception {
        FarmActivity farmActivity = farmActivityService.save(farmActivityMapper.toFarmActivity(createFarmActivityDto));
        GetFarmActivityDto getFarmActivityDto = farmActivityMapper.toGetFarmActivityDto(farmActivity);
        URI location = new URI(request.getRequestURL() + "/" + farmActivity.getId());
        return ResponseEntity.created(location).body(getFarmActivityDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmActivityDto>> findAllFarmActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<FarmActivity> farmActivities = farmActivityService.findAll(page, size, sort, sortDirection);
        List<GetFarmActivityDto> getFarmActivityDtos = farmActivities.stream()
                .map(farmActivityMapper::toGetFarmActivityDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmActivityDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmActivityDto> findFarmActivityById(@PathVariable String id) throws Exception {
        FarmActivity farmActivity = farmActivityService.findOne(UUID.fromString(id));
        GetFarmActivityDto getFarmActivityDto = farmActivityMapper.toGetFarmActivityDto(farmActivity);
        return ResponseEntity.ok(getFarmActivityDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmActivityDto> updateFarmActivity(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmActivityDto updateFarmActivityDto
    ) throws Exception {
        FarmActivity farmActivity = farmActivityMapper.toFarmActivity(updateFarmActivityDto);
        GetFarmActivityDto getFarmActivityDto = farmActivityMapper
                .toGetFarmActivityDto(farmActivityService.update(UUID.fromString(id), farmActivity));
        return ResponseEntity.ok(getFarmActivityDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmActivity(@PathVariable String id) {
        farmActivityService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
