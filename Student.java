package studentgradetracker;
public class Student {

    private int rollNo;
    private String name;
    private double[] marks;

    public Student(int rollNo, String name, double[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public double getTotalMarks() {
        double total = 0;

        for (double mark : marks) {
            total += mark;
        }

        return total;
    }

    public double getPercentage() {
        return getTotalMarks() / marks.length;
    }

    public char getGrade() {
        double percentage = getPercentage();

        if (percentage >= 90) {
            return 'A';
        } else if (percentage >= 80) {
            return 'B';
        } else if (percentage >= 70) {
            return 'C';
        } else if (percentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public double[] getMarks() {
        return marks;
    }
}
