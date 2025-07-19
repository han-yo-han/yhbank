package yhbank.yhbank_system.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yhbank.yhbank_system.TransactionType;
import yhbank.yhbank_system.model.Account;
import yhbank.yhbank_system.model.Transaction;
import yhbank.yhbank_system.repository.AccountRepository;
import yhbank.yhbank_system.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final LocalTime BLOCK_START = LocalTime.of(12,0); //이게 점검기간을 의미
    private final LocalTime BLOCK_END = LocalTime.of(12,30);

    public Transaction deposit(String accountNo, Long amount, String memo){
        if(amount<=0){
            throw new IllegalArgumentException("예금 금액은 0원보다 커야 합니다");
        }

    Account account = accountRepository.findByAccountNo(accountNo).orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

    account.setBalance(account.getBalance() + amount);
    accountRepository.save(account);

    Transaction tx = new Transaction(null, LocalDateTime.now(), amount, TransactionType.DEPOSIT, memo, null, 0, account);

    return transactionRepository.save(tx); //단순 필드저장이아니라 transaction을 저장한다 말 맞네 이건 트랜잭션레포지토리니깐.

    }

    public Transaction withdraw(String accountNo, Long amount, String memo){
        LocalTime now = LocalTime.now();
        //비즈니스 정책을 앞에 넣어 예외던지는 모습
        if (now.isAfter(BLOCK_START) && now.isBefore(BLOCK_END)) {
            throw new IllegalStateException("12:00~12:30에는 출금할 수 없습니다.");
        }

        if (amount > 1_000_000) {
            throw new IllegalArgumentException("출금은 최대 100만원까지 가능합니다.");
        }

        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        if (account.getBalance() < amount) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction tx = new Transaction(null, LocalDateTime.now(), amount,
                TransactionType.WITHDRAWAL, memo, null, 0, account);
        return transactionRepository.save(tx);
    }

    public Transaction transfer(String fromAccountNo, String toAccountNo, Long amount, String memo, boolean isSameBank) {
        LocalTime now = LocalTime.now();
        if (now.isAfter(BLOCK_START) && now.isBefore(BLOCK_END)) {
            throw new IllegalStateException("12:00~12:30에는 송금할 수 없습니다.");
        }

        int fee = isSameBank ? 0 : 500;

        Account from = accountRepository.findByAccountNo(fromAccountNo)
                .orElseThrow(() -> new IllegalArgumentException("송금 출금 계좌 없음"));

        Account to = accountRepository.findByAccountNo(toAccountNo)
                .orElseThrow(() -> new IllegalArgumentException("송금 입금 계좌 없음"));

        long total = amount + fee;
        if (from.getBalance() < total) {
            throw new IllegalStateException("잔액 부족 (수수료 포함)");
        }

        from.setBalance(from.getBalance() - total);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction tx = new Transaction(null, LocalDateTime.now(), amount,
                TransactionType.TRANSFER, memo, toAccountNo, fee, from);
        return transactionRepository.save(tx);
    }

    public Page<Transaction> getTransactionHistory(String accountNo, LocalDate from, LocalDate to, Pageable pageable) {
        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        if (from.isBefore(LocalDate.now().minusMonths(6))) {
            throw new IllegalArgumentException("6개월 이내의 기록만 조회 가능합니다.");
        }

        return transactionRepository.findByAccountAndTimestampBetween(
                account,
                from.atStartOfDay(),
                to.atTime(23, 59, 59),
                pageable
        );
    }
}