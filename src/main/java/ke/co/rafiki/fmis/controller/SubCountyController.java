package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.SubCounty;
import ke.co.rafiki.fmis.dto.subcounty.CreateSubCountyDto;
import ke.co.rafiki.fmis.dto.subcounty.GetSubCountyDto;
import ke.co.rafiki.fmis.dto.subcounty.UpdateSubCountyDto;
import ke.co.rafiki.fmis.mapper.SubCountyMapper;
import ke.co.rafiki.fmis.service.SubCountyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("subcounties")
public class SubCountyController {

    private final SubCountyService subCountyService;
    private final SubCountyMapper subCountyMapper;

    public SubCountyController(SubCountyService subCountyService, SubCountyMapper subCountyMapper) {
        this.subCountyService = subCountyService;
        this.subCountyMapper = subCountyMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetSubCountyDto>> findAll(
            @RequestParam(name = "county", required = false) Integer countyId,
            @RequestParam(name = "constituency", required = false) Integer constituencyId
    ) {
        List<SubCounty> subCounties;
        if (countyId != null) {
            subCounties = subCountyService.findAll(County.builder().id(countyId).build());
        } else if (constituencyId != null) {
            subCounties = subCountyService.findAll(Constituency.builder().id(constituencyId).build());
        } else {
            subCounties = subCountyService.findAll(County.builder().id(countyId).build());
        }
        List<GetSubCountyDto> getSubCountyDtoList = subCounties
                .stream()
                .map(subCountyMapper::toGetSubCountyDto)
                .toList();
        return ResponseEntity.ok(getSubCountyDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubCountyDto> findOne(@PathVariable(name = "id") Integer id) throws Exception {
        SubCounty subCounty = subCountyService.findOne(id);
        GetSubCountyDto getSubCountyDto = subCountyMapper.toGetSubCountyDto(subCounty);
        return ResponseEntity.ok(getSubCountyDto);
    }

    @PostMapping
    public ResponseEntity<GetSubCountyDto> create(
            @RequestBody CreateSubCountyDto createSubCountyDto,
            HttpServletRequest request
    ) throws Exception {
        SubCounty subCounty = subCountyMapper.toSubCounty(createSubCountyDto);
        subCounty = subCountyService.save(subCounty);
        GetSubCountyDto getSubCountyDto = subCountyMapper.toGetSubCountyDto(subCounty);
        URI location = new URI(request.getRequestURL() + "/" + subCounty.getId());
        return ResponseEntity.created(location).body(getSubCountyDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetSubCountyDto> update(
            @PathVariable(name = "id") Integer id,
            @RequestBody UpdateSubCountyDto updateSubCountyDto
    ) throws Exception {
        SubCounty subCounty = subCountyMapper.toSubCounty(updateSubCountyDto);
        subCounty = subCountyService.update(id, subCounty);
        GetSubCountyDto getSubCountyDto = subCountyMapper.toGetSubCountyDto(subCounty);
        return ResponseEntity.ok(getSubCountyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) throws Exception {
        subCountyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
