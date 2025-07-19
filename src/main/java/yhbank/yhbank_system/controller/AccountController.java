package yhbank.yhbank_system.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yhbank.yhbank_system.CustomUserDetails;
import yhbank.yhbank_system.dto.AccountCreateRequest;
import yhbank.yhbank_system.dto.ApiResponse;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.model.User;
import yhbank.yhbank_system.service.AccountService;

import java.util.Optional;

@Controller //그냥 Controller과의 차이점은?
@RequestMapping("/accounts") //getMapping이나 PostMapping을 하지않았는데 이유는? 그것과의차이는? , 메서드가 아닌 클래스 위에 mapping이 의미하는건? 중첩구조를의미하나?
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService; //의존성 주입위해서 autowired로 등록된 빈인스턴스간 연결하는것 맞지?

/*    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody AccountCreateRequest req){
        Account account = accountService.createAccount(
                req.getUsername(),
                req.getPassword(),
                req.getFullName(),
                req.getAccountNo(),
                req.getBankName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("계좌 생성 성공", account));
    }*/

    @PostMapping("/create")
    public String createAccount(@ModelAttribute AccountCreateRequest req, Model model) {
        Account account = accountService.createAccount(
                req.getUsername() , req.getFullName(), req.getAccountNo(), req.getBankName()
        );
        model.addAttribute("account", account);
        return "accounts/info";
    }

    @GetMapping("/create")
    public String showCreateAccountForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "accounts/create";
    }



    @GetMapping("/form")
    public String accountForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        Optional<Account> optionalAccount = accountService.getAccountByUser(user);

        if (optionalAccount.isPresent()) {
            model.addAttribute("account", optionalAccount.get());
            return "accounts/info"; // 계좌 보유 중
        } else {
            model.addAttribute("user", user);
            return "accounts/create"; // 계좌 생성 폼
        }
    }


}
