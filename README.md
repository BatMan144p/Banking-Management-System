# 🏦 Banking Management System

A console-based Banking Management System developed using **Java**, **JDBC**, and **MySQL**. This project demonstrates Object-Oriented Programming (OOP), layered architecture, JDBC database connectivity, SQL operations, transaction management, and exception handling.

---

## 📌 Project Overview

The Banking Management System allows customers to register, log in, create bank accounts, perform transactions, and view account details. The project follows a layered architecture to separate business logic from database operations.

---

## 🚀 Features

- 👤 Customer Registration
- 🔐 Customer Login
- 🏦 Create Bank Account
- 💰 Deposit Money
- 💸 Withdraw Money
- 📊 Check Account Balance
- 🔄 Transfer Money
- 📄 Mini Statement (Transaction History)
- ✅ Input Validation
- 🔒 JDBC Transactions (Commit & Rollback)
- ⚠️ Custom Exception Handling

---

## 🛠 Technologies Used

- Java
- JDBC
- MySQL
- Eclipse IDE
- Git & GitHub

---

## 📂 Project Structure

```
Banking-Management-System
│
├── src
│   └── bank
│       ├── dao
│       ├── exception
│       ├── main
│       ├── model
│       ├── service
│       └── util
│
├── sql
│   └── banking_management_system.sql
│
│
├── README.md
│
└── .gitignore
```

---

## 🗄 Database Tables

### Customer

Stores customer information.

### Account

Stores account details including account number, type, and balance.

### Transaction History

Stores deposit, withdrawal, and transfer records.

---

## 💡 Concepts Implemented

- Object-Oriented Programming (OOP)
- Encapsulation
- Layered Architecture
- JDBC Connectivity
- PreparedStatement
- CRUD Operations
- Exception Handling
- Custom Exceptions
- Collections (`List`)
- Transactions (`commit()` & `rollback()`)
- Input Validation

---

## 🔄 Application Flow

```
User
   │
   ▼
BankApplication
   │
   ▼
BankService
   │
   ├──────────────┐
   ▼              ▼
CustomerDAO   AccountDAO
                   │
                   ▼
           TransactionDAO
                   │
                   ▼
               MySQL Database
```

---



## ▶️ How to Run

1. Clone the repository.
2. Import the project into Eclipse IDE.
3. Import `sql/banking_management_system.sql` into MySQL.
4. Update the database username and password in `DBConnection.java`.
5. Run `BankApplication.java`.

---

## 🔮 Future Enhancements

- Spring Boot REST API
- React Frontend
- JWT Authentication
- Admin Dashboard
- Email Notifications
- Unit Testing

---

## 👨‍💻 Author

**Lokesh Goud Karre**


## ⭐ If you found this project useful, consider giving it a star on GitHub!