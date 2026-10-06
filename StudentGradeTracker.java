package studentgradetracker;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        int subjects = 5;

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for Student " + (i + 1));

            System.out.print("Roll No: ");
            int rollNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            double[] marks = new double[subjects];

            for (int j = 0; j < subjects; j++) {
                System.out.print("Enter marks for Subject " + (j + 1) + ": ");
                marks[j] = sc.nextDouble();
            }

            students.add(new Student(rollNo, name, marks));
        }

        System.out.println("\n========== STUDENT REPORT ==========");

        double totalPercentage = 0;
        double highest = -1;
        double lowest = 101;

        String highestStudent = "";
        String lowestStudent = "";

        for (Student student : students) {

            double percentage = student.getPercentage();

            System.out.println("\nRoll No: " + student.getRollNo());
            System.out.println("Name: " + student.getName());

            System.out.println("Total Marks: " 
                    + student.getTotalMarks() + "/" + (subjects * 100));

            System.out.printf("Percentage: %.2f%%\n", percentage);

            System.out.println("Grade: " + student.getGrade());

            totalPercentage += percentage;

            if (percentage > highest) {
                highest = percentage;
                highestStudent = student.getName();
            }

            if (percentage < lowest) {
                lowest = percentage;
                lowestStudent = student.getName();
            }
        }

        double average = totalPercentage / students.size();

        System.out.println("\n========== SUMMARY ==========");

        System.out.printf("Average Percentage: %.2f%%\n", average);

        System.out.printf("Highest Percentage: %.2f%% (%s)\n",
                highest, highestStudent);

        System.out.printf("Lowest Percentage: %.2f%% (%s)\n",
                lowest, lowestStudent);

        sc.close();
    }
}               