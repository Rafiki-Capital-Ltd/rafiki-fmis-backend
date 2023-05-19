package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmSaleRepository;
import ke.co.rafiki.fmis.service.FarmSaleService;
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

import static ke.co.rafiki.fmis.misc.HelperMethods.getAuthentication;
import static ke.co.rafiki.fmis.misc.HelperMethods.isAuthorized;

@Slf4j
@Service
public class FarmSaleServiceImpl implements FarmSaleService {

    private final FarmSaleRepository farmSaleRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmSaleServiceImpl(
            FarmSaleRepository farmSalesRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmSaleRepository = farmSalesRepository;
        this.farmService = farmService;
        this.userService = userService;
    }



    @Override
    public FarmSale save(FarmSale farmSales) throws Exception {
        Farm farm = farmService.findOne(farmSales.getFarm().getId());
        farmSales.setFarm(farm);
        return farmSaleRepository.save(farmSales);
    }

    @Override
    public Page<FarmSale> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmSaleRepository.findAll(pageRequest);
    }

    @Override
    public FarmSale findOne(UUID id) throws Exception {
        return farmSaleRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmSale update(UUID id, FarmSale farmSale) throws Exception {
        FarmSale _farmSale = this.findOne(id);
        _farmSale.setDate(farmSale.getDate());
        _farmSale.setType(farmSale.getType());
        _farmSale.setQuantity(farmSale.getQuantity());
        _farmSale.setAmount(farmSale.getAmount());
        _farmSale.setDescription(farmSale.getDescription());
        return farmSaleRepository.save(_farmSale);
    }

    @Override
    public void delete(UUID id) {
        farmSaleRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmSaleRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmSale> farmSales) {
        farmSaleRepository.deleteAll(farmSales);
    }

    @Override
    public Page<FarmSale> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmSaleRepository.findByFarm(_farm, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmSaleRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmSaleRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasRole('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmSaleRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmSaleRepository.findByOwnerAndFarm(owner, _farm).size();
    }
}
