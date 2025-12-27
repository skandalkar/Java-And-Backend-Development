import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\t ********** Welcome To Banking Application **********");
        System.out.print("\n\t 1. Admin \n\t 2. Customer");
        System.out.print("\n\t Enter the option : ");
        byte role = sc.nextByte();
        if (role == 2) {
            AccountServices accountServices = new AccountServices();

            /* Menu */
            byte option;
            do{
                System.out.println("\n\t 1. Account Creation and Opening");
                System.out.println("\t 2. Deposit Funds");
                System.out.println("\t 3. Withdraw Funds");
                System.out.println("\t 4. Check Balance");
                System.out.println("\t 5. Exit");

                System.out.print("\n\t Enter option : ");
                option = sc.nextByte();

                int accountNo;
                switch (option) {

                    case 1:
                        accountServices.accountCreation();
                        break;

                    case 2:
                        accountNo = accountServices.checkUserIdentity();
                        accountServices.depositFunds(accountNo);
                        break;

                    case 3:
                        accountNo = accountServices.checkUserIdentity();
                        accountServices.withdrawFunds(accountNo);
                        break;

                    case 4:
                        accountNo = accountServices.checkUserIdentity();
                        accountServices.checkBalance(accountNo);
                        break;

                    case 5:
                        System.out.println("\t ....Thank you....");
                        break;

                    default:
                        System.out.println("\t Invalid Input.");
                }
            }while(option != 5);

        } else {
            System.out.println("\t Functionality yet to implement");
        }
    }
}