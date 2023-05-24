package ke.co.rafiki.fmis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import ke.co.rafiki.fmis.dto.farmanimal.CreateFarmAnimalDto;
import ke.co.rafiki.fmis.dto.farmanimal.GetFarmAnimalDto;
import ke.co.rafiki.fmis.dto.farmanimal.UpdateFarmAnimalDto;
import ke.co.rafiki.fmis.mapper.FarmAnimalMapper;
import ke.co.rafiki.fmis.service.FarmAnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@RestController
@RequestMapping("farm-animals")
public class FarmAnimalController {

    private final FarmAnimalService farmAnimalService;
    private final FarmAnimalMapper farmAnimalMapper;

    public FarmAnimalController(FarmAnimalService farmAnimalService, FarmAnimalMapper farmAnimalMapper) {
        this.farmAnimalService = farmAnimalService;
        this.farmAnimalMapper = farmAnimalMapper;
    }

    @PostMapping
    public ResponseEntity<GetFarmAnimalDto> createFarmAnimal(
            HttpServletRequest request,
            @Valid @RequestBody CreateFarmAnimalDto createFarmAnimalDto
    ) throws Exception {
        FarmAnimal farmAnimal = farmAnimalService.save(farmAnimalMapper.toFarmAnimal(createFarmAnimalDto));
        GetFarmAnimalDto getFarmAnimalDto = farmAnimalMapper.toGetFarmAnimalDto(farmAnimal);
        URI location = new URI(request.getRequestURL() + "/" + farmAnimal.getId());
        return ResponseEntity.created(location).body(getFarmAnimalDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetFarmAnimalDto>> findAllFarmAnimals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam (defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) throws Exception {
        Page<FarmAnimal> farmAnimals = farmAnimalService.findAll(page, size, sort, sortDirection);
        List<GetFarmAnimalDto> getFarmAnimalDtos = farmAnimals.stream()
                .map(farmAnimalMapper::toGetFarmAnimalDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(getFarmAnimalDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFarmAnimalDto> findFarmAnimalById(@PathVariable String id) throws Exception {
        FarmAnimal farmAnimal = farmAnimalService.findOne(UUID.fromString(id));
        GetFarmAnimalDto getFarmAnimalDto = farmAnimalMapper.toGetFarmAnimalDto(farmAnimal);
        return ResponseEntity.ok(getFarmAnimalDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetFarmAnimalDto> updateFarmAnimal(
            @PathVariable String id,
            @Valid @RequestBody UpdateFarmAnimalDto updateFarmAnimalDto
    ) throws Exception {
        FarmAnimal farmAnimal = farmAnimalMapper.toFarmAnimal(updateFarmAnimalDto);
        GetFarmAnimalDto getFarmAnimalDto = farmAnimalMapper
                .toGetFarmAnimalDto(farmAnimalService.update(UUID.fromString(id), farmAnimal));
        return ResponseEntity.ok(getFarmAnimalDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmAnimal(@PathVariable String id) {
        farmAnimalService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmAnimalService.getCount(farm));
        }
        return ResponseEntity.ok(farmAnimalService.getCount());
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(
            HttpServletRequest request,
            @RequestParam(name = "farm") UUID farmId
    ) throws Exception {
        if (farmId != null) {
            Farm farm = Farm.builder().id(farmId).build();
            return ResponseEntity.ok(farmAnimalService.getTotal(farm));
        }
        return ResponseEntity.ok(farmAnimalService.getTotal());
    }
}
