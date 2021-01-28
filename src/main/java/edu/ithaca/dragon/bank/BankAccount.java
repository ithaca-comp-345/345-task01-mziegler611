package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        if (balance < 0){
            balance = 0;
        }
        int num  = (int)(balance * 100);
        balance = ((double)num) /100; //two decimal places
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        
        if(amount > 0){
            int num  = (int)(amount * 100);
            amount = ((double)num) /100; //two decimal places
            if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
    }
    


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf(".com") == -1){
            return false;
        }
        String[] chars = {"!","#","$","%","%","&","*","(",")", "-"};
        for(int i = 0; i < chars.length; i++){
            if(email.indexOf(chars[i]) > -1){
                return false;
            }
        }
        String[] emailArr = email.split("");
        int count = 0;
        for(int i = 0; i < emailArr.length; i++){
            if(emailArr[i].equals(".")){
                count++;
            }
        }
       
        if(count > 1){
            return false;
        }
       else if(email.indexOf('.') == 0){
            return false;
        }
        return true;
    }
}
