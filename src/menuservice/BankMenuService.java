package menuservice;

import management.BankConnection;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class BankMenuService {


    public static int addBank(BankConnection bankManagement) throws SQLException, ClassNotFoundException {

        Optional<Integer> bankCreated = bankManagement.isBankCreated();

        if (bankCreated.isPresent()) {
            System.out.println("Bank is already create in database bank id - > " + bankCreated.get());
            return bankCreated.get();
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Bank name : ");
            String bankName = scanner.nextLine();

            System.out.print("Enter Bank address : ");
            String bankAddress = scanner.nextLine();

            System.out.print("Enter Bank IFSC : ");
            String bankIfsc = scanner.nextLine();
            return bankManagement.createBank(bankName, bankAddress, bankIfsc);
        }
    }
}
