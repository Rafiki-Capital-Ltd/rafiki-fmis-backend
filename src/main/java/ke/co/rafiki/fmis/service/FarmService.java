package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm create(Farm farm) throws Exception;
    Page<Farm> findAll(int page, int size, String sort, String sortDirection);
    Farm findOne(UUID id) throws Exception;
    Farm update(UUID id, Farm farm) throws Exception;
    void delete(UUID id);
    void deleteMany(List<Farm> farms);
    void deleteAll();
    FarmActivityLog findFarmDiary(Farm farm) throws Exception;
    Page<Farm> findByFarmer(
            Principal farmer, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmActivity> findActivities(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmAsset> findAssets(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmConsumption> findConsumptions(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmProduction> findProductions(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmSale> findSales(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    Page<FarmVca> findValueChainAdditions(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
}
