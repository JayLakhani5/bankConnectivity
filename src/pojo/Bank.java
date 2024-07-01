package pojo;

public class Bank {
    private String bankName;
    private String address;
    private String ifsc;


    public Bank(String bankName, String address, String ifsc) {
        this.bankName = bankName;
        this.address = address;
        this.ifsc = ifsc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}

