package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmAssetRepository;
import ke.co.rafiki.fmis.service.FarmAssetService;
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
public class FarmAssetServiceImpl implements FarmAssetService {

    private final FarmAssetRepository farmAssetRepository;
    private final FarmService farmService;

    public  FarmAssetServiceImpl(
            FarmAssetRepository farmAssetRepository, FarmService farmService
    ) {
        this.farmAssetRepository = farmAssetRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmAsset create(FarmAsset farmAsset) throws Exception {
        Farm farm = farmService.findOne(farmAsset.getFarm().getId());
        farmAsset.setFarm(farm);
        return farmAssetRepository.save(farmAsset);
    }

    @Override
    public Page<FarmAsset> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmAssetRepository.findAll(pageRequest);
    }

    @Override
    public FarmAsset findOne(UUID id) throws Exception {
        return farmAssetRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmAsset update(UUID id, FarmAsset farmAsset) throws Exception {
        FarmAsset _farmAsset = this.findOne(id);
        _farmAsset.setType(farmAsset.getType());
        _farmAsset.setDescription(farmAsset.getDescription());
        _farmAsset.setStorageLocation(farmAsset.getStorageLocation());
        _farmAsset.setStatus(farmAsset.getStatus());
        return farmAssetRepository.save(_farmAsset);
    }

    @Override
    public void delete(UUID id) {
        farmAssetRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmAssetRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmAsset> farmAssets) {
        farmAssetRepository.deleteAll(farmAssets);
    }

    @Override
    public Page<FarmAsset> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmAssetRepository.findByFarm(_farm, pageRequest);
    }
}
