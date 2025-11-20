package Classes;

/*Represents an income transaction.
 Inherits from the abstract class Transaction.*/

public class Income extends Transaction {
    public Income(double amount) {   //Constructor to initialize an income transaction.
        super(amount, ""); 
    }
    
     // Override method to display income transaction details.
    @Override
    public void displayInfo() {
        System.out.println("Income | Amount: PHP" + getAmount()); // Displays income amount
    }
}