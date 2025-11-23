# ALTF4
# Project Title:
BudgetPal:A Household Budget Tracker

# Description/Overview: 
BudgetPal is a simple console-based household budgeting tracker that helps users manage their income and expenses. The program allows users to add income, categorize expenses (bills, needs, and wants), display transaction history with current balance, and delete transactions if needed. It provides a clear overview of personal budget and prevents overspending.

# OOP Concepts Applied: 
Abstraction - The Transaction class is abstract and provides a template for Income, Expense, and Balance.

Inheritance - Income, Expense, and Balance classes inherit from Transaction. They share common properties such as amount and description, reducing code duplication.

Polymorphism - displayInfo() method is overridden in Income, Expense, and Balance to show specific information depending on the transaction type.

Encapsulation - Class fields like amount and description are private. Access is controlled via public getter methods, ensuring data integrity.

# Program Structure:
## Main Class: BudgetTracker

Handles user interaction, menu display, and manages transaction array.

### Methods:

addIncome() – Add a new income.

addExpense() – Add a categorized expense.

displayTransactions() – Show all transactions and current balance.

deleteTransaction() – Delete a transaction safely.

updateBalance() – Recalculate current balance.

saveTransactions() / loadTransactions() – Save and load transaction data from a file.

## Transaction Classes

Transaction (abstract) – A parent class that stores the common attributes of all transactions such as amount and description. It serves as the blueprint for the child classes Income, Expense, and Balance, which all inherit its shared properties and behavior.

Income – Represents incoming money.

Expense – Represents money spent with a category.

Balance – Represents the current balance calculated automatically.

# How to Run the Program
### 1st: Open terminal/command prompt.

### 2nd: Navigate to the project folder: Ex.: cd C:\Users\Enrico\Desktop\OOP_PROJECT\ALTF4

### 3rd: Compile the Java classes: javac Main\BudgetTracker.java Classes\*.java

### Last: Run the program: java Main.BudgetTracker

# Sample output:
```
--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 1
Enter income amount: PHP100
Income added successfully!

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 1
Enter income amount: PHP500
Income added successfully!

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 2
Select expense type:
1. Bills
2. Needs
3. Wants
Choose an option (1-3): 1
Enter expense amount: PHP200
Expense added successfully!

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 3

--- Transactions & Balance ---
1. Income | Amount: PHP100.0
2. Income | Amount: PHP500.0
3. Expense: Bills | Amount: PHP200.0
Balance | Amount: PHP400.0

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 4

--- Transactions ---
1. Income | Amount: PHP100.0
2. Income | Amount: PHP500.0
3. Expense: Bills | Amount: PHP200.0
Enter transaction number to delete: 1
Transaction deleted successfully!

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 3

--- Transactions & Balance ---
1. Income | Amount: PHP500.0
2. Expense: Bills | Amount: PHP200.0
Balance | Amount: PHP300.0

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 5
Goodbye!
PS C:\Users\Enrico\Desktop\OOP_PROJECT\ALTF4>

Choose an option: 3

--- Transactions & Balance ---
1. Income | Amount: PHP500.0
2. Expense: Bills | Amount: PHP200.0
Balance | Amount: PHP300.0

--- BudgetPal: Simple Household Tracker ---
1. Add Income
2. Add Expense
3. Display Transactions & Balance
4. Delete Transaction
5. Exit
Choose an option: 5
Goodbye!
PS C:\Users\Enrico\Desktop\OOP_PROJECT\ALTF4>
```
<img width="745" height="905" alt="Screenshot 2025-11-23 205745" src="https://github.com/user-attachments/assets/3f66156f-c51f-4938-8224-71a9748bc795" />
<img width="620" height="944" alt="Screenshot 2025-11-23 205853" src="https://github.com/user-attachments/assets/ca55434a-ca8e-43aa-86eb-0f3ed2bbfbad" />


# Author 
## Author: GROUP ALTF4
### Dalisay, Charles Addison G.

### Hernandez, Napoleon II C.

### Quitain, Manu James  H.

### Sinchongco, Enrico P.

# Acknowledgement: 
We would like to express our sincere gratitude to Sir Juriel for his continuous guidance, support, and valuable feedback throughout the development of this project. 
We also want to thank our groupmates and classmates for their cooperation, ideas, and encouragement that helped us complete this work successfully. 

