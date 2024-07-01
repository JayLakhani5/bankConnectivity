package main;

import management.BankConnection;
import management.TransactionManagement;
import management.UserManagement;
import menuservice.BankMenuService;
import menuservice.TransactionMenuService;
import menuservice.UserMenuService;

import java.sql.SQLException;

import static menuservice.UserMenuService.optionSelection;

public class Main {
    static UserManagement userManagement = new UserManagement();
    static TransactionManagement transactionManagement = new TransactionManagement();

    public static final int BANK_ID;

    static {
        BankConnection bankConnection = new BankConnection();
        try {
            BANK_ID = BankMenuService.addBank(bankConnection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        startMenu();
    }

    public static void startMenu() {
        try {
            int option;
            UserMenuService.printInitialMenu();
            do {
                option = optionSelection();
                switch (option) {
                    case 1:
                        UserMenuService.addUser(userManagement, false);
                        startMenu();
                        break;
                    case 2:
                        UserMenuService.login(userManagement);
                        break;
                    default:
                        throw new RuntimeException("invalid selection ");
                }

            } while (option != 2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            startMenu();
        }
    }

    public static void afterLoginMenu(int userId, boolean isAdmin) {
        try {
            int afterLogin;
            UserMenuService.printOptionsAfterLogin(isAdmin);
            do {
                afterLogin = optionSelection();
                switch (afterLogin) {
                    case 1:
                        TransactionMenuService.checkBalanceMenu(transactionManagement, userId);
                        afterLoginMenu(userId, isAdmin);
                        break;
                    case 2:
                        TransactionMenuService.getDeposite(transactionManagement, userId);
                        afterLoginMenu(userId, isAdmin);
                        break;
                    case 3:
                        TransactionMenuService.getWithdraw(transactionManagement, userId);
                        afterLoginMenu(userId, isAdmin);
                        break;
                    case 4:
                        transactionManagement.createFile(userId);
                        afterLoginMenu(userId, isAdmin);
                        break;
                    case 5:
                        startMenu();
                        break;
                    default:
                        throw new RuntimeException("Invalid selection ");
                }
            } while (afterLogin != 5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            afterLoginMenu(userId, isAdmin);
        }
    }
}