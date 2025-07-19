package yhbank.yhbank_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yhbank.yhbank_system.CustomUserDetails;
import yhbank.yhbank_system.model.User;
import yhbank.yhbank_system.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; //의존성주입 lombok+required 조합으로 완성함. autowired는 인스턴스끼리연결해주는건데 이건 그방식이아니라 생성자생성하는방식?그렇게햇다.자세히잘모르겠다의미명료화필요

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //메서드에서 throw할때는 반드시 함수선언부에도 적어줘야한다.
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        return new CustomUserDetails(user);
    }

}
