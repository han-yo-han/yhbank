package yhbank.yhbank_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String fullName;
    private String username;
    private String password;
}
