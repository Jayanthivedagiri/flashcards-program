import java.io.*;
import java.util.*;

class Flashcard {
    private String question;
    private String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void displayFlashcard() {
        System.out.println("Question: " + question);
        System.out.println("Answer: " + answer);
    }
}

public class FlashcardApp {
    private static final String FILE_NAME = "flashcards.dat";
    private static List<Flashcard> flashcards = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadFlashcards();
        int choice;
        do {
            System.out.println("Flashcard Study App");
            System.out.println("1. Create Flashcard");
            System.out.println("2. Review Flashcards");
            System.out.println("3. Save and Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // To consume newline character

            switch (choice) {
                case 1:
                    createFlashcard();
                    break;
                case 2:
                    reviewFlashcards();
                    break;
                case 3:
                    saveFlashcards();
                    System.out.println("Flashcards saved! Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    // Create a new flashcard
    private static void createFlashcard() {
        System.out.print("Enter the question: ");
        String question = scanner.nextLine();
        System.out.print("Enter the answer: ");
        String answer = scanner.nextLine();
        Flashcard flashcard = new Flashcard(question, answer);
        flashcards.add(flashcard);
        System.out.println("Flashcard created successfully!");
    }

    // Review the flashcards
    private static void reviewFlashcards() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards to review.");
            return;
        }
        System.out.println("\nFlashcards Review:");
        for (int i = 0; i < flashcards.size(); i++) {
            Flashcard flashcard = flashcards.get(i);
            System.out.println("Flashcard " + (i + 1));
            flashcard.displayFlashcard();
            System.out.println("Press Enter to see next...");
            scanner.nextLine(); // Wait for user input to move to next flashcard
        }
    }

    // Save the flashcards to a file
    private static void saveFlashcards() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(flashcards);
            System.out.println("Flashcards saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving flashcards: " + e.getMessage());
        }
    }

    // Load the flashcards from a file
    private static void loadFlashcards() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            flashcards = (List<Flashcard>) in.readObject();
            System.out.println("Flashcards loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous flashcards found. Starting a new session.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading flashcards: " + e.getMessage());
        }
    }
}