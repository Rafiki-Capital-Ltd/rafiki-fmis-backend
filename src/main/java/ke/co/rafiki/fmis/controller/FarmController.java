package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.dto.farm.CreateFarmDto;
import ke.co.rafiki.fmis.dto.farm.GetFarmDto;
import ke.co.rafiki.fmis.dto.farm.UpdateFarmDto;
import ke.co.rafiki.fmis.mapper.FarmMapper;
import ke.co.rafiki.fmis.service.FarmService;
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
@RequestMapping("farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmDto> createFarm(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmDto createFarmDto
    ) throws Exception {
        Farm farm = farmService.save(farmMapper.toFarm(createFarmDto));
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        URI location = new URI(request.getRequestURL() + "/" + farm.getId());
        return ResponseEntity.created(location).body(getFarmDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmDto>> findAllFarms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<Farm> farms = farmService.findAll(page, size, sort, sortDirection);
        List<GetFarmDto> getFarmDtos = farms.stream()
                .map(farmMapper::toGetFarmDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmDto> findFarmById(@PathVariable UUID id) throws Exception {
        Farm farm = farmService.findOne(id);
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        return ResponseEntity.ok(getFarmDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmDto> updateFarm(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmDto updateFarmDto
    ) throws Exception {
        Farm farm = farmMapper.toFarm(updateFarmDto);
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farmService.update(id, farm));
        return ResponseEntity.ok(getFarmDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFarm(@PathVariable UUID id) {
        farmService.delete(id);
    }

    @GetMapping("/context/{id}")
    public ResponseEntity<GetFarmDto> switchFarmContext(@PathVariable UUID id, HttpServletResponse response) throws Exception {
        Farm farm = farmService.findOne(id);
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        Cookie cookie = new Cookie(FARM_CONTEXT_COOKIE_KEY, farm.getId().toString());
        cookie.setMaxAge(30 * 24 * 60 * 60); // expires after 30 days
        response.addCookie(cookie);
        return ResponseEntity.ok(getFarmDto);
    }
}
