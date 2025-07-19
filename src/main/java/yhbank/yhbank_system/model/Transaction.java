package yhbank.yhbank_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yhbank.yhbank_system.TransactionType;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String memo;

    private String targetAccountNo; //송금 대상 계좌

    private int fee; //수수료

    @ManyToOne(fetch = FetchType.LAZY) //fetch가뭐지? 테이블간 일대다관계라는건알겠는데 근데 왜 Account만 연결하는거지? 이 어노테이션의 본질이뭐지?
    private Account account;


}
