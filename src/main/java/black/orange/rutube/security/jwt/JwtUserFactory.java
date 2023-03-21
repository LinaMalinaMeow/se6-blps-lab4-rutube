package black.orange.rutube.security.jwt;

import black.orange.rutube.entity.User;
import black.orange.rutube.service.RolesService;

public final class JwtUserFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getUpdated(),
                RolesService.mapToGrantedAuthorities(user.getRoles())
        );
    }
}
