package management;

import pojo.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankConnection {


    List<Bank> bankList;

    public BankConnection() {
        bankList = new ArrayList<>();
    }

    public Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_System", "root", "jay");
    }

    public Optional<Integer> isBankCreated() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatementCheckBank = connection().prepareStatement("SELECT * FROM bank_System.bank ;");
        ResultSet resultSetCheckBank = preparedStatementCheckBank.executeQuery();
        if (resultSetCheckBank.next()) {
            System.out.println("this bank is already inserted");
            return Optional.of(resultSetCheckBank.getInt(1));
        } else {
            return Optional.empty();
        }
    }

    public int createBank(String bankName, String bankAddress, String bankIfsc) throws SQLException, ClassNotFoundException {
        Optional<Integer> bankCreated = isBankCreated();
        if (bankCreated.isPresent()) {
            return bankCreated.get();
        } else {
            PreparedStatement preparedStatementBank = connection().prepareStatement("INSERT INTO bank_System.bank (bank_name,bank_address,bank_ifsc)values(?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatementBank.setString(1, bankName);
            preparedStatementBank.setString(2, bankAddress);
            preparedStatementBank.setString(3, bankIfsc);
            int resultBank = preparedStatementBank.executeUpdate();
            if (resultBank > 0) {
                ResultSet generatedKeys = preparedStatementBank.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bankId = generatedKeys.getInt(1);
                    System.out.println(STR."new Bank id is created  :  \{bankId}");
                    return bankId;
                } else {
                    throw new RuntimeException("Can not create bank in database");
                }

            } else {
                throw new RuntimeException("Can not create bank in database");
            }
        }
    }
}
