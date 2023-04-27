package black.orange.rutube.service.db;

import black.orange.rutube.entity.User;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDbService {
    private final String ENTITY_CLASS_NAME = "Пользователь";
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
    }
}
