package yhbank.yhbank_system.dto;

import lombok.Data;

//DTO는 model이랑비슷한거같은데model은 entity를모방한거라고한다면 dto는데이터형식의 타입일관성을위해 도입된 거같아 즉, 폼제출시의 데이터타입 검증이 목표이므로 필드만 제대로 입력되어있으면되지않을까?


@Data
public class AccountCreateRequest {  //요청 DTO 정의
    private String username;
    private String fullName;
    private String  accountNo;
    private String bankName;
}

