package black.orange.rutube.service;

import black.orange.rutube.converter.UserConverter;
import black.orange.rutube.dto.UserDto;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import black.orange.rutube.exception.auth.EntityAlreadyExistsException;
import black.orange.rutube.exception.auth.WrongAuthException;
import black.orange.rutube.security.jwt.JwtTokenProvider;
import black.orange.rutube.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final String ENTITY_CLASS_NAME = "Пользователь";
    private final UserDbService userDbService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserConverter userConverter;
    private final RolesService rolesService;

    public String auth(UserDto authUser) throws AuthException {
        User userFromDataBase = userDbService.findUserByEmail(authUser.getEmail());

        if (passwordEncoder.matches(authUser.getPassword(), userFromDataBase.getPassword())) {
            return jwtTokenProvider.createToken(authUser.getEmail(), userFromDataBase.getRoles());
        }

        throw new WrongAuthException();
    }

    public String register(UserDto authUser) {
        if (userDbService.existsByEmail(authUser.getEmail())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        List<Role> userRoles = rolesService.getDefaultRoles();

        User user = userConverter.toEntity(authUser, userRoles);

        userDbService.create(user);

        return jwtTokenProvider.createToken(authUser.getEmail(), userRoles);
    }


    public Long getUserIdFromContext() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDbService.findUserByEmail(userName).getId();
    }

    public String getUserEmail(Long userId) {
       return userDbService.findUserById(userId).getEmail();
    }
}
