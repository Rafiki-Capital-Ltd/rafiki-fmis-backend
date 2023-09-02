package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.CountyRepository;
import ke.co.rafiki.fmis.service.CountyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static ke.co.rafiki.fmis.misc.HelperMethods.getPageRequest;

@Slf4j
@Service
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;

    public CountyServiceImpl(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }

    @Override
    public List<County> findAll() {
        return countyRepository.findAll();
    }

    @Override
    public Page<County> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);
        return countyRepository.findAll(pageRequest);
    }

    @Override
    public County findOne(Integer id) throws Exception {
        return countyRepository.findById(id).orElseThrow(() -> {
            String message = String.format("County %d was not found", id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public County save(County county) throws Exception {
        return countyRepository.save(county);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public County update(Integer id, County county) throws Exception {
        County _county = findOne(id);
        _county.setName(county.getName());
        return countyRepository.save(_county);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(Integer id) throws Exception {
        County county = findOne(id);
        countyRepository.delete(county);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMany(List<County> counties) {
        countyRepository.deleteAll(counties);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        countyRepository.deleteAll();
    }
}
