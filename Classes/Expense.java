package Classes;

/* Represents an expense transaction.
  Inherits from the abstract class Transaction.*/
  
public class Expense extends Transaction { //Constructor to initialize an expense transaction.
    public Expense(double amount, String description ) { 
        super(amount, description ); // Passing the amount and description to the superclass constructor.
    }   

     // Override method to display expense transaction details.
    @Override
    public void displayInfo() {
        System.out.println("Expense: " + getDescription() + " | Amount: PHP" + getAmount()); // Displays expense amount
    }
}