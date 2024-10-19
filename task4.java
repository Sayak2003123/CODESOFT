import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizApplication {

    static Scanner scanner = new Scanner(System.in);
    static int score = 0;
    static boolean timeOut = false;

    public static void main(String[] args) {
        String[][] questions = {
                {"What is the capital of France?", "A) Berlin", "B) Madrid", "C) Paris", "D) Rome", "C"},
                {"Which planet is known as the Red Planet?", "A) Earth", "B) Mars", "C) Jupiter", "D) Venus", "B"},
                {"Who wrote 'Hamlet'?", "A) Charles Dickens", "B) J.K. Rowling", "C) William Shakespeare", "D) Mark Twain", "C"}
        };

        System.out.println("Welcome to the Quiz!");
        for (int i = 0; i < questions.length; i++) {
            askQuestion(questions[i]);
        }

        System.out.println("Quiz Over!");
        System.out.println("Your final score is: " + score + "/" + questions.length);
        scanner.close();
    }

    public static void askQuestion(String[] question) {
        System.out.println("\n" + question[0]);
        System.out.println(question[1]);
        System.out.println(question[2]);
        System.out.println(question[3]);
        System.out.println(question[4]);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                timeOut = true;
                System.out.println("\nTime's up! Moving to the next question.");
                timer.cancel();
            }
        };

        timer.schedule(task, 10000); // 10 seconds to answer

        String userAnswer = "";
        if (!timeOut) {
            System.out.print("Enter your answer (A/B/C/D): ");
            userAnswer = scanner.next().toUpperCase();
        }

        timer.cancel();
        if (!timeOut) {
            if (userAnswer.equals(question[5])) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + question[5]);
            }
        }

        timeOut = false; 
    }
}
