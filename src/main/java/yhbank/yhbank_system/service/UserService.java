package yhbank.yhbank_system.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yhbank.yhbank_system.model.User;
import yhbank.yhbank_system.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; //마찬가지로 의존성 주입? + 원래는 객체를 생성해서 주입해줘야하는데 그걸 편하게 한게 bean 관리? 그걸 컨테이너에 한번만생성해서 관리하는 싱글톤패턴? 즉, 의존성주입 + 빈생성및주입자동화 + 싱글톤패턴이라는 개념. 컨벤셔널하게 개념을 명료화해줘.
    private final PasswordEncoder passwordEncoder;

    public void register(String fullName, String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setFullName(fullName);
        newUser.setEncodedPassword(encodedPassword);
        newUser.setRole("ROLE_USER"); // ✅ 권한 기본 설정


        userRepository.save(newUser);
    }

}
