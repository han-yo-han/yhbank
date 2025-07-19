package yhbank.yhbank_system.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yhbank.yhbank_system.CustomUserDetails;
import yhbank.yhbank_system.model.User;



@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "users/dashboard"; // templates/dashboard.html
    }

}
