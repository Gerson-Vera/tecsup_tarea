package com.banco.digital.infrastructure.config;

import com.banco.digital.application.ports.output.AccountRepositoryPort;
import com.banco.digital.application.ports.output.NotificationPort;
import com.banco.digital.application.ports.output.TransactionRepositoryPort;
import com.banco.digital.application.usecases.CreateAccountService;
import com.banco.digital.application.usecases.GetAccountBalanceService;
import com.banco.digital.application.usecases.TransferMoneyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Punto de ensamblaje de la arquitectura hexagonal.
 * Los servicios de aplicación (sin anotaciones Spring) se conectan aquí
 * con los adaptadores de infraestructura.
 */
@Configuration
public class BeanConfiguration {

    // Registra el Singleton como bean de Spring
    @Bean
    public BankConfigurationManager bankConfigurationManager() {
        return BankConfigurationManager.getInstance();
    }

    @Bean
    public CreateAccountService createAccountService(AccountRepositoryPort accountRepositoryPort) {
        return new CreateAccountService(accountRepositoryPort);
    }

    @Bean
    public GetAccountBalanceService getAccountBalanceService(AccountRepositoryPort accountRepositoryPort) {
        return new GetAccountBalanceService(accountRepositoryPort);
    }

    @Bean
    public TransferMoneyService transferMoneyService(AccountRepositoryPort accountRepositoryPort,
                                                     TransactionRepositoryPort transactionRepositoryPort,
                                                     NotificationPort notificationPort,
                                                     BankConfigurationManager bankConfigurationManager) {
        return new TransferMoneyService(
                accountRepositoryPort,
                transactionRepositoryPort,
                notificationPort,
                bankConfigurationManager.getCommissionFee()
        );
    }
}
