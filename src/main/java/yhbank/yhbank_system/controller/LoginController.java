package yhbank.yhbank_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import yhbank.yhbank_system.dto.UserRegisterRequest;
import yhbank.yhbank_system.service.UserService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html
    }

    @PostMapping("/register")
    public String register(UserRegisterRequest request) {
        userService.register(request.getFullName(),request.getUsername(),request.getPassword());
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 이동
    }
}
