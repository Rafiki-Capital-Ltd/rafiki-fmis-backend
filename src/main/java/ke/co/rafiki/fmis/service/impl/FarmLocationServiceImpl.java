package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmLocation;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmLocationRepository;
import ke.co.rafiki.fmis.service.FarmLocationService;
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
public class FarmLocationServiceImpl implements FarmLocationService {

    private final FarmLocationRepository farmLocationRepository;
    private final FarmService farmService;

    public  FarmLocationServiceImpl(
            FarmLocationRepository farmLocationRepository, FarmService farmService
    ) {
        this.farmLocationRepository = farmLocationRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmLocation create(FarmLocation farmLocation) throws Exception {
        Farm farm = farmService.findOne(farmLocation.getFarm().getId());
        farmLocation.setFarm(farm);
        return farmLocationRepository.save(farmLocation);
    }

    @Override
    public Page<FarmLocation> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmLocationRepository.findAll(pageRequest);
    }

    @Override
    public FarmLocation findOne(UUID id) throws Exception {
        return farmLocationRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmLocation update(UUID id, FarmLocation farmLocation) throws Exception {
        FarmLocation _farmLocation = this.findOne(id);
        _farmLocation.setLatitude(farmLocation.getLatitude());
        _farmLocation.setLongitude(farmLocation.getLongitude());
        return farmLocationRepository.save(_farmLocation);
    }

    @Override
    public void delete(UUID id) {
        farmLocationRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmLocationRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmLocation> farmLocations) {
        farmLocationRepository.deleteAll(farmLocations);
    }

    @Override
    public FarmLocation findByFarm(Farm farm) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        return farmLocationRepository.findByFarm(_farm);
    }
}
