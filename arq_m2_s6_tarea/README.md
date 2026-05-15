# Banco Digital — Arquitectura Hexagonal

Sistema de gestión bancaria que implementa la Arquitectura Hexagonal (Ports & Adapters).

---

## Requisitos

- Java 17
- Maven 3.8+

---

## Ejecución

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080/api`  
Consola H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:bancodb`)

---

## Endpoints

### Crear cuenta
```bash
POST /api/accounts
Content-Type: application/json

{
  "ownerName": "Juan Pérez",
  "initialBalance": 1000.00
}
```

### Consultar cuenta
```bash
GET /api/accounts/{id}
GET /api/accounts/{id}/balance
GET /api/accounts
```

### Transferir dinero
```bash
POST /api/transfers
Content-Type: application/json

{
  "sourceAccountId": "...",
  "destinationAccountId": "...",
  "amount": 200.00,
  "description": "Pago de servicios"
}
```

> Nota: cada transferencia aplica una comisión de S/ 5.00 configurada en `BankConfigurationManager`.

---

## Estructura del Proyecto

```
src/
├── domain/                     # Sin dependencias externas
│   ├── model/                  # Account, Transaction, enums
│   └── exception/              # Excepciones de negocio
├── application/
│   ├── ports/
│   │   ├── input/              # Interfaces de casos de uso
│   │   └── output/             # Interfaces de repositorios y notificación
│   └── usecases/               # Lógica de negocio (sin Spring)
└── infrastructure/
    ├── adapters/
    │   ├── input/rest/         # Adaptador REST (AccountController)
    │   └── output/
    │       ├── persistence/    # Adaptadores H2/JPA
    │       └── notification/   # Adaptador consola
    └── config/                 # BankConfigurationManager (Singleton) + BeanConfiguration
```

---

## Patrones de Diseño

### Patrón ADAPTER (4 implementaciones)

| Adaptador | Tipo | Puerto que implementa |
|-----------|------|----------------------|
| `AccountController` | Entrada | Invoca `CreateAccountUseCase`, `TransferMoneyUseCase`, `GetAccountBalanceUseCase` |
| `H2AccountRepositoryAdapter` | Salida | `AccountRepositoryPort` |
| `H2TransactionRepositoryAdapter` | Salida | `TransactionRepositoryPort` |
| `ConsoleNotificationAdapter` | Salida | `NotificationPort` |

Cada adaptador puede ser reemplazado de forma independiente sin modificar el dominio ni la aplicación. Por ejemplo, para agregar notificaciones por email basta con crear una nueva clase que implemente `NotificationPort`.

### Patrón SINGLETON (`BankConfigurationManager`)

```java
// Double-checked locking — thread-safe
public static BankConfigurationManager getInstance() {
    if (instance == null) {
        synchronized (BankConfigurationManager.class) {
            if (instance == null) {
                instance = new BankConfigurationManager();
            }
        }
    }
    return instance;
}
```

Gestiona la configuración global del banco: nombre, comisión por transferencia y saldo mínimo. Una sola instancia en toda la JVM.

---

## Documentación

- [ADR-001: Por qué Arquitectura Hexagonal](docs/ADR-001.md)
- [ADR-002: Por qué H2 en memoria](docs/ADR-002.md)
- [Diagrama de Arquitectura](docs/architecture-diagram.md)
