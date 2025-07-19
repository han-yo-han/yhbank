package yhbank.yhbank_system.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yhbank.yhbank_system.CustomUserDetails;
import yhbank.yhbank_system.dto.LoginRequest;
import yhbank.yhbank_system.dto.TransactionRequest;
import yhbank.yhbank_system.dto.TransferRequest;
import yhbank.yhbank_system.dto.UserRegisterRequest;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.model.User;
import yhbank.yhbank_system.service.AccountService;
import yhbank.yhbank_system.service.LoginService;
import yhbank.yhbank_system.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/register")
    public String registerForm(Model model){
        return "register";
    }



    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
