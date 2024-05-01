import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input: Take marks obtained (out of 100) in each subject
        System.out.print("Enter the number of subjects:");
        int numSubjects = scanner.nextInt();
        
        int totalMarks = 0;
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks obtained in subject(out of 100) " + i + ":");
            int marks = scanner.nextInt();
            if (marks<=100) {
                totalMarks += marks;
            } else {
                System.err.println("Invalid Input!");
                i--;
            }
        }
        
        // Calculate Total Marks
        System.out.println("Total Marks: " + totalMarks);
        
        // Calculate Average Percentage
        double averagePercentage = (double) totalMarks / numSubjects;
        System.out.println("Average Percentage: " + averagePercentage);
        
        // Grade Calculation
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
        
        // Display Results
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }
}
