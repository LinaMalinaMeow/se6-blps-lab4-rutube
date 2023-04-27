package black.orange.rutube.service.db;

import black.orange.rutube.entity.Role;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoleDbService {
    private final String ENTITY_ROLE_CLASS_NAME = "Роль";
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));

    }
}
