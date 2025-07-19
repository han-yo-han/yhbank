package yhbank.yhbank_system.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private String accountNo;
    private Long amount;
    private String memo;
}