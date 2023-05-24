package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmAnimal;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FarmAnimalService {
    FarmAnimal save(FarmAnimal farmAnimal) throws Exception;
    Page<FarmAnimal> findAll(int page, int size,
                            String sort, String sortDirection) throws Exception;
    Page<FarmAnimal> findAll(Farm farm, int page, int size,
                            String sort, String sortDirection) throws Exception;
    FarmAnimal findOne(UUID id) throws Exception;
    FarmAnimal update(UUID id, FarmAnimal farmAnimal) throws Exception;
    void delete(UUID id);
    void deleteAll();
    void deleteMany(List<FarmAnimal> farmAnimals);
    Page<FarmAnimal> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception;
    long getCount() throws Exception;
    long getCount(Farm farm) throws Exception;
    long getTotal() throws Exception;
    long getTotal(Farm farm) throws Exception;
}
