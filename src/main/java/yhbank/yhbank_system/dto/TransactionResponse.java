package yhbank.yhbank_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private LocalDateTime timestamp;
    private String type;
    private Long amount;
    private String memo;
    private String targetAccountNo;
    private int fee;
}
