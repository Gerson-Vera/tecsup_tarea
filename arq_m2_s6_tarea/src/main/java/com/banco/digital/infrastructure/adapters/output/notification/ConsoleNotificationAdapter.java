package com.banco.digital.infrastructure.adapters.output.notification;

import com.banco.digital.application.ports.output.NotificationPort;
import com.banco.digital.domain.model.Account;
import com.banco.digital.domain.model.Transaction;
import com.banco.digital.infrastructure.config.BankConfigurationManager;
import org.springframework.stereotype.Component;

/**
 * PATRÓN ADAPTER #3
 * Adapta NotificationPort al canal consola.
 * Para agregar email/SMS basta implementar NotificationPort en otro adaptador.
 */
@Component
public class ConsoleNotificationAdapter implements NotificationPort {

    @Override
    public void notifyTransfer(Account source, Account destination, Transaction transaction) {
        String bankName = BankConfigurationManager.getInstance().getBankName();
        System.out.println("\n========================================");
        System.out.println("  NOTIFICACIÓN - " + bankName);
        System.out.println("========================================");
        System.out.printf("  Transferencia COMPLETADA%n");
        System.out.printf("  ID Transacción : %s%n", transaction.getId());
        System.out.printf("  De             : %s%n", source.getOwnerName());
        System.out.printf("  Para           : %s%n", destination.getOwnerName());
        System.out.printf("  Monto          : S/ %.2f%n", transaction.getAmount());
        System.out.printf("  Comisión       : S/ %.2f%n", transaction.getCommission());
        System.out.printf("  Saldo restante : S/ %.2f%n", source.getBalance());
        System.out.println("========================================\n");
    }
}
