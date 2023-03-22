package black.orange.rutube.service;

import black.orange.rutube.converter.UserConverter;
import black.orange.rutube.dto.UserDto;
import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.User;
import black.orange.rutube.exception.auth.EntityAlreadyExistsException;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.exception.auth.WrongAuthException;
import black.orange.rutube.repository.UserRepository;
import black.orange.rutube.security.jwt.JwtTokenProvider;
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
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserConverter userConverter;
    private final RolesService rolesService;

    public String auth(UserDto authUser) throws AuthException {
        User userFromDataBase = findUserByEmail(authUser.getEmail());

        if (passwordEncoder.matches(authUser.getPassword(), userFromDataBase.getPassword())) {
            return jwtTokenProvider.createToken(authUser.getEmail(), userFromDataBase.getRoles());
        }

        throw new WrongAuthException();
    }

    public String register(UserDto authUser) {
        if (userRepository.existsByEmail(authUser.getEmail())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        List<Role> userRoles = rolesService.getDefaultRoles();

        User user = userConverter.toEntity(authUser, userRoles);

        userRepository.save(user);

        return jwtTokenProvider.createToken(authUser.getEmail(), userRoles);
    }


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
    }

    public Long getUserIdFromContext() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(userName).getId();
    }

}
