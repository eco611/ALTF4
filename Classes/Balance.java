package Classes;

/*Represents the current balance.
 Inherits from the abstract class Transaction.*/

public class Balance extends Transaction {
    public Balance(double amount) {  //Constructor to initialize the balance.
        super(amount, "Current Balance"); 
    }
    
    @Override // Overridden method to display balance information.
    public void displayInfo() {
        System.out.println("Balance | Amount: PHP" + getAmount()); // Displays the current balance
    }
}