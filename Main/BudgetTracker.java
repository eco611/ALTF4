package Main;

import Classes.*;  // Importing all classes from the Classes package
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

            try { // Handle invalid input
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
            } catch (InputMismatchException error) { // Handle non-integer inputs
                System.out.println("Invalid input! Enter a number only.");
                sc.nextLine();  // Consume the invalid input
            }
        }
    }

    // Add income 
    /*
     * Add an income. The user enters a positive amount.
     * The income is recorded and the balance is recalculated and saved.
     */
    static void addIncome() { 
        if (count >= transactions.length) { // Check for array overflow
            System.out.println("Cannot add more transactions! Maximum reached.");
            return;
        }
        System.out.print("Enter income amount: PHP");
        try { // Handle invalid input
            double amt = sc.nextDouble(); // Read income amount
            sc.nextLine(); // Consume the newline character
            if (amt <= 0) { // Validate positive amount
                System.out.println("Amount must be positive!");
                return;
            }
            transactions[count++] = new Income(amt);  // Store the income transaction
            updateBalance();  // Update the balance after adding income
            saveTransactions();  // Save transactions in real-time
            System.out.println("Income added successfully!");
        } catch (InputMismatchException error) { // Catch non-numeric inputs
            System.out.println("Invalid input! Enter a valid number.");
            sc.nextLine();  // Consume the invalid input
        }
    }

    // ADD EXPENSE
    /*
      Add an expense. The user chooses a category
     (Bills/Needs/Wants) and enters a positive amount. The expense
     is recorded and the balance is recalculated and saved.
     */
    // To get current balance
    static double getCurrentBalance() {
        if (count == 0) return 0;
        Transaction t = transactions[count - 1]; // Last transaction should be Balance
        if (t instanceof Balance) {
            return t.getAmount();
        } else {
            return 0;
        }
    }


    static void addExpense() { 
        if (count >= transactions.length) { // Check for array overflow
            System.out.println("Cannot add more transactions! Maximum reached.");
            return;
        }
         if (getCurrentBalance() <= 0) { // Ensure there is enough balance
        System.out.println("Cannot add expense! Current balance is zero or negative.");
        return;
    }

    String desc = "";  // Description of the expense
    int typeChoice = 0;

    // Select expense type
    while (true) {
        System.out.println("Select expense type:");
        System.out.println("1. Bills");
        System.out.println("2. Needs");
        System.out.println("3. Wants");
        System.out.print("Choose an option (1-3): ");

        try { // Handle invalid input
            typeChoice = sc.nextInt(); // Read type choice
            sc.nextLine(); // Consume the newline character
            if (typeChoice >= 1 && typeChoice <= 3) break; // Valid choice
            else System.out.println("Invalid choice! Please choose 1-3.");
        } catch (InputMismatchException error) { // Catch non-integer inputs
            System.out.println("Invalid input! Enter a number 1-3.");
            sc.nextLine(); // Consume the invalid input
        }
    }

    // Map choice to description
    switch (typeChoice) {
        case 1: desc = "Bills"; break;
        case 2: desc = "Needs"; break;
        case 3: desc = "Wants"; break;
    }

    double amt = 0; // Expense amount
    double currentBalance = getCurrentBalance(); // Get current balance 

    // Input expense amount
    while (true) {
        System.out.print("Enter expense amount: PHP");
        try { // Handle invalid input
            amt = sc.nextDouble(); // Read expense amount
            sc.nextLine(); // Consume the newline character

             // Validate positive amount

            if (amt <= 0) {
                System.out.println("Amount must be positive!");
                continue;
            }

            if (amt > currentBalance) { // Check against current balance
                System.out.println("Insufficient balance! Current balance: PHP" + String.format("%.2f", currentBalance));
                continue;
            }
            break;
        } catch (InputMismatchException error) { // Catch non-numeric inputs
            System.out.println("Invalid input! Enter a valid number.");
            sc.nextLine(); // Consume the invalid input
        }
    }

    // Add expense and update balance
    transactions[count++] = new Expense(amt, desc);
    updateBalance(); // Recalculate balance
    saveTransactions(); // Save transactions in real-time
    System.out.println("Expense added successfully!");
    }


   
    // DISPLAY TRANSACTIONS
    /**
     * Print all recorded transactions (except the internal Balance entry)
     * followed by the current Balance entry.
     */

    static void displayTransactions() {
        if (count == 0) { // Check if there are any transactions to display
            System.out.println("No transactions yet!");
            return;
        }

        System.out.println("\n--- Transactions & Balance ---");
        // Map displayed numbers to actual indexes
        int displayNum = 1;
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) continue; // Skip balance
            System.out.print(displayNum + ". "); // Display number
            transactions[i].displayInfo(); // Show transaction info
            displayNum++; // Increment display number
        }
         // Display balance at the end
        for (int i = 0; i < count; i++) {
        if (transactions[i] instanceof Balance) {
            transactions[i].displayInfo();
            break;
            }
        }
    }

  
    // DELETE TRANSACTION
    /**
     * Delete a transaction by its displayed number. Shifts the array
     * down to remove gaps, then updates balance and saves the new list of transactions.
     */
    static void deleteTransaction() {
    if (count == 0) { // Check if there are any transactions to delete
        System.out.println("No transactions to delete!"); 
        return;
    }

    // Display transactions with numbers
    System.out.println("\n--- Transactions ---");
    int displayNum = 1; // Numbering for user
    int[] map = new int[count]; // Maps display number to actual array index
    int idx = 0;

    for (int i = 0; i < count; i++) {
        if (!(transactions[i] instanceof Balance)) {
            System.out.print(displayNum + ". ");
            transactions[i].displayInfo();
            map[idx++] = i; // store actual array index
            displayNum++;
        }
    }
    System.out.print("Enter transaction number to delete: "); 
    try { // Handle invalid input
        int num = sc.nextInt(); // Read transaction number to delete
        sc.nextLine();  // Consume the newline character

         // Validate transaction number
        if (num < 1 || num > displayNum) {
            System.out.println("Invalid number! Choose between 1 and " + displayNum + ".");
            return;
        }

        int realIndex = map[num - 1]; // get actual array index

        // Check if deleting this transaction would make balance negative
        double tempTotalIncome = 0, tempTotalExpense = 0;
        for (int i = 0; i < count; i++) {
            if (i == realIndex) continue; 
            if (transactions[i] instanceof Income) tempTotalIncome += transactions[i].getAmount();
            if (transactions[i] instanceof Expense) tempTotalExpense += transactions[i].getAmount();
        }

        if (tempTotalIncome - tempTotalExpense < 0) { // Check for negative balance
            System.out.println("Cannot delete this transaction! Deleting it would make balance negative.");
            return;
        }

        // Shift array to delete the chosen transaction
        for (int i = realIndex; i < count - 1; i++) {
            transactions[i] = transactions[i + 1];
        }
        transactions[--count] = null; // Decrease count and nullify last element

        updateBalance(); // Recalculate balance
        saveTransactions(); // Save changes
        System.out.println("Transaction deleted successfully!");

    } catch (InputMismatchException error) { // Handle non-integer inputs
        System.out.println("Invalid input! Enter a valid number.");
        sc.nextLine(); // Consume the invalid input
    }
}



    // UPDATE BALANCE
    /**
     * Recalculate the Balance entry based on current incomes and expenses.
     * Removes any existing Balance entry before appending the updated one.
     */
    static void updateBalance() {
        double totalIncome = 0, totalExpense = 0; // Initialize total income and expense

        // Remove any existing Balance entry from the array
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) { // Check if current transaction is Balance
                for (int j = i; j < count - 1; j++) {
                    transactions[j] = transactions[j + 1]; // Shift all elements left to remove the Balance
                }
                transactions[--count] = null; // Decrease count and nullify last element
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

   
    // SAVE TO FILE
    /**
     * Writes all current transactions to FILE_NAME using the format:
     * Type|Amount|Description. This updates the file every time changes occur.
     */
    static void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) { 
            for (int i = 0; i < count; i++) { // Loop through all transactions
                String type; // Store the transaction type as a string

                if (transactions[i] instanceof Income) type = "Income"; // Check if transaction is Income
                else if (transactions[i] instanceof Expense) type = "Expense"; // Check if transaction is Expense
                else type = "Balance"; // Otherwise, it's a Balance transaction

                // Write the transaction in the format Type|Amount|Description
                bw.write(type + "|" + transactions[i].getAmount() + "|" + transactions[i].getDescription());
                bw.newLine(); // Move to the next line for the next transaction
            }
        } catch (IOException error) { // Handle file writing errors
            System.out.println("Error saving transactions: " + error.getMessage()); // Print error message
        }
    }

   
    // LOAD FROM FILE
    /**
     * Load transactions from FILE_NAME. Expects lines in the format:
     * Type|Amount|Description. After reading, it recalculates the balance.
     */
    static void loadTransactions() {
    File file = new File(FILE_NAME); // Reference to the file
    if (!file.exists()) return; // If file doesn't exist, nothing to load

    // Clear the array before loading
    for (int i = 0; i < transactions.length; i++) {
        transactions[i] = null;
    }
    count = 0; // Reset count

     // Read the file line by line
    try (BufferedReader br = new BufferedReader(new FileReader(file))) { // BufferedReader for efficient reading
        String line; // Variable to hold each line read
        while ((line = br.readLine()) != null) { // Read each line until end of file
            String[] parts = line.split("\\|"); // Split the line into parts based on the '|' delimiter
            if (parts.length < 2) { // Validate line format
                System.out.println("Skipping invalid line: " + line);
                continue;
            }

            String type = parts[0]; // Transaction type
            double amount = 0; // Transaction amount
             // Parse the amount and handle potential format issues
            try { // Handle invalid amount format
                amount = Double.parseDouble(parts[1]); // Convert amount string to double
            } catch (NumberFormatException error) { // Catch parsing errors 
                System.out.println("Invalid amount in file, skipping line: " + line); // Print error message
                continue;
            }

            String desc; // Transaction description
             // Description is only for Expense; Income and Balance have empty descriptions
                if (parts.length > 2) {
                    desc = parts[2];
                } else {
                    desc = "";
                }

            switch (type) { // Create appropriate transaction based on type
                case "Income":
                    transactions[count++] = new Income(amount);
                    break;
                case "Expense":
                    transactions[count++] = new Expense(amount, desc);
                    break;
                case "Balance":
                    break; // Ignore balance 
            }
        }
    } catch (IOException error) { // Handle file reading errors
        System.out.println("Error loading transactions: " + error.getMessage());
    }

    updateBalance(); // Recalculate balance after loading

    // Warn if loaded expenses exceed income
    if (getCurrentBalance() < 0) {
        System.out.println("Warning: Loaded expenses exceed total income! Balance is negative.");
    }
    }
}
