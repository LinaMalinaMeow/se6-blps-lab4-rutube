package black.orange.rutube.validator;

import black.orange.rutube.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UserValidator implements ConstraintValidator<ValidUser, Long> {
    private final UserRepository userRepository;

    @Override
    public void initialize(ValidUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.existsById(userId);
    }
}
