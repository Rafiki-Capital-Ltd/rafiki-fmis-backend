package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmCrop;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmCropService {
    FarmCrop save(FarmCrop farmCrop) throws Exception;
    Page<FarmCrop> findAll(int page, int size,
                             String sort, String sortDirection) throws Exception;
    Page<FarmCrop> findAll(Farm farm, int page, int size,
                           String sort, String sortDirection) throws Exception;
    FarmCrop findOne(UUID id) throws Exception;
    FarmCrop update(UUID id, FarmCrop farmCrop) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmCrop> farmCrops);
    Page<FarmCrop> findByFarm(Farm farm, int page, int size,
                              String sort, String sortDirection) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    long getTotal() throws Exception;
    long getTotal(Farm farm) throws Exception;
}
