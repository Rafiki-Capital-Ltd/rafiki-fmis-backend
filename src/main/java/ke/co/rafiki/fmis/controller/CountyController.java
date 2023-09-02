package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.dto.county.CreateCountyDto;
import ke.co.rafiki.fmis.dto.county.GetCountyDto;
import ke.co.rafiki.fmis.dto.county.UpdateCountyDto;
import ke.co.rafiki.fmis.mapper.CountyMapper;
import ke.co.rafiki.fmis.service.CountyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("counties")
public class CountyController {
    private final CountyService countyService;
    private final CountyMapper countyMapper;

    public CountyController(CountyService countyService, CountyMapper countyMapper) {
        this.countyService = countyService;
        this.countyMapper = countyMapper;
    }

    @GetMapping
    private ResponseEntity<Page<GetCountyDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam (defaultValue = "ASC", name = "sort_direction") String sortDirection
    ) {
        List<GetCountyDto> counties = countyService.findAll(page, size, sort, sortDirection)
                .stream()
                .map(countyMapper::toGetCountyDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(counties));
    }

    @GetMapping("/{id}")
    private ResponseEntity<GetCountyDto> findOne(@PathVariable(name = "id") Integer id) throws Exception {
        County county = countyService.findOne(id);
        GetCountyDto getCountyDto = countyMapper.toGetCountyDto(county);
        return ResponseEntity.ok(getCountyDto);
    }

    @PostMapping
    private ResponseEntity<GetCountyDto> create(
            @RequestBody CreateCountyDto createCountyDto,
            HttpServletRequest request
    ) throws Exception {
        County _county = countyMapper.toCounty(createCountyDto);
        County county = countyService.save(_county);
        GetCountyDto getCountyDto = countyMapper.toGetCountyDto(county);
        URI location = new URI(request.getRequestURL() + "/" + county.getId());
        return ResponseEntity.created(location).body(getCountyDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<GetCountyDto> update(
            @RequestBody UpdateCountyDto updateCountyDto,
            @PathVariable(name = "id") Integer id
    ) throws Exception {
        County _county = countyMapper.toCounty(updateCountyDto);
        County county = countyService.update(id, _county);
        GetCountyDto getCountyDto = countyMapper.toGetCountyDto(county);
        return ResponseEntity.ok(getCountyDto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) throws Exception {
        countyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
