package yhbank.yhbank_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.model.Transaction;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByAccountAndTimestampBetween( //트랜잭션 엔티티. 거래내역조회목적.그래서 페이지로찾겠다라는건가?
            Account account,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageble
    );

}
