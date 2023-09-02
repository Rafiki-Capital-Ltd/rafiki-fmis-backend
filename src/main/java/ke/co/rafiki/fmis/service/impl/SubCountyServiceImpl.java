package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Constituency;
import ke.co.rafiki.fmis.domain.County;
import ke.co.rafiki.fmis.domain.SubCounty;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.SubCountyRepository;
import ke.co.rafiki.fmis.service.SubCountyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static ke.co.rafiki.fmis.misc.HelperMethods.getPageRequest;

@Slf4j
@Service
public class SubCountyServiceImpl implements SubCountyService {

    private final SubCountyRepository subCountyRepository;

    public SubCountyServiceImpl(SubCountyRepository subCountyRepository) {
        this.subCountyRepository = subCountyRepository;
    }

    @Override
    public List<SubCounty> findAll() {
        return subCountyRepository.findAll();
    }

    @Override
    public Page<SubCounty> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = getPageRequest(page, size, sort, sortDirection);
        return subCountyRepository.findAll(pageRequest);
    }

    @Override
    public List<SubCounty> findAll(County county) {
        return subCountyRepository.findByCounty(county);
    }

    @Override
    public List<SubCounty> findAll(Constituency constituency) {
        return subCountyRepository.findByConstituency(constituency);
    }

    @Override
    public SubCounty findOne(Integer id) throws Exception {
        return subCountyRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Sub-county %d was not found", id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public SubCounty save(SubCounty subCounty) throws Exception {
        return subCountyRepository.save(subCounty);
    }

    @Override
    public SubCounty update(Integer id, SubCounty subCounty) throws Exception {
        SubCounty _subCounty = findOne(id);
        _subCounty.setName(subCounty.getName());
        _subCounty.setCounty(subCounty.getCounty());
        _subCounty.setConstituency(subCounty.getConstituency());
        return subCountyRepository.save(_subCounty);
    }

    @Override
    public void delete(Integer id) throws Exception {
        SubCounty subCounty = findOne(id);
        subCountyRepository.delete(subCounty);
    }

    @Override
    public void deleteMany(List<SubCounty> subCounties) {
        subCountyRepository.deleteAll(subCounties);
    }

    @Override
    public void deleteAll() {
        subCountyRepository.deleteAll();
    }
}
