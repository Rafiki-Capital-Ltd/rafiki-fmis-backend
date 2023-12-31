package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.dto.farm.CreateFarmDto;
import ke.co.rafiki.fmis.dto.farm.GetFarmDto;
import ke.co.rafiki.fmis.dto.farm.UpdateFarmDto;
import ke.co.rafiki.fmis.mapper.CountyMapper;
import ke.co.rafiki.fmis.mapper.FarmMapper;
import ke.co.rafiki.fmis.service.FarmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.misc.Constants.*;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@RestController
@RequestMapping("farms")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;
    private final CountyMapper countyMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper,
                          CountyMapper countyMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
        this.countyMapper = countyMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmDto> createFarm(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody CreateFarmDto createFarmDto
    ) throws Exception {
        Farm farm = farmService.save(farmMapper.toFarm(createFarmDto));
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        URI location = new URI(request.getRequestURL() + "/" + farm.getId());
        Cookie cookie = new Cookie(FARM_CONTEXT_COOKIE_KEY, farm.getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.created(location).body(getFarmDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmDto>> findAllFarms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<Farm> farms = farmService.findAll(page, size, sort, sortDirection);
        List<GetFarmDto> getFarmDtos = farms.stream()
                .map(farmMapper::toGetFarmDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmDto> findFarmById(@PathVariable String id) throws Exception {
        Farm farm = farmService.findOne(UUID.fromString(id));
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        return ResponseEntity.ok(getFarmDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmDto> updateFarm(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmDto updateFarmDto
    ) throws Exception {
        Farm farm = farmMapper.toFarm(updateFarmDto);
        GetFarmDto getFarmDto = farmMapper
                .toGetFarmDto(farmService.update(UUID.fromString(id), farm));
        return ResponseEntity.ok(getFarmDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarm(@PathVariable String id) {
        farmService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/context")
    public ResponseEntity<GetFarmDto> getFarmContext(@CookieValue(name = FARM_CONTEXT_COOKIE_KEY, required = false) UUID farmId) throws Exception {
        Farm farm = farmService.findOne(farmId);
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        return ResponseEntity.ok(getFarmDto);
    }

    @GetMapping("/context/{id}")
    public ResponseEntity<GetFarmDto> switchFarmContext(@PathVariable String id, HttpServletResponse response) throws Exception {
        Farm farm = farmService.findOne(UUID.fromString(id));
        GetFarmDto getFarmDto = farmMapper.toGetFarmDto(farm);
        Cookie cookie = new Cookie(FARM_CONTEXT_COOKIE_KEY, farm.getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(getFarmDto);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(farmService.getCount());
    }
}
