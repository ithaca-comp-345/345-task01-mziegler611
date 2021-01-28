package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        //Balance is negative (should set it to zero)
        BankAccount bankAccount2 = new BankAccount("a@b.com", -1);
        assertEquals(0, bankAccount2.getBalance());

        //Balance with a Double
        BankAccount bankAccount3 = new BankAccount("a@b.com", 10.21);
        assertEquals(10.21, bankAccount3.getBalance());

        //Balance with a Double with too many places
        BankAccount bankAccount4 = new BankAccount("a@b.com", 10.213);
        assertEquals(10.21, bankAccount4.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        //Withdraw a negative amount
        bankAccount.withdraw(-100);
        assertEquals(100, bankAccount.getBalance());
        
        //Withdraw 0
        bankAccount.withdraw(0);
        assertEquals(100, bankAccount.getBalance());

        //Edge Case
        bankAccount.withdraw(.000000000001);
        assertEquals(100, bankAccount.getBalance());

        //Withdraw a Regular Double 
        bankAccount.withdraw(0.01);
        assertEquals(99.99, bankAccount.getBalance());

        //Withdraw a Double with too many places
        bankAccount.withdraw(0.999);
        assertEquals(99.00, bankAccount.getBalance());

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
       
        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}