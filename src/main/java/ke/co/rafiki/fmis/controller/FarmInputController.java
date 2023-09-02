package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmInput;
import ke.co.rafiki.fmis.dto.farminput.CreateFarmInputDto;
import ke.co.rafiki.fmis.dto.farminput.GetFarmInputDto;
import ke.co.rafiki.fmis.dto.farminput.UpdateFarmInputDto;
import ke.co.rafiki.fmis.mapper.FarmInputMapper;
import ke.co.rafiki.fmis.service.FarmInputService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-inputs")
public class FarmInputController {

    private final FarmInputService farmInputService;
    private final FarmInputMapper farmInputMapper;

    public FarmInputController(FarmInputService farmInputService, FarmInputMapper farmInputMapper) {
        this.farmInputService = farmInputService;
        this.farmInputMapper = farmInputMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmInputDto> createFarmInput(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmInputDto createFarmInputDto
    ) throws Exception {
        FarmInput farmInput = farmInputService.save(farmInputMapper.toFarmInput(createFarmInputDto));
        GetFarmInputDto getFarmInputDto = farmInputMapper.toGetFarmInputDto(farmInput);
        URI location = new URI(request.getRequestURL() + "/" + farmInput.getId());
        return ResponseEntity.created(location).body(getFarmInputDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmInputDto>> findAllFarmInputs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection,
            @RequestParam(name = "farm", required = false) UUID farmId
    ) throws Exception {
        Page<FarmInput> farmInputs;

        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            farmInputs = farmInputService.findAll(farm, page, size, sort, sortDirection);
        } else
            farmInputs = farmInputService.findAll(page, size, sort, sortDirection);

        List<GetFarmInputDto> getFarmInputDtos = farmInputs.stream()
                .map(farmInputMapper::toGetFarmInputDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmInputDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmInputDto> findFarmInputById(@PathVariable String id) throws Exception {
        FarmInput farmInput = farmInputService.findOne(UUID.fromString(id));
        GetFarmInputDto getFarmInputDto = farmInputMapper.toGetFarmInputDto(farmInput);
        return ResponseEntity.ok(getFarmInputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmInputDto> updateFarmInput(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmInputDto updateFarmInputDto
    ) throws Exception {
        FarmInput farmInput = farmInputMapper.toFarmInput(updateFarmInputDto);
        GetFarmInputDto getFarmInputDto = farmInputMapper
                .toGetFarmInputDto(farmInputService.update(UUID.fromString(id), farmInput));
        return ResponseEntity.ok(getFarmInputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmInput(@PathVariable String id) {
        farmInputService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmInputService.getCount(farm));
        }
        return ResponseEntity.ok(farmInputService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmInputService.getTotal(farm));
        }
        return ResponseEntity.ok(farmInputService.getTotal());
    }
}
