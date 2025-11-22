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
    static void addExpense() {
        if (count == 0) { // Ensure there is at least one income before adding expenses
            System.out.println("Please add income first!");
            return;
        }
        
        String desc = "";// To hold the expense description
        int typeChoice = 0;// To hold the user's choice of expense type

        while (true) { // Loop until a valid type is chosen
            System.out.println("Select expense type:");
            System.out.println("1. Bills");
            System.out.println("2. Needs");
            System.out.println("3. Wants");
            System.out.print("Choose an option (1-3): ");
 
            try { // Handle invalid input
                typeChoice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                if (typeChoice >= 1 && typeChoice <= 3) break;
                else System.out.println("Invalid choice! Please choose 1-3.");
            } catch (InputMismatchException error) { // Catch non-integer inputs
                System.out.println("Invalid input! Enter a number 1-3.");
                sc.nextLine(); // Consume the invalid input
            }
        }
    // Map numeric choice to a short textual description
        switch (typeChoice) {
            case 1: desc = "Bills"; break;
            case 2: desc = "Needs"; break;
            case 3: desc = "Wants"; break;
        }

        double amt = 0; // To hold the expense amount
        while (true) { // Loop until a valid positive amount is entered
            System.out.print("Enter expense amount: ₱");
            try { // Handle invalid input
                amt = sc.nextDouble(); // Read expense amount
                sc.nextLine(); // Consume the newline character
                if (amt > 0) break; // Valid positive amount
                else System.out.println("Amount must be positive!"); // Prompt again
            } catch (InputMismatchException error) { // Catch non-numeric inputs
                System.out.println("Invalid input! Enter a valid number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
        // Add the new expense and refresh the computed balance
        transactions[count++] = new Expense(amt, desc);
        updateBalance(); // Update the balance after adding expense
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
        int num = 1; // To number the displayed transactions
        // Print non-balance transactions in order
        for (int i = 0; i < count; i++) { 
            if (transactions[i] instanceof Balance) continue; // Skip Balance entries so that they are printed last
            System.out.print(num + ". "); // Print the transaction number
            transactions[i].displayInfo(); // Display transaction details
            num++; // Increment the transaction number
        }
        // Print the balance at the end
        for (int i = 0; i < count; i++) {
            if (transactions[i] instanceof Balance) {
                transactions[i].displayInfo(); // Display balance details
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
        if (count == 0) { // Checks if there are any transactions to delete
            System.out.println("No transactions to delete!"); 
            return;
        }

        displayTransactions(); // Show current transactions for reference
        System.out.print("Enter transaction number to delete: "); // Choose transaction to delete
        try { // Handle invalid input
            int num = sc.nextInt(); // Read the transaction number to delete
            sc.nextLine(); // Consume the newline character
            if (num < 1 || num > count) { // Validate the chosen number
                System.out.println("Invalid number! Choose between 1 and " + count + ".");
                return;
            }
    // Moves all following elements one position left to fill the removed entry.
            for (int i = num - 1; i < count - 1; i++) {
                transactions[i] = transactions[i + 1];
            }
            transactions[--count] = null; // Decrease count and nullify last element
            updateBalance(); // Recalculate balance after deletion
            saveTransactions(); //Updates the saved data.
            System.out.println("Transaction deleted successfully!");
        } catch (InputMismatchException error) { // Catch non-integer inputs
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
        File file = new File(FILE_NAME); // Represent the file where transactions are stored
        if (!file.exists()) return; // If file doesn't exist, nothing to load

        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Open file for reading
            String line; 
            count = 0; // Reset transaction count before loading

            while ((line = br.readLine()) != null) { // Read file line by line
                String[] parts = line.split("\\|"); // Split line by '|' to get type, amount, description
                if (parts.length < 2) continue; // Skip invalid lines

                String type = parts[0]; // First part is transaction type
                double amount = Double.parseDouble(parts[1]); // Second part is amount
                String desc = parts.length > 2 ? parts[2] : ""; // Optional description

                switch (type) {
                    case "Income": 
                        transactions[count++] = new Income(amount); // Create Income object
                        break;
                    case "Expense": 
                        transactions[count++] = new Expense(amount, desc); // Create Expense object
                        break;
                    case "Balance": 
                        break; // Ignore Balance, it will be recalculated
                }
            }
        } catch (IOException | NumberFormatException error) { // Catch  errors
            System.out.println("Error loading transactions: " + error.getMessage()); // Print error message
        }

        updateBalance(); // Recompute balance after loading all transactions
    }

}
