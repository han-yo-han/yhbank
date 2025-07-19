package yhbank.yhbank_system.service;

import yhbank.yhbank_system.model.User;
import yhbank.yhbank_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 아이디/비밀번호 검증 후 User 반환
     */
    public User authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                );

        if (!passwordEncoder.matches(rawPassword, user.getEncodedPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
}
