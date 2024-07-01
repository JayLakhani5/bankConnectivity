package menuservice;

import management.TransactionManagement;

import java.sql.SQLException;
import java.util.Scanner;

public class TransactionMenuService {

    public static void getDeposite(TransactionManagement transactionManagement, int userId) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Deposite amount : ");
        int deposite = scanner.nextInt();
        transactionManagement.deposite(userId, deposite);
    }

    public static void getWithdraw(TransactionManagement transactionManagement, int userId) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter withdraw amount : ");
        int withdraw = scanner.nextInt();
        transactionManagement.withdraw(userId, withdraw);
    }

    public static void checkBalanceMenu(TransactionManagement transactionManagement, int userId) throws SQLException, ClassNotFoundException {
        transactionManagement.checkBalance(userId);
        
    }
}
