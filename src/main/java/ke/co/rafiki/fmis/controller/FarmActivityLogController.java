package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.dto.farmactivitylog.CreateFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.GetFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.UpdateFarmActivityLogDto;
import ke.co.rafiki.fmis.mapper.FarmActivityLogMapper;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-activity-logs")
public class FarmActivityLogController {

    private final FarmActivityLogService farmActivityLogService;
    private final FarmActivityLogMapper farmActivityLogMapper;

    public FarmActivityLogController(FarmActivityLogService farmActivityLogService, FarmActivityLogMapper farmActivityLogMapper) {
        this.farmActivityLogService = farmActivityLogService;
        this.farmActivityLogMapper = farmActivityLogMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmActivityLogDto> createFarmActivityLog(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmActivityLogDto createFarmActivityLogDto
    ) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogService.save(farmActivityLogMapper.toFarmActivityLog(createFarmActivityLogDto));
        GetFarmActivityLogDto getFarmActivityLogDto = farmActivityLogMapper.toGetFarmActivityLogDto(farmActivityLog);
        URI location = new URI(request.getRequestURL() + "/" + farmActivityLog.getId());
        return ResponseEntity.created(location).body(getFarmActivityLogDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmActivityLogDto>> findAllFarmActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<FarmActivityLog> farmActivities = farmActivityLogService.findAll(page, size, sort, sortDirection);
        List<GetFarmActivityLogDto> getFarmActivityLogDtos = farmActivities.stream()
                .map(farmActivityLogMapper::toGetFarmActivityLogDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmActivityLogDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmActivityLogDto> findFarmActivityLogById(@PathVariable UUID id) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogService.findOne(id);
        GetFarmActivityLogDto getFarmActivityLogDto = farmActivityLogMapper.toGetFarmActivityLogDto(farmActivityLog);
        return ResponseEntity.ok(getFarmActivityLogDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmActivityLogDto> updateFarmActivityLog(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmActivityLogDto updateFarmActivityLogDto
    ) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogMapper.toFarmActivityLog(updateFarmActivityLogDto);
        GetFarmActivityLogDto getFarmActivityLogDto = farmActivityLogMapper.toGetFarmActivityLogDto(farmActivityLogService.update(id, farmActivityLog));
        return ResponseEntity.ok(getFarmActivityLogDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFarmActivityLog(@PathVariable UUID id) {
        farmActivityLogService.delete(id);
    }
}
