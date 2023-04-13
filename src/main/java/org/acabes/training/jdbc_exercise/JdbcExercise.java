package org.acabes.training.jdbc_exercise;

import org.acabes.training.Exercise;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdbcExercise implements Exercise {
    private final StudentsTable table;
    private final Scanner scanner;
    private final MyConnection connection;

    public JdbcExercise() {
        scanner = new Scanner(System.in);
        connection = new MyConnection();
        table = new StudentsTable(connection);
    }

    @Override
    public void solve() {
        do {
            promptChoice();
        } while (yesNoPrompt("again?"));

        closeSystem();
    }

    void promptChoice() {
        printSystemPrompt();
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addStudentChoice();
                break;
            case 2:
                updateStudentChoice();
                break;
            case 3:
                deleteStudentChoice();
                break;
            case 4:
                showAllStudentsChoice();
                break;
            case 5:
                closeSystem();
            default:
                System.out.println("invalid choice, try again.");
        }
    }

    void printSystemPrompt() {
        System.out.println(
                String.join("\n",
                        "=======================",
                        "1- Add Student",
                        "2- Update Student",
                        "3- Delete Student",
                        "4- Show All Students",
                        "5- Quit",
                        "=======================",
                        "Please Enter your Choice:\n"
                )
        );
    }

    void closeSystem() {
        connection.close();
        System.out.println("Good bye!");
        System.exit(0);
    }

    int promptId() {
        while (true) {
            System.out.print("Enter student ID: ");
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID.");
                scanner.next(); // clear scanner
            }
        }
    }

    String promptName() {
        System.out.print("Enter student name: ");
        return scanner.next();
    }

    double promptAverage() {
        while (true) {
            System.out.print("Enter student average: ");
            try {
                double average = scanner.nextDouble();

                if (average >= 0 && average <= 4) {
                    return average;
                } else {
                    System.out.println("Invalid average. Averages must be between 0.0 and 4.0");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid average.");
                scanner.next(); // clear scanner
            }
        }
    }

    private static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    String promptEmail() {
        while (true) {
            System.out.print("Enter student Email: ");
            String input = scanner.next();

            if (validateEmail(input)) {
                return input;
            } else {
                System.out.println("Invalid Email Address.");
            }
        }
    }

    void addStudentChoice() {
        int id = promptId();
        String name = promptName();
        double average = promptAverage();
        String email = promptEmail();

        InsertFeedback feedback = table.addStudent(new Student(id, name, average, email));
        if (feedback == InsertFeedback.SUCCESS) {
            System.out.println("Student was added successfully!");
        } else if (feedback == InsertFeedback.DUPLICATE) {
            System.out.println("ID already exists!");
        } else {
            System.out.println("Transaction failed for unknown reason.");
        }
    }

    void updateStudentChoice() {
        int id = promptId();

        Result result = table.queryStudentById(id);
        if (result.exists()) {
            result.print();
            String name = yesNoPrompt("update name?") ? promptName() : null;
            double average = yesNoPrompt("update average?") ? promptAverage() : -1;
            String email = yesNoPrompt("update email?") ? promptEmail() : null;

            boolean updated = table.updateStudent(new Student(id, name, average, email));
            System.out.println(updated ? "Student info was updated successfully!" : "No update happened!");
        }
    }

    void showAllStudentsChoice() {
        table.showAllStudents();
    }

    void deleteStudentChoice() {
        int id = promptId();
        Result result = table.queryStudentById(id);
        if (result.exists()) {
            result.print();
            promptDelete(id);
        } else {
            System.out.println("No student with id: " + id);
        }
    }

    void promptDelete(int id) {
        boolean prompt = yesNoPrompt("Are you sure?");
        if (prompt) {
            boolean deleted = table.deleteStudent(id);
            System.out.println(deleted ? "Student deleted." : "Student was not deleted.");
        } else {
            System.out.println("Canceled.");
        }
    }

    /**
     * Prompt the user to enter Y or N.
     *
     * @param question Yes/No questions.
     * @return true if the answer is Yes, false if the answer is No.
     */
    boolean yesNoPrompt(String question) {
        while (true) {
            System.out.printf("%s (Y/N): ", question);
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("Y")) {
                return true;
            } else if (answer.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid answer. Please enter Y or N.");
            }
        }
    }
}