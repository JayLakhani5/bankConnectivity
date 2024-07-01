package management;

import pojo.CreateStatement;
import pojo.TransactionType;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionManagement {
    private int balance = 0;
    private List<CreateStatement> createStatementList;

    public TransactionManagement() {
        createStatementList = new ArrayList<>();
    }

    public List<CreateStatement> getCreateStatementList() {
        return createStatementList;
    }

    public void setCreateStatementList(List<CreateStatement> createStatementList) {
        this.createStatementList = createStatementList;
    }


    public Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_System", "root", "jay");
    }

    public void deposite(int userID, int amount) throws SQLException, ClassNotFoundException {

        // TODO show current balance fetch data in database

        PreparedStatement preparedStatementShowBalance = connection().prepareStatement("SELECT user_name ,balance \n" +
                "from `user` u \n" +
                "join account a ON u.user_id = a.user_id  where u.user_id = '" + userID + "';");
        ResultSet resultSetShowBalance = preparedStatementShowBalance.executeQuery();
        while (resultSetShowBalance.next()) {

            int balanceShow = resultSetShowBalance.getInt("balance");
            // System.out.println("balance is ->" + balanceShow);

            if (amount > 0) {
                balanceShow = balanceShow + amount;
                balance = balanceShow;

                //TODO insert deposite transaction

                PreparedStatement preparedStatementDeposite = connection().prepareStatement("INSERT INTO trasnsaction (user_id,transactionType,amount)values\t(?,?,?);");
                preparedStatementDeposite.setInt(1, userID);
                preparedStatementDeposite.setString(2, String.valueOf(TransactionType.CR));
                preparedStatementDeposite.setInt(3, balance);
                int resultDeposite = preparedStatementDeposite.executeUpdate();
                if (resultDeposite > 0) {
                    System.out.println("Deposite successfully..");

                    //TODO Update balance after withdraw in database

                    PreparedStatement preparedStatementUpdateBalance = connection().prepareStatement(STR."UPDATE account SET balance = '\{balance}' WHERE  user_id = '\{userID}';");
                    int resultSetUpdateBalance = preparedStatementUpdateBalance.executeUpdate();
                    if (resultSetUpdateBalance > 0) {
                        System.out.println(STR."update balance successful your balance is \{balance}");
                    } else {
                        System.out.println("not update balance");
                    }
                }
            } else {
                System.out.println("negative values not inserted");
            }
        }
    }

    public void withdraw(int userId, int amount) throws SQLException, ClassNotFoundException {

        //TODO show current balance fetch data in database
        //checkBalance(userId)

        PreparedStatement preparedStatementShow = connection().prepareStatement("SELECT user_name ,balance \n" +
                "from `user` u \n" +
                "join account a ON u.user_id = a.user_id  where u.user_id = '" + userId + "';");
        ResultSet resultSetShow = preparedStatementShow.executeQuery();
        while (resultSetShow.next()) {
            int balanceShow = resultSetShow.getInt("balance");
            // System.out.println("balance is ->" + balanceShow);

            if (amount > 0) {
                if (balanceShow >= amount) {

                    balanceShow = balanceShow - amount;
                    balance = balanceShow;
                    System.out.println(STR."After withdraw your balance is : \{balanceShow}");

                    //TODO insert withdraw transaction

                    PreparedStatement preparedStatementDeposite = connection().prepareStatement("INSERT INTO trasnsaction (user_id,transactionType,amount)values\t(?,?,?);");
                    preparedStatementDeposite.setInt(1, userId);
                    preparedStatementDeposite.setString(2, String.valueOf(TransactionType.DR));
                    preparedStatementDeposite.setInt(3, balance);
                    int resultDeposite = preparedStatementDeposite.executeUpdate();
                    if (resultDeposite > 0) {
                        System.out.println("withdraw successfully..");

                        //TODO Update balance after withdraw in database

                        PreparedStatement preparedStatementUpdateBalance = connection().prepareStatement("UPDATE account SET balance = '" + balance + "' WHERE  user_id = '" + userId + "';");
                        int resultSetUpdateBalance = preparedStatementUpdateBalance.executeUpdate();
                        if (resultSetUpdateBalance > 0) {
                            System.out.println("update balance");
                        } else {
                            System.out.println("not update balance");
                            
                        }
                    }
                } else {
                    System.out.println(STR."your balance is less than ->\{amount}");
                }
            } else {
                System.out.println("Not negative values inserted");
            }

        }
    }

    public int checkBalance(int userId) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatementCheckBalance = connection().prepareStatement("SELECT user_name ,balance ,account_number\n" +
                "from `user` u \n" +
                "join account a ON u.user_id = a.user_id  where u.user_id = '" + userId + "';");
        ResultSet resultSet = preparedStatementCheckBalance.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("user_name");
            System.out.print(STR."user name is : \{username}");

            int balance = resultSet.getInt("balance");
            System.out.print(STR.", balacne is : \{balance}");

            int accountNumber = resultSet.getInt("account_number");
            System.out.println(STR.", account number  is : \{accountNumber}");
        }
        return balance;

    }


    public void createFile(int userId) throws SQLException, ClassNotFoundException, IOException {
        PreparedStatement preparedStatementCreateFile = connection().prepareStatement("SELECT first_name ,user_name ,account_number ,`date` ,transactionType ,amount ,balance \n" +
                "from `user` u \n" +
                "join account a on u.user_id =a.user_id \n" +
                "join trasnsaction t on u.user_id =t.user_id where u.user_id ='" + userId + "';");


        int accountNumber = 0;
        List<String> lines = new ArrayList<>();

        ResultSet resultSetCreateFile = preparedStatementCreateFile.executeQuery();
        while (resultSetCreateFile.next()) {

            String firstName = resultSetCreateFile.getString("first_name");
            System.out.print("First name : " + firstName);

            String userName = resultSetCreateFile.getString("user_name");
            System.out.print(", user name : " + userName);

            accountNumber = resultSetCreateFile.getInt("account_number");
            System.out.println(", account number : " + accountNumber);

            String date = String.valueOf(resultSetCreateFile.getString("date"));
            System.out.print("date : " + date);

            String transactionType = resultSetCreateFile.getString("transactionType");
            System.out.print(", transaction type : " + transactionType);

            int amount = resultSetCreateFile.getInt("amount");
            System.out.println(", amount : " + amount);

            int balance = resultSetCreateFile.getInt("balance");
            System.out.println("total balance : " + balance);


            CreateStatement createStatement = new CreateStatement(firstName, userName, accountNumber, date, transactionType, amount, balance);
            createStatementList.add(createStatement);
            System.out.println(createStatementList);
        }

        lines = this.getCreateStatementList().stream().map(CreateStatement::toString).toList();
        FileWriter fileWriter = new FileWriter("/home/jay/Desktop/Demofile/" + accountNumber + ".txt");
        lines.forEach(line -> {
            try {
                fileWriter.append("\n");
                fileWriter.append(line);

            } catch (Exception e) {
                System.out.println(e);
            }
        });
        fileWriter.close();
        System.out.println("Statement created ");
    }
}