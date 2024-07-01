package menuservice;

import management.UserManagement;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Scanner;

public class UserMenuService {


    public static void printInitialMenu() {
        System.out.println("1.SingUp");
        System.out.println("2.LoginUser");
        System.out.print("Enter your selection : ");
    }

    public static void printOptionsAfterLogin(boolean Admin) {
        System.out.println("1.Check balance");
        System.out.println("2.Deposit amount");
        System.out.println("3.withdraw balance");
        if (Admin) {
            System.out.println("4.statement");
        }
        System.out.println("5.Logout");
        System.out.print("Enter your selection : ");
        
    }

    public static void addUser(UserManagement userManagement, boolean isAdmin) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name : ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name : ");
        String lastName = scanner.nextLine();

        System.out.print("Enter user name : ");
        String userName = scanner.nextLine();

        System.out.print("Enter password : ");
        String password = scanner.nextLine();

        int accountNumber = new SecureRandom().nextInt(10000, 99999);
        int balance = 0;
        userManagement.addUser(firstName, lastName, userName, password, accountNumber, balance);
    }

    public static int optionSelection() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void login(UserManagement userManagement) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user name : ");
        String userName = scanner.nextLine();

        System.out.print("Enter password : ");
        String password = scanner.nextLine();
        userManagement.loginToAccount(userName, password);

    }
}
