package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAsset;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmAssetRepository;
import ke.co.rafiki.fmis.service.FarmAssetService;
import ke.co.rafiki.fmis.service.FarmService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmAssetServiceImpl implements FarmAssetService {

    private final FarmAssetRepository farmAssetRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmAssetServiceImpl(
            FarmAssetRepository farmAssetRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmAssetRepository = farmAssetRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmAsset save(FarmAsset farmAsset) throws Exception {
        Farm farm = farmService.findOne(farmAsset.getFarm().getId());
        farmAsset.setFarm(farm);
        return farmAssetRepository.save(farmAsset);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public Page<FarmAsset> findAll(
            int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmAssetRepository.findAll(pageRequest);

        User user = userService.findOne(getAuthentication().getName());
        return farmAssetRepository.findByOwner(user, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject 'MANAGER')")
    public FarmAsset findOne(UUID id) throws Exception {
        return farmAssetRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmAsset', 'MANAGER')")
    public FarmAsset update(UUID id, FarmAsset farmAsset) throws Exception {
        FarmAsset _farmAsset = this.findOne(id);
        _farmAsset.setType(farmAsset.getType());
        _farmAsset.setDescription(farmAsset.getDescription());
        _farmAsset.setStorageLocation(farmAsset.getStorageLocation());
        _farmAsset.setStatus(farmAsset.getStatus());
        return farmAssetRepository.save(_farmAsset);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmAsset', 'MANAGER')")
    public void delete(UUID id) {
        farmAssetRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmAssetRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmAsset> farmAssets) {
        farmAssetRepository.deleteAll(farmAssets);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmAsset> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmAssetRepository.findByFarm(_farm, pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmAssetRepository.findByOwnerAndFarm(owner, farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmAssetRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmAssetRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmAssetRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmAssetRepository.findByOwnerAndFarm(owner, _farm).size();
    }
}
