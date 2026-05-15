package com.banco.digital.application.ports.output;

import com.banco.digital.domain.model.Account;
import com.banco.digital.domain.model.Transaction;

// Puerto de salida: contrato para envío de notificaciones
public interface NotificationPort {

    void notifyTransfer(Account source, Account destination, Transaction transaction);
}
