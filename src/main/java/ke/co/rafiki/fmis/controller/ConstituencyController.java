package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.dto.constituency.CreateConstituencyDto;
import ke.co.rafiki.fmis.dto.constituency.GetConstituecyDto;
import ke.co.rafiki.fmis.dto.constituency.UpdateConstituencyDto;
import ke.co.rafiki.fmis.mapper.ConstituencyMapper;
import ke.co.rafiki.fmis.service.ConstituencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("constituencies")
public class ConstituencyController {

    private final ConstituencyService constituencyService;
    private final ConstituencyMapper constituencyMapper;

    public ConstituencyController(ConstituencyService constituencyService, ConstituencyMapper constituencyMapper) {
        this.constituencyService = constituencyService;
        this.constituencyMapper = constituencyMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetConstituecyDto>> findAll(@RequestParam(name = "county", required = false) Integer countyId) {
        List<Constituency> constituencies;
        if (countyId != null) {
            constituencies = constituencyService.findAll(County.builder().id(countyId).build());
        } else {
            constituencies = constituencyService.findAll();
        }
        List<GetConstituecyDto> constituecyDtos = constituencies
                .stream()
                .map(constituencyMapper::toGetConstituencyDto)
                .toList();
        return ResponseEntity.ok(constituecyDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetConstituecyDto> findOne(@PathVariable(name = "id") Integer id) throws Exception {
        Constituency constituency = constituencyService.findOne(id);
        GetConstituecyDto getConstituecyDto = constituencyMapper.toGetConstituencyDto(constituency);
        return ResponseEntity.ok(getConstituecyDto);
    }

    @PostMapping
    public ResponseEntity<GetConstituecyDto> create(
            @RequestBody CreateConstituencyDto createConstituencyDto,
            HttpServletRequest request
    ) throws Exception {
        Constituency constituency = constituencyMapper.toConstituency(createConstituencyDto);
        constituency = constituencyService.save(constituency);
        GetConstituecyDto getConstituecyDto = constituencyMapper.toGetConstituencyDto(constituency);
        URI location = new URI(request.getRequestURL() + "/" + constituency.getId());
        return ResponseEntity.created(location).body(getConstituecyDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetConstituecyDto> upddate(
            @RequestBody UpdateConstituencyDto updateConstituencyDto,
            @PathVariable(name = "id") Integer id
    ) throws Exception {
        Constituency constituency = constituencyMapper.toConstituency(updateConstituencyDto);
        constituency = constituencyService.update(id, constituency);
        GetConstituecyDto getConstituecyDto = constituencyMapper.toGetConstituencyDto(constituency);
        return ResponseEntity.ok(getConstituecyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) throws Exception {
        constituencyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
