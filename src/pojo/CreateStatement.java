package pojo;

public class CreateStatement {
    private String firstName;
    private String userName;
    private int accountNumber;
    private String date;
    private String transactionType;
    private int amount;
    private int balance;

    public CreateStatement(String firstName, String userName, int accountNumber, String date, String transactionType, int amount, int balance) {
        this.firstName = firstName;
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.date = date;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return
                "firstName='" + firstName + '\'' +
                        ", userName='" + userName + '\'' +
                        ", accountNumber=" + accountNumber +
                        ", date='" + date + '\'' +
                        ", transactionType='" + transactionType + '\'' +
                        ", amount=" + amount +
                        ", balance=" + balance;

    }
}
