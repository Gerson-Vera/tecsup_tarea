package com.banco.digital.infrastructure.adapters.input.rest;

import com.banco.digital.application.ports.input.CreateAccountUseCase;
import com.banco.digital.application.ports.input.GetAccountBalanceUseCase;
import com.banco.digital.application.ports.input.TransferMoneyUseCase;
import com.banco.digital.domain.exception.AccountNotFoundException;
import com.banco.digital.domain.exception.InsufficientFundsException;
import com.banco.digital.infrastructure.adapters.input.rest.dto.AccountResponse;
import com.banco.digital.infrastructure.adapters.input.rest.dto.CreateAccountRequest;
import com.banco.digital.infrastructure.adapters.input.rest.dto.TransactionResponse;
import com.banco.digital.infrastructure.adapters.input.rest.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ADAPTADOR DE ENTRADA — REST API
 * Traduce peticiones HTTP a llamadas a los puertos de entrada (casos de uso).
 * No contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountBalanceUseCase getAccountBalanceUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase,
                             TransferMoneyUseCase transferMoneyUseCase,
                             GetAccountBalanceUseCase getAccountBalanceUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.transferMoneyUseCase = transferMoneyUseCase;
        this.getAccountBalanceUseCase = getAccountBalanceUseCase;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        var account = createAccountUseCase.createAccount(request.getOwnerName(), request.getInitialBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(account));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        var accounts = getAccountBalanceUseCase.getAllAccounts().stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String id) {
        var account = getAccountBalanceUseCase.getAccount(id);
        return ResponseEntity.ok(AccountResponse.from(account));
    }

    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<Map<String, BigDecimal>> getBalance(@PathVariable String id) {
        BigDecimal balance = getAccountBalanceUseCase.getBalance(id);
        return ResponseEntity.ok(Map.of("balance", balance));
    }

    @PostMapping("/transfers")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {
        var transaction = transferMoneyUseCase.transfer(
                request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(transaction));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
