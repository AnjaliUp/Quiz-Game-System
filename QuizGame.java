// QuizGame.java
import java.util.Scanner;
import java.util.List;

public class QuizGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Introduction and player registration
        System.out.println("Welcome to the Quiz Game!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your unique identifier (email or username): ");
        String identifier = scanner.nextLine();
        Player player = new Player(name, identifier);

        // Fetch quiz questions
        QuestionFetcher questionFetcher = new QuestionFetcher();
        List<Question> questions = questionFetcher.fetchQuestions();

        // Game loop: Ask 5 random questions
        int totalQuestions = 5;
        for (int i = 1; i <= totalQuestions; i++) {
            System.out.println("\nQuestion " + i + ": ");
            Question currentQuestion = questionFetcher.getRandomQuestion(questions);

            // Display the question and options
            currentQuestion.displayQuestion();

            // Ask for the player's answer
            System.out.print("Select your answer (1-4): ");
            int answer = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            // Check if the answer is correct
            if (currentQuestion.getOptions()[answer - 1].equals(currentQuestion.getCorrectAnswer())) {
                System.out.println("Correct!");
                player.increaseScore(10); // Increment score for correct answer
            } else {
                System.out.println("Incorrect! The correct answer was: " + currentQuestion.getCorrectAnswer());
            }
        }

        // End of game: Display the final score
        System.out.println("\nGame Over!");
        System.out.println("Your final score: " + player.getScore());
    }
}
