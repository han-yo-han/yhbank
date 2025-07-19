package yhbank.yhbank_system;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import yhbank.yhbank_system.service.CustomUserDetailsService;

@Configuration  //config 빈 생성 어노테이션
@EnableWebSecurity  //웹시큐리티 어노테이션 기능은 잘모르겠음? 어떤거임? 컨벤셔널하게 1-2문장단위로대답할것.
@RequiredArgsConstructor  //lombok 생성자 자동화 어노테이션
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService; //userDetailsService라는 인터페이스 구체화

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ //여기서는 왜 일반예외를던지지? 특정예외로 구체화하지않는이유는? 이게 컨벤션이라? . 여기서 httpSecurity의 의미는? 어떤 보안 담당인가?
        //csrf가 뭐지? 이렇게 메소드체이닝때문에 filterchain이라고 한건가?
        //중간에 메서드 생략하면안되는게 직전메소드의반환객체를바탕으로메서드를실행하는거기때문에그런거잖아맞나?
        //csrf depecrated되었따는데 저것만삭제한다고되는문제가아니잖아? 이런경우의 대처 컨벤셔널하게 어떻게 처리하지?
        // 보니까 메소드채이닝방식이고 초기권한, 로그인할때,로그아웃할때 이렇게 경우 나눠서 체인이 세개있는거같은데? 그 체인을 1열로 나열한느낌이지. 병렬로하는건아니니깐. 채인이긴해 그리고 각 성격의 체인마다 지금 어떤 과정을 하고 있는건지 궁금해 리다이렉트도있고 쿠키를 지우는것도있고 가시성은좋은데 명료하게 컨벤셔널하게 정리할필요가있음.
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth->auth.requestMatchers("/admin/**").hasRole("ADMIN") // "ROLE_ADMIN"을 가진 사용자만 접근 가능
                        .requestMatchers("/login", "/logout","register", "/accounts/create").permitAll().anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/",true).failureHandler((req, res, ex) -> {
                            ex.printStackTrace(); // 예외 상세 출력
                            res.setStatus(HttpStatus.UNAUTHORIZED.value());
                            res.setContentType("application/json");
                            res.getWriter().write("{\"success\":false,\"message\":\"로그인 실패\"}");
                        })
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> {
                    session
                            .maximumSessions(1)
                            .expiredUrl("/login?expired=true");

                    session
                            .sessionFixation()
                            .migrateSession();
                });

        return http.build();

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception{
        // 이 부분 전체 예외는 던져버린다는거고 권한메니저역할인거같고 이거 provier한테 넘기는거아닌가? 그 부분이 나타났나?
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //이게 어떤방식이지? 어떻게 암호화하는거지? DES, AES, RSA, SHA-256 등 정보보호이론시간에 암호화 복호화 관련해서 들은바가있는데 이거 이론적 바탕 이해할필요있ㅇ므.
                                            //내가 정보보호이론시간에 배운 인크립/디크립션 요즘 암호 중요하잖아. 단순히 비밀번호 암호화 말고 확장할수있는거있으면 이론적배경->이부분에확장 이렇게 하면좋을듯
    }
}
