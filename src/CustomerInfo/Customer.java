package CustomerInfo;

public class Customer {
    private String name;
    private String ACNo;
    private String ACType;
    private String Balance;
    private String DOB;
    private String Address;

    public Customer(String name, String ACNo, String ACType, String balance, String DOB, String address) {
        this.name = name;
        this.ACNo = ACNo;
        this.ACType = ACType;
        Balance = balance;
        this.DOB = DOB;
        Address = address;
    }

    public String getName() {
        return name;
    }

    public String getACNo() {
        return ACNo;
    }

    public String getACType() {
        return ACType;
    }

    public String getBalance() {
        return Balance;
    }

    public String getDOB() {
        return DOB;
    }

    public String getAddress() {
        return Address;
    }
}
