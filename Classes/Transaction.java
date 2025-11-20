package Classes;

/*Abstract class to represent a financial transaction.
 It is the superclass for Income, Expense, and Balance.*/

public abstract class Transaction {
    private double amount; // Amount of the transaction
    private String description;  // Description of the transaction

     // Constructor to initialize the amount and description.
    public Transaction(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

      // Getters for the amount and description
    public double getAmount() { 
        return amount; 
    }
    public String getDescription() {
         return description; 
    }
        // Abstract method to display transaction information.
    public abstract void displayInfo();
}