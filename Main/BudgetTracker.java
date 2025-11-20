package Main;

import Classes.*;  // Importing all classes from the ALT_F4.Classes package
import java.util.*;  // Importing the utility classes (like Scanner)
import java.io.*;  // Importing Input/Output classes for file handling

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
            } catch (InputMismatchException err) {
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

    static void addExpense() {
     
    }

   
    static void displayTransactions() {
      
    }

  
    static void deleteTransaction() {
       
    }

    static void updateBalance() {
       
    }

   
    static void saveTransactions() {
  
    }

   
    static void loadTransactions() {
     
    }
}

