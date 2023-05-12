package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmConsumption;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmConsumptionRepository;
import ke.co.rafiki.fmis.service.FarmConsumptionService;
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
public class FarmConsumptionServiceImpl implements FarmConsumptionService {

    private final FarmConsumptionRepository farmConsumptionRepository;
    private final FarmService farmService;

    public  FarmConsumptionServiceImpl(
            FarmConsumptionRepository farmConsumptionRepository, FarmService farmService
    ) {
        this.farmConsumptionRepository = farmConsumptionRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmConsumption create(FarmConsumption farmConsumption) throws Exception {
        Farm farm = farmService.findOne(farmConsumption.getFarm().getId());
        farmConsumption.setFarm(farm);
        return farmConsumptionRepository.save(farmConsumption);
    }

    @Override
    public Page<FarmConsumption> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmConsumptionRepository.findAll(pageRequest);
    }

    @Override
    public FarmConsumption findOne(UUID id) throws Exception {
        return farmConsumptionRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmConsumption update(UUID id, FarmConsumption farmConsumption) throws Exception {
        FarmConsumption _farmConsumption = this.findOne(id);
        _farmConsumption.setDate(farmConsumption.getDate());
        _farmConsumption.setQuantity(farmConsumption.getQuantity());
        return farmConsumptionRepository.save(_farmConsumption);
    }

    @Override
    public void delete(UUID id) {
        farmConsumptionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmConsumptionRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmConsumption> farmConsumptions) {
        farmConsumptionRepository.deleteAll(farmConsumptions);
    }

    @Override
    public Page<FarmConsumption> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmConsumptionRepository.findByFarm(_farm, pageRequest);
    }
}
