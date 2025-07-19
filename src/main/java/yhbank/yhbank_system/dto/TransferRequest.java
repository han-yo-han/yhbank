package yhbank.yhbank_system.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromAccountNo;
    private String toAccountNo;
    private Long amount;
    private String memo;
    private boolean sameBank; // 같은 은행인지 여부 (수수료 판단)
}
