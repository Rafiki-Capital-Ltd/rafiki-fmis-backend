package ke.co.rafiki.fmis.service.impl;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.enums.RoleType;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import ke.co.rafiki.fmis.repository.RoleRepository;
import ke.co.rafiki.fmis.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return roleRepository.findAll(pageRequest);
    }

    @Override
    public Role findOne(UUID id) throws NotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Role id %s was not found", id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public Role findOne(RoleType type) throws NotFoundException {
        return roleRepository.findByType(type).orElseThrow(() -> {
            String message = String.format("Role type %s was not found.", type);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public Role save(Role role) throws BadRequestException {
        Optional<Role> existingRole = roleRepository.findByType(role.getType());
        if (existingRole.isPresent()) {
            String message = String.format("Role type %s already exists.", role.getType());
            log.error(message);
            throw new BadRequestException(message);
        }
        return roleRepository.save(role);
    }

    @Override
    public Role update(UUID id, Role role) throws NotFoundException {
        Role existingRole = this.findOne(id);
        existingRole.setType(role.getType());
        return roleRepository.save(existingRole);
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }
}
