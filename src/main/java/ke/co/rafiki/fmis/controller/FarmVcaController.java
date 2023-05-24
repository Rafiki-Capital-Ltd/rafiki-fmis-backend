package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmVca;
import ke.co.rafiki.fmis.dto.farmvca.CreateFarmVcaDto;
import ke.co.rafiki.fmis.dto.farmvca.GetFarmVcaDto;
import ke.co.rafiki.fmis.dto.farmvca.UpdateFarmVcaDto;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.mapper.FarmVcaMapper;
import ke.co.rafiki.fmis.service.FarmVcaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-vcas")
public class FarmVcaController {

    private final FarmVcaService farmVcaService;
    private final FarmVcaMapper farmVcaMapper;

    public FarmVcaController(FarmVcaService farmVcaService, FarmVcaMapper farmVcaMapper) {
        this.farmVcaService = farmVcaService;
        this.farmVcaMapper = farmVcaMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmVcaDto> createFarmVca(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmVcaDto createFarmVcaDto
    ) throws Exception {
        FarmVca farmVca = farmVcaService.save(farmVcaMapper.toFarmVca(createFarmVcaDto));
        GetFarmVcaDto getFarmVcaDto = farmVcaMapper.toGetFarmVcaDto(farmVca);
        URI location = new URI(request.getRequestURL() + "/" + farmVca.getId());
        return ResponseEntity.created(location).body(getFarmVcaDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmVcaDto>> findAllFarmVcas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        Page<FarmVca> farmVcas = farmVcaService.findAll(page, size, sort, sortDirection);
        List<GetFarmVcaDto> getFarmVcaDtos = farmVcas.stream()
                .map(farmVcaMapper::toGetFarmVcaDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmVcaDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmVcaDto> findFarmVcaById(@PathVariable UUID id) throws Exception {
        FarmVca farmVca = farmVcaService.findOne(id);
        GetFarmVcaDto getFarmVcaDto = farmVcaMapper.toGetFarmVcaDto(farmVca);
        return ResponseEntity.ok(getFarmVcaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmVcaDto> updateFarmVca(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFarmVcaDto updateFarmVcaDto
    ) throws Exception {
        FarmVca farmVca = farmVcaMapper.toFarmVca(updateFarmVcaDto);
        GetFarmVcaDto getFarmVcaDto = farmVcaMapper.toGetFarmVcaDto(farmVcaService.update(id, farmVca));
        return ResponseEntity.ok(getFarmVcaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmVca(@PathVariable UUID id) {
        farmVcaService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmVcaService.getCount(farm));
        }
        return ResponseEntity.ok(farmVcaService.getCount());
    }
}
