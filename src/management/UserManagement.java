package management;

import main.Main;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagement {

    List<User> userList;


    public UserManagement() {
        userList = new ArrayList<>();
    }

    public static Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_System", "root", "jay");
    }

    public void addUser(String firstName, String lastName, String userName, String password, int accountNumber, int balance) throws SQLException, ClassNotFoundException {
        User user = new User(firstName, lastName, userName, password, balance);
        userList.add(user);

        PreparedStatement preparedStatementCheckUserName = connection().prepareStatement("SELECT user_name from `user` u WHERE u.user_name = '" + userName + "';");
        ResultSet resultSetCheckUsername = preparedStatementCheckUserName.executeQuery();
        while (resultSetCheckUsername.next()) {
            String checkUserName = resultSetCheckUsername.getString("user_name");
            if (checkUserName.equals(userName)) {
                throw new RuntimeException("this user name is already inserted ");
            }
        }

        PreparedStatement preparedStatementAddUser = connection().prepareStatement("INSERT INTO bank_System.`user` (first_name,last_name,user_name,password,bank_id)values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatementAddUser.setString(1, firstName);
        preparedStatementAddUser.setString(2, lastName);
        preparedStatementAddUser.setString(3, userName);
        preparedStatementAddUser.setString(4, password);
        preparedStatementAddUser.setInt(5, Main.BANK_ID);
        int resultAddUser = preparedStatementAddUser.executeUpdate();
        int userId = 0;
        if (resultAddUser > 0) {
            ResultSet generatedKeys = preparedStatementAddUser.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
                System.out.println(STR."New user Id Created -> \{userId}");
            }
            System.out.println("User Inserted");
        } else {
            System.out.println("User not inserted");
        }
        if (userId == 0) {
            throw new RuntimeException("User is not valid");
        }

        PreparedStatement preparedStatementAccount = connection().prepareStatement("INSERT INTO bank_System.account (user_id,account_number,balance,bank_id)values(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        preparedStatementAccount.setInt(1, userId);
        preparedStatementAccount.setInt(2, accountNumber);
        preparedStatementAccount.setInt(3, balance);
        preparedStatementAccount.setInt(4, Main.BANK_ID);
        int resultAddress = preparedStatementAccount.executeUpdate();
        int accountId = 0;
        if (resultAddress > 0) {
            ResultSet generatedKeys = preparedStatementAccount.getGeneratedKeys();
            if (generatedKeys.next()) {
                accountId = generatedKeys.getInt(1);
                System.out.println(STR." new account id created -> \{accountId}");
            }
            System.out.println("account inserted");
            System.out.println(STR."Create account  number : \{user.getAccountNumber()}  balance is : \{user.getBalance()}");
        } else {
            System.out.println("account not inserted");

        }
        if (accountId == 0) {
            throw new RuntimeException("account is not inserted");
        }
    }

    public static void loginToAccount(String username, String password) throws SQLException, ClassNotFoundException {
        int userAfterLoginId = 0;
        PreparedStatement preparedStatementLogin = connection().prepareStatement("SELECT *  FROM bank_System.`user` u WHERE u.user_name  =? AND u.password  =? ");
        preparedStatementLogin.setString(1, username);
        preparedStatementLogin.setString(2, password);
        ResultSet resultSetLogin = preparedStatementLogin.executeQuery();
        System.out.println(preparedStatementLogin.toString());
        if (!resultSetLogin.isBeforeFirst()) {
            System.out.println("not match username & password");
            Main.startMenu();
        } else {
            while (resultSetLogin.next()) {
                int usertype = resultSetLogin.getInt("user_type");
                System.out.println("user type : " + usertype);
                userAfterLoginId = resultSetLogin.getInt("user_id");
                if (usertype == 1) {
                    System.out.println("you are admin user ");
                    Main.afterLoginMenu(userAfterLoginId, true);
                } else {
                    System.out.println(STR."id is : \{userAfterLoginId}");
                    Main.afterLoginMenu(userAfterLoginId, false);
                }
            }
        }

    }

}

