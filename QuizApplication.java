// import java.util.*;
// import java.util.concurrent.*;

// class QuizQuestion {
//     private String question;
//     private List<String> options;
//     private int correctOptionIndex;

//     public QuizQuestion(String question, List<String> options, int correctOptionIndex) {
//         this.question = question;
//         this.options = options;
//         this.correctOptionIndex = correctOptionIndex;
//     }

//     public String getQuestion() {
//         return question;
//     }

//     public List<String> getOptions() {
//         return options;
//     }

//     public int getCorrectOptionIndex() {
//         return correctOptionIndex;
//     }

//     public String getCorrectOption() {
//         return options.get(correctOptionIndex);
//     }
// }

// public class QuizApplication {
//     private static List<QuizQuestion> questions;
//     private static Scanner scanner;
//     private static int score;
//     private static int totalTime;

//     public static void main(String[] args) {
//         initializeQuestions();

//         scanner = new Scanner(System.in);
//         score = 0;
//         totalTime = 0;

//         for (int i = 0; i < questions.size(); i++) {
//             QuizQuestion question = questions.get(i);
//             System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
//             List<String> options = question.getOptions();
//             for (int j = 0; j < options.size(); j++) {
//                 System.out.println((j + 1) + ". " + options.get(j));
//             }
//             System.out.print("Enter your answer (1-" + options.size() + "): ");

//             ExecutorService executor = Executors.newSingleThreadExecutor();
//             Future<String> future = executor.submit(() -> scanner.nextLine());
//             long startTime = System.currentTimeMillis();
//             String answer = "";
//             try {
//                 answer = future.get(5, TimeUnit.SECONDS); // Timeout for 10 seconds
//             } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                 System.out.println("Time's up!");
//                 executor.shutdownNow();
//                 break;
//             } finally {
//                 long endTime = System.currentTimeMillis();
//                 totalTime += (endTime - startTime) / 1000;
//             }

//             executor.shutdownNow();

//             int selectedOptionIndex = Integer.parseInt(answer) - 1;
//             if (selectedOptionIndex == question.getCorrectOptionIndex()) {
//                 System.out.println("Correct!");
//                 score++;
//             } else {
//                 System.out.println("Incorrect!");
//                 System.out.println("Correct Option: " + question.getCorrectOption());
//             }
//             System.out.println();
//         }

//         System.out.println("Quiz completed!");
//         System.out.println("Your score: " + score + "/" + questions.size());
//         System.out.println("Total time taken: " + totalTime + " seconds");
//     }

//     private static void initializeQuestions() {
//         questions = new ArrayList<>();

//         // Adding sample questions
//         List<String> options1 = Arrays.asList("Paris", "Rome", "Berlin", "London");
//         QuizQuestion question1 = new QuizQuestion("What is the capital of France?", options1, 0);
//         questions.add(question1);

//         List<String> options2 = Arrays.asList("Jupiter", "Saturn", "Earth", "Mars");
//         QuizQuestion question2 = new QuizQuestion("Which is the largest planet in the solar system?", options2, 1);
//         questions.add(question2);

//         List<String> options3 = Arrays.asList("H20", "CO2", "CH4", "O2");
//         QuizQuestion question3 = new QuizQuestion("What is the chemical symbol for water?", options3, 0);
//         questions.add(question3);
//     }
// }
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;
import java.util.concurrent.*;

class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctOptionIndex;

    public QuizQuestion(String question, List<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getCorrectOption() {
        return options.get(correctOptionIndex);
    }
}

public class QuizApplication extends Application {
    private List<QuizQuestion> questions;
    private int score;
    private int totalTime;

    @Override
    public void start(Stage primaryStage) {
        initializeQuestions();

        score = 0;
        totalTime = 0;

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label questionLabel = new Label();
        ToggleGroup optionsToggleGroup = new ToggleGroup();
        VBox optionsBox = new VBox(5);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            RadioButton selectedRadioButton = (RadioButton) optionsToggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                int selectedOptionIndex = Integer.parseInt(selectedRadioButton.getId()) - 1;
                int correctOptionIndex = questions.get(score).getCorrectOptionIndex();
                if (selectedOptionIndex == correctOptionIndex) {
                    score++;
                }
                optionsBox.getChildren().clear();
                optionsToggleGroup.getSelectedToggle().setSelected(false);
                if (score < questions.size()) {
                    displayQuestion(score, questionLabel, optionsBox, optionsToggleGroup);
                } else {
                    endQuiz(primaryStage);
                }
            }
        });

        root.getChildren().addAll(questionLabel, optionsBox, submitButton);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz Application");
        primaryStage.show();

        displayQuestion(score, questionLabel, optionsBox, optionsToggleGroup);
    }

    private void displayQuestion(int questionIndex, Label questionLabel, VBox optionsBox, ToggleGroup toggleGroup) {
        QuizQuestion question = questions.get(questionIndex);
        questionLabel.setText(question.getQuestion());
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            RadioButton optionRadioButton = new RadioButton(options.get(i));
            optionRadioButton.setId(Integer.toString(i + 1));
            optionRadioButton.setToggleGroup(toggleGroup);
            optionsBox.getChildren().add(optionRadioButton);
        }
    }

    private void endQuiz(Stage primaryStage) {
        Label resultLabel = new Label("Quiz completed!\nYour score: " + score + "/" + questions.size() + "\nTotal time taken: " + totalTime + " seconds");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());
        VBox endRoot = new VBox(10);
        endRoot.getChildren().addAll(resultLabel, closeButton);
        endRoot.setPadding(new Insets(20));
        primaryStage.getScene().setRoot(endRoot);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // Adding sample questions
        List<String> options1 = Arrays.asList("Paris", "Rome", "Berlin", "London");
        QuizQuestion question1 = new QuizQuestion("What is the capital of France?", options1, 0);
        questions.add(question1);

        List<String> options2 = Arrays.asList("Jupiter", "Saturn", "Earth", "Mars");
        QuizQuestion question2 = new QuizQuestion("Which is the largest planet in the solar system?", options2, 0);
        questions.add(question2);

        List<String> options3 = Arrays.asList("H2O", "CO2", "CH4", "O2");
        QuizQuestion question3 = new QuizQuestion("What is the chemical symbol for water?", options3, 0);
        questions.add(question3);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
