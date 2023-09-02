package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.ConstituencyRepository;
import ke.co.rafiki.fmis.service.ConstituencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static ke.co.rafiki.fmis.misc.HelperMethods.getPageRequest;

@Slf4j
@Service
public class ConstituencyServiceImpl implements ConstituencyService {

    private final ConstituencyRepository constituencyRepository;

    public ConstituencyServiceImpl(ConstituencyRepository constituencyRepository) {
        this.constituencyRepository = constituencyRepository;
    }

    @Override
    public List<Constituency> findAll() {
        return constituencyRepository.findAll();
    }

    @Override
    public Page<Constituency> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);
        return constituencyRepository.findAll(pageRequest);
    }

    @Override
    public List<Constituency> findAll(County county) {
        return constituencyRepository.findByCounty(county);
    }

    @Override
    public Constituency findOne(Integer id) throws Exception {
        return constituencyRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Constituency %d was not found", id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Constituency save(Constituency constituency) throws Exception {
        return constituencyRepository.save(constituency);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Constituency update(Integer id, Constituency constituency) throws Exception {
        Constituency _constituency = findOne(id);
        _constituency.setName(constituency.getName());
        _constituency.setCounty(constituency.getCounty());
        return constituencyRepository.save(_constituency);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(Integer id) throws Exception {
        Constituency constituency = findOne(id);
        constituencyRepository.delete(constituency);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMany(List<Constituency> constituencies) {
        constituencyRepository.deleteAll(constituencies);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAll() {
        constituencyRepository.deleteAll();
    }
}
