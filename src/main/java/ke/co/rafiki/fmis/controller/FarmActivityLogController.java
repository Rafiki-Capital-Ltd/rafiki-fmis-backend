package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmActivityLog;
import ke.co.rafiki.fmis.dto.farmactivitylog.CreateFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.GetFarmActivityLogDto;
import ke.co.rafiki.fmis.dto.farmactivitylog.UpdateFarmActivityLogDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmActivityLogMapper;
import ke.co.rafiki.fmis.service.FarmActivityLogService;
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
            @CookieValue(name = FARM_CONTEXT_COOKIE_KEY) UUID farmId,
            @Valid @RequestBody CreateFarmActivityLogDto createFarmActivityLogDto
    ) throws Exception {
        if (farmId == null && createFarmActivityLogDto.getFarm() == null)
            throw new BadRequestException();

        if (createFarmActivityLogDto.getFarm() == null)
            createFarmActivityLogDto.setFarm(Farm.builder().id(farmId).build());

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
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection,
            @RequestParam(name = "farm", required = false) UUID farmId
    ) throws Exception {
        Page<FarmActivityLog> farmActivityLogs;

        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            farmActivityLogs = farmActivityLogService.findAll(farm, page, size, sort, sortDirection);
        } else
            farmActivityLogs = farmActivityLogService.findAll(page, size, sort, sortDirection);

        List<GetFarmActivityLogDto> getFarmActivityLogDtos = farmActivityLogs.stream()
                .map(farmActivityLogMapper::toGetFarmActivityLogDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmActivityLogDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmActivityLogDto> findFarmActivityLogById(@PathVariable String id) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogService.findOne(UUID.fromString(id));
        GetFarmActivityLogDto getFarmActivityLogDto = farmActivityLogMapper.toGetFarmActivityLogDto(farmActivityLog);
        return ResponseEntity.ok(getFarmActivityLogDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmActivityLogDto> updateFarmActivityLog(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmActivityLogDto updateFarmActivityLogDto
    ) throws Exception {
        FarmActivityLog farmActivityLog = farmActivityLogMapper.toFarmActivityLog(updateFarmActivityLogDto);
        GetFarmActivityLogDto getFarmActivityLogDto = farmActivityLogMapper
                .toGetFarmActivityLogDto(farmActivityLogService.update(UUID.fromString(id), farmActivityLog));
        return ResponseEntity.ok(getFarmActivityLogDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmActivityLog(@PathVariable String id) {
        farmActivityLogService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
