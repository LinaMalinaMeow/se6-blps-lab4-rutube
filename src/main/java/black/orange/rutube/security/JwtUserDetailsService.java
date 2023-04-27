package black.orange.rutube.security;

import black.orange.rutube.entity.User;
import black.orange.rutube.security.jwt.JwtUserFactory;
import black.orange.rutube.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDbService userDbService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userDbService.findUserByEmail(email);

        return JwtUserFactory.create(user);
    }
}
