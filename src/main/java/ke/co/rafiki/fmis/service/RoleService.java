package ke.co.rafiki.fmis.service;

import ke.co.rafiki.fmis.domain.Role;
import ke.co.rafiki.fmis.domain.RoleType;
import ke.co.rafiki.fmis.exceptions.BadRequestException;
import ke.co.rafiki.fmis.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RoleService {
    Page<Role> findAll(int page, int size, String sort, String sortDirection);
    Role findOne(UUID id) throws NotFoundException;
    Role findOne(RoleType type) throws NotFoundException;
    Role save(Role role) throws BadRequestException;
    Role update(UUID id, Role role) throws NotFoundException;
    void delete(UUID id);
}
