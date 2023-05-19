package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmAnimalRepository;
import ke.co.rafiki.fmis.service.FarmAnimalService;
import ke.co.rafiki.fmis.service.FarmService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmAnimalServiceImpl implements FarmAnimalService {

    private final FarmAnimalRepository farmAnimalRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmAnimalServiceImpl(
            FarmAnimalRepository farmAnimalRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmAnimalRepository = farmAnimalRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    @PreAuthorize("hasRole('FARMER')")
    public FarmAnimal save(FarmAnimal farmAnimal) throws Exception {
        Farm farm = farmService.findOne(farmAnimal.getFarm().getId());
        farmAnimal.setFarm(farm);
        return farmAnimalRepository.save(farmAnimal);
    }

    @Override
    @PreAuthorize("hasRole('MANAGER')")
    public Page<FarmAnimal> findAll(
            int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmAnimalRepository.findAll(pageRequest);

        User user = userService.findOne(getAuthentication().getName());
        return farmAnimalRepository.findByOwner(user, pageRequest);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmAnimal', 'MANAGER')")
    public FarmAnimal findOne(UUID id) throws Exception {
        return farmAnimalRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmAnimal', 'MANAGER')")
    public FarmAnimal update(UUID id, FarmAnimal farmAnimal) throws Exception {
        FarmAnimal _farmAnimal = this.findOne(id);
        _farmAnimal.setName(farmAnimal.getName());
        _farmAnimal.setQuantity(farmAnimal.getQuantity());
        _farmAnimal.setDescription(farmAnimal.getDescription());
        return farmAnimalRepository.save(_farmAnimal);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmAnimal', 'MANAGER')")
    public void delete(UUID id) {
        farmAnimalRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmAnimalRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteMany(List<FarmAnimal> farmAnimals) {
        farmAnimalRepository.deleteAll(farmAnimals);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmAnimal> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmAnimalRepository.findByFarm(_farm, pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmAnimalRepository.findByOwnerAndFarm(owner, farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmAnimalRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmAnimalRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasRole('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmAnimalRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmAnimalRepository.findByOwnerAndFarm(owner, _farm).size();
    }
}
