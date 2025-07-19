package yhbank.yhbank_system.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yhbank.yhbank_system.dto.ApiResponse;
import yhbank.yhbank_system.dto.TransactionRequest;
import yhbank.yhbank_system.dto.TransactionResponse;
import yhbank.yhbank_system.dto.TransferRequest;
import yhbank.yhbank_system.model.Transaction;
import yhbank.yhbank_system.service.TransactionService;

import java.time.LocalDate;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @GetMapping("/")
    public String transactionPage() {
        return "transaction/transaction"; // templates/transaction.html
    }



    @PostMapping("/deposit")
    @ResponseBody
    public ResponseEntity<ApiResponse<TransactionResponse>> deposit(@RequestBody TransactionRequest request) {
        var tx = transactionService.deposit(request.getAccountNo(), request.getAmount(), request.getMemo());
        return ResponseEntity.ok(ApiResponse.success("입금 완료", toResponse(tx)));
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<ApiResponse<TransactionResponse>> withdraw(@RequestBody TransactionRequest request) {
        var tx = transactionService.withdraw(request.getAccountNo(), request.getAmount(), request.getMemo());
        return ResponseEntity.ok(ApiResponse.success("출금 완료", toResponse(tx)));
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(@RequestBody TransferRequest request) {
        var tx = transactionService.transfer(
                request.getFromAccountNo(),
                request.getToAccountNo(),
                request.getAmount(),
                request.getMemo(),
                request.isSameBank()
        );
        return ResponseEntity.ok(ApiResponse.success("송금 완료", toResponse(tx)));
    }

    @GetMapping("/history")
    @ResponseBody
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getHistory(
            @RequestParam("accountNo") String accountNo,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        var page = transactionService.getTransactionHistory(accountNo, from, to, pageable)
                .map(this::toResponse);
        return ResponseEntity.ok(ApiResponse.success("거래내역 조회 성공", page));
    }



    @GetMapping("/deposit-form")
    public String depositForm(Model model) {
        model.addAttribute("request", new TransactionRequest());
        return "transactions/deposit-form";
    }

    @GetMapping("/withdraw-form")
    public String withdrawForm(Model model) {
        model.addAttribute("request", new TransactionRequest());
        return "transactions/withdraw-form";
    }

    @GetMapping("/transfer-form")
    public String transferForm(Model model) {
        model.addAttribute("request", new TransferRequest());
        return "transactions/transfer-form";
    }

    @GetMapping("/history-form")
    public String historyForm() {
        return "transactions/history-form";
    }

    @GetMapping("/form")
    public String transactionForm() {
        return "transactions/form";
    }



    private TransactionResponse toResponse(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getTimestamp(),
                tx.getType().name(),
                tx.getAmount(),
                tx.getMemo(),
                tx.getTargetAccountNo(),
                tx.getFee()
        );
    }
}

