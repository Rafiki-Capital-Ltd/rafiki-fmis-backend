package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmProduction;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmProductionRepository;
import ke.co.rafiki.fmis.service.FarmProductionService;
import ke.co.rafiki.fmis.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FarmProductionServiceImpl implements FarmProductionService {

    private final FarmProductionRepository farmProductionRepository;
    private final FarmService farmService;

    public  FarmProductionServiceImpl(
            FarmProductionRepository farmProductionRepository, FarmService farmService
    ) {
        this.farmProductionRepository = farmProductionRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmProduction create(FarmProduction farmProduction) throws Exception {
        Farm farm = farmService.findOne(farmProduction.getFarm().getId());
        farmProduction.setFarm(farm);
        return farmProductionRepository.save(farmProduction);
    }

    @Override
    public Page<FarmProduction> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmProductionRepository.findAll(pageRequest);
    }

    @Override
    public FarmProduction findOne(UUID id) throws Exception {
        return farmProductionRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmProduction update(UUID id, FarmProduction farmProduction) throws Exception {
        FarmProduction _farmProduction = this.findOne(id);
        _farmProduction.setDate(farmProduction.getDate());
        _farmProduction.setQuantity(farmProduction.getQuantity());
        return farmProductionRepository.save(_farmProduction);
    }

    @Override
    public void delete(UUID id) {
        farmProductionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmProductionRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmProduction> farmProductions) {
        farmProductionRepository.deleteAll(farmProductions);
    }

    @Override
    public Page<FarmProduction> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmProductionRepository.findByFarm(_farm, pageRequest);
    }
}
