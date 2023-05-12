package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Farm;
import ke.co.rafiki.fmis.domain.FarmVca;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.FarmVcaRepository;
import ke.co.rafiki.fmis.service.FarmVcaService;
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
public class FarmVcaServiceImpl implements FarmVcaService {

    private final FarmVcaRepository farmVcaRepository;
    private final FarmService farmService;

    public FarmVcaServiceImpl(
            FarmVcaRepository farmVcaRepository, FarmService farmService
    ) {
        this.farmVcaRepository = farmVcaRepository;
        this.farmService = farmService;
    }



    @Override
    public FarmVca save(FarmVca farmVca) throws Exception {
        Farm farm = farmService.findOne(farmVca.getFarm().getId());
        farmVca.setFarm(farm);
        return farmVcaRepository.save(farmVca);
    }

    @Override
    public Page<FarmVca> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmVcaRepository.findAll(pageRequest);
    }

    @Override
    public FarmVca findOne(UUID id) throws Exception {
        return farmVcaRepository.findById(id).orElseThrow(() -> {
            String message = "Farm asset id " + id + " was not found.";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public FarmVca update(UUID id, FarmVca farmVca) throws Exception {
        FarmVca _farmVca = this.findOne(id);
        _farmVca.setType(farmVca.getType());
        _farmVca.setDescription(farmVca.getDescription());
        return farmVcaRepository.save(_farmVca);
    }

    @Override
    public void delete(UUID id) {
        farmVcaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        farmVcaRepository.deleteAll();
    }

    @Override
    public void deleteMany(List<FarmVca> farmVcas) {
        farmVcaRepository.deleteAll(farmVcas);
    }

    @Override
    public Page<FarmVca> findByFarm(
            Farm farm, int page, int size,
            String sort, String sortDirection
    ) throws Exception {
        Farm _farm = farmService.findOne(farm.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return farmVcaRepository.findByFarm(_farm, pageRequest);
    }
}
