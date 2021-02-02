package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     * @throws IllegalArgumentException if amount is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            if (isAmountValid(startingBalance)){
                this.balance = startingBalance;
            }
            else{
                throw new IllegalArgumentException("Starting Balance: " + startingBalance + " is invalid, cannot create account");
            }
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
     * @post reduces the balance by amount if amount is non-negative and smaller than balance, if it is negative throw error
     * @throws IllegalArgumentException if amount is invalid
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        
        if(isAmountValid(amount)){
            if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
        else{
            throw new IllegalArgumentException("Invalid money value");
        }
       
        
    }
    

    /**
     * @post adds to the balance by amount if amount is non-negative and smaller than balance, if it is negative throw error
     * @throws IllegalArgumentException if amount is invalid
     */
    public void deposit (double amount) throws IllegalArgumentException{
        if(isAmountValid(amount)){
            balance += amount;
        }
        else{
            throw new IllegalArgumentException("Invalid money value");
        }
    }


    /*
    * Checks if the double is positive and only has two decimal places
    */
    public static boolean isAmountValid(double amount){
        if(amount >= 0){
            int num  = (int)(amount * 100);
            double roundedAmt = ((double)num) /100; //two decimal places
            if (roundedAmt - amount == 0){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }


    /**
     * @post checks if it can withdraw the money from the first account
     * Then deposits it in the second account if valid
     * @throws InsufficientFundsException if amount is larger than the first accs balance
     * @throws IllegalArgumentException if amount is invalid
     */
    public static void transfer(BankAccount toWithdraw, BankAccount toDeposit, double amount)
            throws InsufficientFundsException {
        if(isAmountValid(amount)){
            try{ 
                toWithdraw.withdraw(amount);
            }
            catch(InsufficientFundsException e){
                throw new InsufficientFundsException("not enough money");
            }
            toDeposit.deposit(amount);
        }
        else{
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    /**
     * given a string determines if that email is valid or not
     * @param email
     * @return true/false depending if the email is valid
     */
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

