import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /// This is a Console-based Quiz Application a learning project.

        System.out.println("\t********** Welcome to Quiz Application **********");
        Scanner sc = new Scanner(System.in);
        String name, role;

        System.out.print("\n\tEnter your Role (Role: Trainer || Student): "); //role: Trainer || Student
        role = sc.nextLine();

        if (role.equals("Trainer")){
            System.out.println("\tYou don't have access yet!");
        }else if (role.equals("Student")){
            System.out.print("\tEnter your name: ");
            name = sc.nextLine();
            String ch;
            System.out.println("\tAre you sure want to start quiz ?\n\t1) Yes \t\t2) No");
            System.out.print("\tYour answer: ");
            ch = sc.nextLine();
            if(ch.equals("Yes")){
                System.out.println("\tBest of Luck!");
                QuestionService service = new QuestionService();

                //start the quiz
                service.startQuiz();

                //score of quiz
                service.calculateScore();
                System.out.println("\n\tCongratulation, "+name+" you did it.");
                System.out.println("\tThanks for using our service.");

            }else{
                System.out.println("\n\tThanks for using our service.");
            }
        }
    }
}