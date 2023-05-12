package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmRepository;
import ke.co.rafiki.fmis.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final UserService userService;
    private final FarmActivityLogService farmActivityLogService;
    private final FarmActivityService farmActivityService;
    private final FarmAssetService farmAssetService;
    private final FarmConsumptionService farmConsumptionService;
    private final FarmProductionService farmProductionService;
    private final FarmSaleService farmSaleService;
    private final FarmValueChainAdditionService farmValueChainAdditionService;

    public FarmServiceImpl(
            FarmRepository farmRepository, UserService userService,
            FarmActivityLogService farmActivityLogService, FarmActivityService farmActivityService,
            FarmAssetService farmAssetService, FarmConsumptionService farmConsumptionService,
            FarmProductionService farmProductionService, FarmSaleService farmSaleService,
            FarmValueChainAdditionService farmValueChainAdditionService
    ) {
        this.farmRepository = farmRepository;
        this.userService = userService;
        this.farmActivityLogService = farmActivityLogService;
        this.farmActivityService = farmActivityService;
        this.farmAssetService = farmAssetService;
        this.farmConsumptionService = farmConsumptionService;
        this.farmProductionService = farmProductionService;
        this.farmSaleService = farmSaleService;
        this.farmValueChainAdditionService = farmValueChainAdditionService;
    }

    @Override
    public Farm create(Farm farm) {
        return farmRepository.save(farm);
    }

    @Override
    public Page<Farm> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmRepository.findAll(pageRequest);
    }

    @Override
    public Farm findOne(UUID id) throws Exception {
        return farmRepository.findById(id).orElseThrow(() -> {
            String message = "Farm id " + id + " was not found";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public Farm update(UUID id, Farm farm) throws Exception {
        Farm _farm = this.findOne(id);
        _farm.setName(farm.getName());
        _farm.setSize(farm.getSize());
        _farm.setCounty(farm.getCounty());
        _farm.setWard(farm.getWard());
        _farm.setNearestShoppingCenter(farm.getNearestShoppingCenter());
        _farm.setLocation(farm.getLocation());
        return farmRepository.save(_farm);
    }

    @Override
    public void delete(UUID id) {
        farmRepository.deleteById(id);
    }

    @Override
    public void deleteMany(List<Farm> farms) {
        farmRepository.deleteAll(farms);
    }

    @Override
    public void deleteAll() {
        farmRepository.deleteAll();
    }

    @Override
    public FarmActivityLog findFarmDiary(Farm farm) throws Exception {
        return farmActivityLogService.findOne(farm);
    }

    @Override
    public Page<Farm> findByFarmer(Principal farmer, int page, int size, String sort, String sortDirection) throws Exception {
        User _farmer = userService.findOne(farmer.getName());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmRepository.findByFarmer(_farmer, pageRequest);
    }

    @Override
    public Page<FarmActivity> findActivities(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmActivityService.findByFarm(farm, page, size, sort, sortDirection);
    }

    @Override
    public Page<FarmAsset> findAssets(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmAssetService.findByFarm(farm, page, size, sort, sortDirection);
    }

    @Override
    public Page<FarmConsumption> findConsumptions(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmConsumptionService.findByFarm(farm, page, size, sort, sortDirection);
    }

    @Override
    public Page<FarmProduction> findProductions(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmProductionService.findByFarm(farm, page, size, sort, sortDirection);
    }

    @Override
    public Page<FarmSale> findSales(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmSaleService.findByFarm(farm, page, size, sort, sortDirection);
    }

    @Override
    public Page<FarmValueChainAddition> findValueChainAdditions(Farm farm, int page, int size, String sort, String sortDirection) {
        return farmValueChainAdditionService.findByFarm(farm, page, size, sort, sortDirection);
    }
}
