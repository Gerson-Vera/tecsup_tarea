# Diagrama de Arquitectura Hexagonal — Banco Digital

```
                    ┌─────────────────────────────────────────────────────┐
                    │              INFRAESTRUCTURA                         │
                    │                                                       │
  HTTP Request ───► │  ┌──────────────────┐                               │
                    │  │ AccountController │  (Adaptador Entrada #1: REST) │
                    │  └────────┬─────────┘                               │
                    │           │ llama                                    │
                    └───────────┼──────────────────────────────────────────┘
                                │
                    ┌───────────▼──────────────────────────────────────────┐
                    │              APLICACIÓN                               │
                    │                                                       │
                    │  Puerto Entrada (interfaces):                         │
                    │  ┌────────────────────┐                              │
                    │  │ CreateAccountUseCase│                              │
                    │  │ TransferMoneyUseCase│                              │
                    │  │ GetBalanceUseCase  │                              │
                    │  └────────┬───────────┘                              │
                    │           │ implementado por                          │
                    │  ┌────────▼───────────┐                              │
                    │  │ CreateAccountService│                              │
                    │  │ TransferMoneyService│  (Lógica de negocio)        │
                    │  │ GetBalanceService  │                              │
                    │  └────────┬───────────┘                              │
                    │           │ usa                                       │
                    │  Puerto Salida (interfaces):                          │
                    │  ┌────────────────────┐                              │
                    │  │ AccountRepositoryPort                              │
                    │  │ TransactionRepPort │                              │
                    │  │ NotificationPort   │                              │
                    │  └────────┬───────────┘                              │
                    └───────────┼──────────────────────────────────────────┘
                                │
                    ┌───────────▼──────────────────────────────────────────┐
                    │              INFRAESTRUCTURA                          │
                    │                                                       │
                    │  ┌──────────────────────────┐                        │
                    │  │ H2AccountRepositoryAdapter│ (Adaptador Salida #1) │
                    │  │ H2TransactionRepAdapter  │ (Adaptador Salida #2) │
                    │  └──────────┬───────────────┘                        │
                    │             │                                         │
                    │  ┌──────────▼───────────────┐                        │
                    │  │    Base de Datos H2        │                        │
                    │  └──────────────────────────┘                        │
                    │                                                       │
                    │  ┌──────────────────────────┐                        │
                    │  │ ConsoleNotificationAdapter│ (Adaptador Salida #3) │
                    │  └──────────────────────────┘                        │
                    │                                                       │
                    │  ┌──────────────────────────┐                        │
                    │  │  BankConfigurationManager │ (Patrón Singleton)    │
                    │  └──────────────────────────┘                        │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │              DOMINIO (centro del hexágono)            │
                    │                                                       │
                    │   Account          Transaction                        │
                    │   AccountStatus    TransactionStatus                  │
                    │   InsufficientFundsException                          │
                    │   AccountNotFoundException                             │
                    │                                                       │
                    │   ► Sin dependencias externas                         │
                    │   ► Lógica de negocio pura (debit, credit)           │
                    └──────────────────────────────────────────────────────┘
```

## Flujo de una Transferencia

```
1. POST /api/transfers  ──►  AccountController
2. AccountController    ──►  TransferMoneyUseCase.transfer()
3. TransferMoneyService ──►  AccountRepositoryPort.findById() x2
4. Account.debit()              (lógica de dominio valida fondos)
5. Account.credit()
6. AccountRepositoryPort.save() x2  ──►  H2AccountRepositoryAdapter ──►  H2
7. TransactionRepositoryPort.save() ──►  H2TransactionRepositoryAdapter ──► H2
8. NotificationPort.notifyTransfer() ──►  ConsoleNotificationAdapter ──► consola
```

## Patrones Implementados

| Patrón | Clase | Ubicación |
|--------|-------|-----------|
| **Singleton** | `BankConfigurationManager` | `infrastructure/config` |
| **Adapter #1** | `H2AccountRepositoryAdapter` | `infrastructure/adapters/output/persistence` |
| **Adapter #2** | `H2TransactionRepositoryAdapter` | `infrastructure/adapters/output/persistence` |
| **Adapter #3** | `ConsoleNotificationAdapter` | `infrastructure/adapters/output/notification` |
| **Adapter #4** | `AccountController` (input) | `infrastructure/adapters/input/rest` |
