package black.orange.rutube.service;

import black.orange.rutube.entity.Role;
import black.orange.rutube.exception.EntityNotFoundException;
import black.orange.rutube.service.db.RoleDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RolesService {
    private final static String ENTITY_ROLE_CLASS_NAME = "Роль";
    private final static String DEFAULT_ROLE_NAME = "ROLE_USER";
    private final RoleDbService roleDbService;


    public static List<String> getRoleNames(List<Role> userRoles) {
        return userRoles.stream().map(Role::getName).toList();
    }

    public List<Role> getDefaultRoles() {
        Role roleUser = roleDbService.findByName(DEFAULT_ROLE_NAME).orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
        return List.of(roleUser);
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
