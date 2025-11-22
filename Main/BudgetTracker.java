package Main;

import Classes.*;  // Importing all classes from the ALT_F4.Classes package
import java.util.*;  // Importing the utility classes (like Scanner)

public class BudgetTracker {
    static Transaction[] transactions = new Transaction[100];  // Array to store transactions
    static int count = 0;  // Counter for the number of transactions
    static Scanner sc = new Scanner(System.in);  // Scanner for user input
    static final String FILE_NAME = "budget.txt";  // File for saving/loading transactions

    public static void main(String[] args) {
        loadTransactions();  // Load transactions from file at the start

        boolean exit = false;  // to control the exit of the loop
        while (!exit) {
            // Display the main menu
            System.out.println("\n--- BudgetPal: Simple Household Tracker ---");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Display Transactions & Balance");
            System.out.println("4. Delete Transaction");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = sc.nextInt();  // Read user choice
                sc.nextLine();  // Consume the newline character

                switch (choice) {
                    case 1: addIncome(); break;  // Add income
                    case 2: addExpense(); break;  // Add expense
                    case 3: displayTransactions(); break;  // Display all transactions and balance
                    case 4: deleteTransaction(); break;  // Delete a transaction
                    case 5:
                        saveTransactions();  // Save transactions before exit
                        exit = true;  // Exit the program
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Choose 1-5.");
                }
            } catch (InputMismatchException error) {
                System.out.println("Invalid input! Enter a number only.");
                sc.nextLine();  // Consume the invalid input
            }
        }
    }

    // Method to add income
    static void addIncome() {
        System.out.print("Enter income amount: PHP");
        try {
            double amt = sc.nextDouble();
            sc.nextLine();
            if (amt <= 0) {
                System.out.println("Amount must be positive!");
                return;
            }
            transactions[count++] = new Income(amt);  // Store the income transaction
            updateBalance();  // Update the balance after adding income
            saveTransactions();  // Save transactions in real-time
            System.out.println("Income added successfully!");
        } catch (InputMismatchException error) {
            System.out.println("Invalid input! Enter a valid number.");
            sc.nextLine();  // Consume the invalid input
        }
    }

    // *ADD EXPENSE
    /*
      Add an expense. The user chooses a category
     (Bills/Needs/Wants) and enters a positive amount. The expense
     is recorded and the balance is recalculated and saved.
     */
    static void addExpense() {
        if (count == 0) {
            System.out.println("Please add income first!");
            return;
        }

        String desc = "";
        int typeChoice = 0;

        while (true) {
            System.out.println("Select expense type:");
            System.out.println("1. Bills");
            System.out.println("2. Needs");
            System.out.println("3. Wants");
            System.out.print("Choose an option (1-3): ");

            try {
                typeChoice = sc.nextInt();
                sc.nextLine();

                if (typeChoice >= 1 && typeChoice <= 3) break;
                else System.out.println("Invalid choice! Please choose 1-3.");
            } catch (InputMismatchException error) {
                System.out.println("Invalid input! Enter a number 1-3.");
                sc.nextLine();
            }
        }
    // Map numeric choice to a short textual description
        switch (typeChoice) {
            case 1: desc = "Bills"; break;
            case 2: desc = "Needs"; break;
            case 3: desc = "Wants"; break;
        }

        double amt = 0;
        while (true) {
            System.out.print("Enter expense amount: ₱");
            try {
                amt = sc.nextDouble();
                sc.nextLine();
                if (amt > 0) break;
                else System.out.println("Amount must be positive!");
            } catch (InputMismatchException error) {
                System.out.println("Invalid input! Enter a valid number.");
                sc.nextLine();
            }
        }
        // Add the new expense and refresh the computed balance
        transactions[count++] = new Expense(amt, desc);
        updateBalance();
        saveTransactions(); // REAL-TIME SAVE
        System.out.println("Expense added successfully!");
    }

   
    //   DISPLAY TRANSACTIONS
    /**
     * Print all recorded transactions (except the internal Balance entry)
     * followed by the current Balance entry.
     */

    static void displayTransactions() {
        if (count == 0) {
            System.out.println("No transactions yet!");
            return;
        }

        System.out.println("\n--- Transactions & Balance ---");
        int num = 1;
    // Print non-balance transactions in order
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) continue;
            System.out.print(num + ". ");
            transactions[i].displayInfo();
            num++;
        }
    // Print the balance at the end
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) {
                transactions[i].displayInfo();
                break;
            }
        }
    }

  
    //DELETE TRANSACTION
    /**
     * Delete a transaction by its displayed number. Shifts the array
     * down to remove gaps, then updates balance and saves the new list of transactions.
     */
    static void deleteTransaction() {
        if (count == 0) {
            System.out.println("No transactions to delete!");
            return;
        }

        displayTransactions();
        System.out.print("Enter transaction number to delete: ");
        try {
            int num = sc.nextInt();
            sc.nextLine();
            if (num < 1 || num > count) {
                System.out.println("Invalid number! Choose between 1 and " + count + ".");
                return;
            }
    // Moves all following elements one position left to fill the removed entry.
            for (int i = num - 1; i < count - 1; i++) {
                transactions[i] = transactions[i + 1];
            }
            transactions[--count] = null;
            updateBalance();
            saveTransactions(); //Updates the saved data.
            System.out.println("Transaction deleted successfully!");
        } catch (InputMismatchException err) {
            System.out.println("Invalid input! Enter a valid number.");
            sc.nextLine();
        }
    }


    // UPDATE BALANCE
    /**
     * Recalculate the Balance entry based on current incomes and expenses.
     * Removes any existing Balance entry before appending the updated one.
     */
    static void updateBalance() {
        double totalIncome = 0, totalExpense = 0;

        // Remove any existing Balance entry from the array
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) {
                for (int j = i; j < count - 1; j++) {
                    transactions[j] = transactions[j + 1];
                }
                transactions[--count] = null;
                i--; // re-check current index after shift
            }
        }

        // Sum incomes and expenses
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Income) totalIncome += transactions[i].getAmount();
            if (transactions[i] instanceof Expense) totalExpense += transactions[i].getAmount();
        }

        // Append a new Balance entry representing (income - expense)
        transactions[count++] = new Balance(totalIncome - totalExpense);
    }

   
    static void saveTransactions() {
  
    }

   
    static void loadTransactions() {
     
    }
}

