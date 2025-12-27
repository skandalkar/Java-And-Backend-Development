import java.util.Scanner;

public class AccountServices {
    private static final Scanner sc =new Scanner(System.in);
    byte accountCount = 1;
    Account[] accounts = new Account[10];

    public AccountServices() {
        accounts[0] = new Account(101, "John Doe", 1500.00);
        accounts[1] = new Account(102, "Jane Smith", 5000.00);
        accounts[2] = new Account(103, "Peter Jones", 890.50);
        accounts[3] = new Account(104, "Mary Williams", 1250.75);
        accounts[4] = new Account(105, "Robert Brown", 1418.00);
    }

    public void accountCreation(){
        System.out.println();
        if (accountCount < accounts.length) {
            System.out.print("\t Enter Account Holder Name: ");
            String accountHolderName = sc.nextLine();
            System.out.print("\t Enter Initial Balance: ");
            double initialBalance = sc.nextDouble();
            int accNum = 100 + accountCount + 5; // Auto account number generation
            accounts[5] = new Account(accNum, accountHolderName, initialBalance);

            System.out.println("\n\t Account Details:");
            System.out.println("\t Account Holder Name: " + accountHolderName);
            System.out.println("\t Account Number: " + accNum);
            System.out.println("\t Balance: " + initialBalance);
            accountCount++;
        } else {
            System.out.println("\t Cannot create more accounts. Array is full.\n");
        }
    }

    public int checkUserIdentity() {
        Scanner scanner = new Scanner(System.in);
        int accountNum;
        System.out.print("\t Enter Account Number || Customer ID: ");
        accountNum = scanner.nextInt();

        for (Account acc : accounts) {
            if (accountNum == acc.getAccountNumber()) {
                int captcha = (int) (Math.random() * 100 + 15);

                System.out.println("\n\t Captch: " + captcha);
                System.out.print("\t Enter captch displaying :");
                int temp = scanner.nextInt();
                if (temp == captcha) {
                    System.out.println();
                    return accountNum;
                }
            }
        }
        return 0;
    }

    public void depositFunds(int accNo){
        System.out.print("\t Enter amount to deposit: ");
        double depositFund = sc.nextDouble();
        for (Account acc : accounts){
            if(accNo == acc.getAccountNumber()){
                acc.setBalance((acc.getBalance()+depositFund));
                System.out.println("\t Amount deposited successfully, Transaction done.");
                System.out.println("\t Available Balance : "+(acc.getBalance()));
                System.out.println();
                break;
            }
        }
    }

    public void withdrawFunds(int accNo){
        System.out.print("\t Enter Amount to withdraw: ");
        double withdrawAmount = sc.nextDouble();
        for (Account acc : accounts){
            if (accNo == acc.getAccountNumber()){
                if (acc.getBalance() > withdrawAmount){
                    double updateBal = acc.getBalance() - withdrawAmount;
                    acc.setBalance(updateBal);
                    System.out.println("\t Your transaction in process....");
                    System.out.println("\t Available Balance : "+(acc.getBalance()));
                    System.out.println();
                    break;
                }else {
                    System.out.println("\t Insufficient balance!\n");
                    break;
                }
            }else {
                System.out.println("\t Not found existing account!\n");
                break;
            }
        }
    }

    public void checkBalance(int accNo){
        for (Account acc : accounts){
            if (accNo == acc.getAccountNumber()){
                System.out.println("\t Account Number: "+accNo);
                System.out.println("\t Balance: "+acc.getBalance());
                System.out.println();
                break;
            }else {
                System.out.println("\t Not found existing account!\n");
            }
        }
    }
}