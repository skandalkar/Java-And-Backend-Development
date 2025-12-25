import java.util.Objects;
import java.util.Scanner;

public class QuestionService {
    Question[] questions = new Question[20];
    String[] correctOption = new String[20];

    /** There are basic 20 fundamental questions based on the core Java. */
    public QuestionService(){
        questions[0] = new Question(1, "Which of these is the correct way to declare a main method?", "public void main(String[] args)", "static void main(String args)", "public static void main(String[] args)", "public static int main(String[] args)", "public static void main(String[] args)", 'c');
        questions[1] = new Question(2, "What is the default value of a boolean variable in Java?", "true", "false", "null", "0", "false", 'b');
        questions[2] = new Question(3, "Which data type is used to create a variable that should store text?", "String", "myString", "char", "Txt", "String", 'a');
        questions[3] = new Question(4, "Which operator is used to compare two values for equality?", "=", "==", "<>", "===", "==", 'b');
        questions[4] = new Question(5, "How do you create a variable with the numeric value 5?", "num x = 5;", "int x = 5;", "x = 5;", "float x = 5;", "int x = 5", 'b');
        questions[5] = new Question(6, "What is the purpose of the 'break' statement in a loop?", "Skip iteration", "Exit the loop", "Restart loop", "Exit program", "Exit the loop", 'b');
        questions[6] = new Question(7, "Which keyword is used to create an instance of a class?", "class", "new", "create", "alloc", "new", 'b');
        questions[7] = new Question(8, "What is the index of the first element in a Java array?", "1", "0", "-1", "Any integer", "0", 'b');
        questions[8] = new Question(9, "Which of these is NOT a primitive data type in Java?", "int", "boolean", "String", "double", "String", 'c');
        questions[9] = new Question(10, "Which loop is guaranteed to execute at least once?", "for loop", "while loop", "do-while loop", "foreach loop", "do-while loop", 'c');
        questions[10] = new Question(11, "What is the result of 10 % 3 in Java?", "3", "1", "0", "3.33", "1", 'b');
        questions[11] = new Question(12, "Which access modifier makes a member accessible only within its class?", "public", "protected", "private", "default", "private", 'c');
        questions[12] = new Question(13, "How do you find the length of a String named 'str'?", "str.length", "str.length()", "str.size()", "length(str)", "str.length()", 'b');
        questions[13] = new Question(14, "Which keyword is used to call the parent class constructor?", "this", "parent", "super", "extends", "super", 'c');
        questions[14] = new Question(15, "What does the 'static' keyword signify for a variable?", "Constant value", "Belongs to class", "Private access", "Stored in stack", "Belongs to class", 'b');
        questions[15] = new Question(16, "Which keyword is used to inherit a class in Java?", "implements", "extends", "inherits", ":", "extends", 'b');
        questions[16] = new Question(17, "What happens when accessing an index equal to array length?", "Returns null", "Returns 0", "Throws Exception", "Crashes", "Throws Exception", 'c');
        questions[17] = new Question(18, "Which keyword prevents a class from being inherited?", "static", "final", "private", "abstract", "final", 'b');
        questions[18] = new Question(19, "What is Method Overloading?", "Same name, different params", "Redefining in subclass", "Same signature", "Calling other class", "Same name, different params", 'a');
        questions[19] = new Question(20, "Which class is used to read console input in Java?", "System.out", "Scanner", "Printer", "InputReader", "Scanner", 'b');
    }

    public void startQuiz(){
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        for (Question que : questions){
            System.out.println();
            System.out.println("\tQuestion no. "+que.getQuestionId());
            System.out.println("\t"+que.getQuestion());
            System.out.println("\n\ta) "+que.getOption1()+"\t\tb) "+que.getOption2());
            System.out.println("\tc) "+que.getOption3()+"\t\td) "+que.getOption4());
            System.out.print("\n\tSelect option: ");
            correctOption[i] = scanner.nextLine();
            i++;
        }
        System.out.println("\tYour answers:");
        System.out.print("\t{");
        for (int j = 0; j < correctOption.length; j++){
            System.out.print(" Q."+(j+1)+": "+correctOption[j]+",");
        }
        System.out.print(" }");
    }

    public void calculateScore(){
        int studentScore = 0;
        for (int i = 0; i < questions.length; i++){
            Question que = questions[i];
            String actualAnswer = String.valueOf(que.getOptionCorrect()); // correctAnswer of the ith respective question
            String userAnswer = correctOption[i]; // user selected answer for that question

            if (userAnswer.equals(actualAnswer)){
                studentScore++;
            }
        }
        System.out.println("\n\n\tScore : "+studentScore+" /20");
    }
}