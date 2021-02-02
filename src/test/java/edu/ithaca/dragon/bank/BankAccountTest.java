package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200); //Equivalence class (Works correctly)
        assertEquals(200, bankAccount.getBalance());

        //Balance is negative (should set it to zero)
        BankAccount bankAccount2 = new BankAccount("a@b.com", -1); //Equivalence class any negative amount
        assertEquals(0, bankAccount2.getBalance());

        //Balance with a Double
        BankAccount bankAccount3 = new BankAccount("a@b.com", 10.21); //Equivalence class
        assertEquals(10.21, bankAccount3.getBalance());

        //Balance with a Double with too many places
        BankAccount bankAccount4 = new BankAccount("a@b.com", 10.213); //Equivalence class
        assertEquals(10.21, bankAccount4.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance()); //Correctly amount left from withdrawn
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //Overdrawing

        //Withdraw a negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
        //Equivalence class Negative amount
        
        //Withdraw 0
        bankAccount.withdraw(0);
        assertEquals(100, bankAccount.getBalance()); //Equivalence class withdrawing nothing

        
        //Edge Case for any number not in the correct format
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(.00000000001));

        //Withdraw a Regular Double 
        bankAccount.withdraw(0.01);
        assertEquals(99.99, bankAccount.getBalance());  //Equivalence class

        //Withdraw a Double with too many places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(.999));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //Equivalence class (base case to make sure a valid email passes)

        //Edge Case
        assertFalse( BankAccount.isEmailValid("")); //Edge Case

        //Certain Characters not allowed
        assertFalse( BankAccount.isEmailValid("a#a@b.com")); //Equivalence class(Coded for all invalid symbols)
        assertFalse( BankAccount.isEmailValid("a-@b.com")); //Equivalence class

        //No full .com at end
        assertFalse( BankAccount.isEmailValid("a@b.c")); //Equivalence class (Search to make sure .com is present )
        assertFalse( BankAccount.isEmailValid("a@bcom")); //Equivalence class
        assertTrue( BankAccount.isEmailValid("a@b.com")); //Equivalence class

        //No double ..s or too many
        assertFalse( BankAccount.isEmailValid("a..b@b.com")); //Equivalence class (Checking for too many periods)
        assertFalse( BankAccount.isEmailValid("a.b@b.com")); //Equivalence class

        //Can't start with certain chatacters
        assertFalse( BankAccount.isEmailValid(".a@b.com")); //Equivalence class (Checked if the email started with a period) Invalid chars covered above
        assertFalse( BankAccount.isEmailValid("b.a@b.com")); //Equivalence class (Two many periods)
        
        //Everything case is present 
        
    }

    @Test
    void isAmountValidTest(){
        

        //Valid Inputs
        assertTrue(BankAccount.isAmountValid(10));
        assertTrue(BankAccount.isAmountValid(10.0));
        assertTrue(BankAccount.isAmountValid(10.01));

        //Invalid edge cases
        assertFalse(BankAccount.isAmountValid(10.011));
        assertFalse(BankAccount.isAmountValid(-0.01));

        //Negative Inputs
        assertFalse(BankAccount.isAmountValid(-10));
        assertFalse(BankAccount.isAmountValid(-10.00));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

    
        //Equivalence class any negative amount
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", -1));

        //Balance with a Double
        BankAccount bankAccount3 = new BankAccount("a@b.com", 10.21); //Equivalence class
        assertEquals("a@b.com", bankAccount3.getEmail());
        assertEquals(10.21, bankAccount3.getBalance());

        //Balance with a Double with too many places
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 10.213));
    }

    @Test
    void depositTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);

        assertEquals(300, bankAccount.getBalance()); //Correctly amount left from deposit
       

        //Deposit a negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));
        //Equivalence class Negative amount
        
        //Deposit 0
        bankAccount.deposit(0);
        assertEquals(300, bankAccount.getBalance()); //Equivalence class deposit nothing

        
        //Edge Case for any number not in the correct format
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(.00000000001));

        //Deposit a Regular Double 
        bankAccount.deposit(0.01);
        assertEquals(300.01, bankAccount.getBalance());  //Equivalence class

        //Depost a Double with too many places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(.999));
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        

       //Transfer a normal amount
       BankAccount.transfer(bankAccount, bankAccount2, 100);
       assertEquals(100, bankAccount.getBalance()); 
       assertEquals(300, bankAccount2.getBalance()); 

       //Transfer a number with too many places
       assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount, bankAccount2, 10.001));

       //Transfer a negative number
       assertThrows(IllegalArgumentException.class, () -> BankAccount.transfer(bankAccount, bankAccount2, -1));

        //Transfer a value larger than the account balance to withdraw from
       assertThrows(InsufficientFundsException.class, () -> BankAccount.transfer(bankAccount, bankAccount2, 500));
        
       //Transfer a normal amount with double
       BankAccount.transfer(bankAccount, bankAccount2, 50.50);
       assertEquals(49.50, bankAccount.getBalance()); 
       assertEquals(350.50, bankAccount2.getBalance()); 
    }

    

}