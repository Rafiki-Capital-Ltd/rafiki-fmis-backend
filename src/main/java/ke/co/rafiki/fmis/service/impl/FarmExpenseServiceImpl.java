package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmExpense;
import ke.co.rafiki.fmis.domain.User;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmExpenseRepository;
import ke.co.rafiki.fmis.service.FarmExpenseService;
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
public class FarmExpenseServiceImpl implements FarmExpenseService {

    private final FarmExpenseRepository farmExpenseRepository;
    private final FarmService farmService;
    private final UserService userService;

    public  FarmExpenseServiceImpl(
            FarmExpenseRepository farmExpensesRepository,
            FarmService farmService,
            UserService userService
    ) {
        this.farmExpenseRepository = farmExpensesRepository;
        this.farmService = farmService;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmExpense> findAll(int page, int size,
                                      String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findAll(pageRequest);

        User owner = userService.findOne(getAuthentication().getName());
        return farmExpenseRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public Page<FarmExpense> findAll(Farm farm, int page, int size,
                                      String sort, String sortDirection) throws Exception {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);

        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findAll(pageRequest);

        Farm _farm = farmService.findOne(farm.getId());
        User owner = userService.findOne(getAuthentication().getName());
        return farmExpenseRepository.findByOwner(owner, pageRequest);
    }

    @Override
    @PostAuthorize("hasPermission(returnObject, 'FARMER')")
    public FarmExpense findOne(UUID id) throws Exception {
        return farmExpenseRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public FarmExpense save(FarmExpense farmExpenses) throws Exception {
        Farm farm = farmService.findOne(farmExpenses.getFarm().getId());
        User owner = userService.findOne(getAuthentication().getName());
        farmExpenses.setFarm(farm);
        farmExpenses.setOwner(owner);
        return farmExpenseRepository.save(farmExpenses);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmExpense', 'MANAGER')")
    public FarmExpense update(UUID id, FarmExpense farmExpense) throws Exception {
        FarmExpense _farmExpense = this.findOne(id);
        _farmExpense.setDate(farmExpense.getDate());
        _farmExpense.setType(farmExpense.getType());
        _farmExpense.setAmount(farmExpense.getAmount());
        _farmExpense.setDescription(farmExpense.getDescription());
        return farmExpenseRepository.save(_farmExpense);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'FarmExpense', 'MANAGER')")
    public void delete(UUID id) {
        farmExpenseRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        farmExpenseRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public void deleteMany(List<FarmExpense> farmExpenses) {
        farmExpenseRepository.deleteAll(farmExpenses);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        return farmExpenseRepository.findByOwner(owner).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public long getCount(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findAll().size();

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmExpenseRepository.findByOwnerAndFarm(owner, _farm).size();
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal() throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        return farmExpenseRepository.findTotal(owner).orElse(BigDecimal.ZERO);
    }

    @Override
    @PreAuthorize("hasAuthority('FARMER')")
    public BigDecimal getTotal(Farm farm) throws Exception {
        if (isAuthorized(RoleType.MANAGER))
            return farmExpenseRepository.findTotal().orElse(BigDecimal.ZERO);

        User owner = userService.findOne(getAuthentication().getName());
        Farm _farm = farmService.findOne(farm.getId());
        return farmExpenseRepository.findTotal(owner, _farm).orElse(BigDecimal.ZERO);
    }
}
