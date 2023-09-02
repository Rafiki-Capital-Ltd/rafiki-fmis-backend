package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmPurchase;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmPurchaseRepository;
import ke.co.rafiki.fmis.service.FarmPurchaseService;
import ke.co.rafiki.fmis.service.FarmService;
import ke.co.rafiki.fmis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ke.co.rafiki.fmis.misc.HelperMethods.*;

@Slf4j
@Service
public class FarmPurchaseServiceImpl implements FarmPurchaseService {

    private final FarmPurchaseRepository farmPurchaseRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmPurchaseServiceImpl(
            FarmPurchaseRepository farmPurchasesRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmPurchaseRepository = farmPurchasesRepository;
        this.farmService = farmService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmPurchase> findAll(int page, int size,
                                      String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);

        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmPurchaseRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmPurchase> findAll(Farm farm, int page, int size,
                                      String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findAll(pageRequest);

        Farm _farm = farmService.findOne(farm.getId());
        User owner = userService.findOne(getAuthentication().getName());
        return farmPurchaseRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'FARMER')")
    public FarmPurchase findOne(UUID id) throws Exception {
        return farmPurchaseRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmPurchase save(FarmPurchase farmPurchases) throws Exception {
        Farm farm = farmService.findOne(farmPurchases.getFarm().getId());
        User owner = userService.findOne(getAuthentication().getName());
        farmPurchases.setFarm(farm);
        farmPurchases.setOwner(owner);
        return farmPurchaseRepository.save(farmPurchases);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmPurchase', 'MANAGER')")
    public FarmPurchase update(UUID id, FarmPurchase farmPurchase) throws Exception {
        FarmPurchase _farmPurchase = this.findOne(id);
        _farmPurchase.setDate(farmPurchase.getDate());
        _farmPurchase.setType(farmPurchase.getType());
        _farmPurchase.setQuantity(farmPurchase.getQuantity());
        _farmPurchase.setAmount(farmPurchase.getAmount());
        _farmPurchase.setDescription(farmPurchase.getDescription());
        return farmPurchaseRepository.save(_farmPurchase);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmPurchase', 'MANAGER')")
    public void delete(UUID id) {
        farmPurchaseRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmPurchaseRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public void deleteMany(List<FarmPurchase> farmPurchases) {
        farmPurchaseRepository.deleteAll(farmPurchases);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmPurchaseRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmPurchaseRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        return farmPurchaseRepository.findTotal(owner).orElse(BigDecimal.ZERO);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmPurchaseRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmPurchaseRepository.findTotal(owner, _farm).orElse(BigDecimal.ZERO);
    }
}
