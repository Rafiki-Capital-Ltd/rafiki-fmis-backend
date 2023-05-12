package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.*;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm create(Farm farm);
    Page<Farm> findAll(int page, int size, String sort, String sortDirection);
    Farm findOne(UUID id) throws NotFoundException;
    Farm update(UUID id, Farm farm) throws NotFoundException;
    void delete(UUID id);
    void deleteMany(List<Farm> farms);
    void deleteAll();
    FarmDiary findFarmDiary(Farm farm) throws NotFoundException;
    Page<Farm> findByFarmer(Principal farmer, int page, int size, String sort, String sortDirection);
    Page<FarmActivity> findActivities(Farm farm, int page, int size, String sort, String sortDirection);
    Page<FarmAsset> findAssets(Farm farm, int page, int size, String sort, String sortDirection);
    Page<FarmConsumption> findConsumptions(Farm farm, int page, int size, String sort, String sortDirection);
    List<FarmProduction> findPRoductions(Farm farm, int page, int size, String sort, String sortDirection);
    List<FarmSale> findSales(Farm farm, int page, int size, String sort, String sortDirection);
    List<FarmValueChainAddition> findValueChainAdditions(Farm farm, int page, int size, String sort, String sortDirection);
}
