package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmSale;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmSaleRepository;
import ke.co.rafiki.fmis.service.FarmSaleService;
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
public class FarmSaleServiceImpl implements FarmSaleService {

    private final FarmSaleRepository farmSaleRepository;
    private final FarmService farmService;

    public  FarmSaleServiceImpl(
            FarmSaleRepository farmSalesRepository, FarmService farmService
    ) {
        this.farmSaleRepository = farmSalesRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmSale create(FarmSale farmSales) throws Exception {
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
}
