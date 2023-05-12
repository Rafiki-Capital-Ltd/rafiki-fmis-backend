package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmValueChainAddition;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmValueChainAdditionRepository;
import ke.co.rafiki.fmis.service.FarmValueChainAdditionService;
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
public class FarmValueChainAdditionServiceImpl implements FarmValueChainAdditionService {

    private final FarmValueChainAdditionRepository farmValueChainAdditionRepository;
    private final FarmService farmService;

    public  FarmValueChainAdditionServiceImpl(
            FarmValueChainAdditionRepository farmValueChainAdditionRepository, FarmService farmService
    ) {
        this.farmValueChainAdditionRepository = farmValueChainAdditionRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmValueChainAddition create(FarmValueChainAddition farmValueChainAddition) throws Exception {
        Farm farm = farmService.findOne(farmValueChainAddition.getFarm().getId());
        farmValueChainAddition.setFarm(farm);
        return farmValueChainAdditionRepository.save(farmValueChainAddition);
    }

    @Override
    public Page<FarmValueChainAddition> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmValueChainAdditionRepository.findAll(pageRequest);
    }

    @Override
    public FarmValueChainAddition findOne(UUID id) throws Exception {
        return farmValueChainAdditionRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmValueChainAddition update(UUID id, FarmValueChainAddition farmValueChainAddition) throws Exception {
        FarmValueChainAddition _farmValueChainAddition = this.findOne(id);
        _farmValueChainAddition.setType(farmValueChainAddition.getType());
        _farmValueChainAddition.setDescription(farmValueChainAddition.getDescription());
        return farmValueChainAdditionRepository.save(_farmValueChainAddition);
    }

    @Override
    public void delete(UUID id) {
        farmValueChainAdditionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmValueChainAdditionRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmValueChainAddition> farmValueChainAdditions) {
        farmValueChainAdditionRepository.deleteAll(farmValueChainAdditions);
    }

    @Override
    public Page<FarmValueChainAddition> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmValueChainAdditionRepository.findByFarm(_farm, pageRequest);
    }
}
