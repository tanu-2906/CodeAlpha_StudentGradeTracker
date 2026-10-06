package studentgradetracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeGUI extends JFrame {

    private JTextField rollField;
    private JTextField nameField;
    private JTextField[] marksFields;
    private JTextArea resultArea;

    private ArrayList<Student> students;

    public StudentGradeGUI() {

        students = new ArrayList<>();

        setTitle("Student Grade Tracker");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(
                "STUDENT GRADE TRACKER",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Manage student marks and generate grade reports",
                SwingConstants.CENTER
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 12, 12));
        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Student Information")
        );

        inputPanel.add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        inputPanel.add(rollField);

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        marksFields = new JTextField[5];

        for (int i = 0; i < 5; i++) {

            inputPanel.add(
                    new JLabel("Subject " + (i + 1) + " Marks:")
            );

            marksFields[i] = new JTextField();
            inputPanel.add(marksFields[i]);
        }

        // ===== RESULT AREA =====
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        resultArea.setBorder(
                BorderFactory.createTitledBorder("Report")
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        // Center panel
        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        JButton addButton =
                new JButton("Add Student");

        JButton summaryButton =
                new JButton("View Summary");

        JButton clearButton =
                new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(summaryButton);
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // ===== BUTTON ACTIONS =====

        addButton.addActionListener(e -> addStudent());

        summaryButton.addActionListener(e -> showSummary());

        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // ===== ADD STUDENT =====

    private void addStudent() {

        try {

            if (rollField.getText().trim().isEmpty()
                    || nameField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Roll Number and Name."
                );

                return;
            }

            int rollNo =
                    Integer.parseInt(rollField.getText());

            String name =
                    nameField.getText().trim();

            double[] marks = new double[5];

            for (int i = 0; i < 5; i++) {

                marks[i] =
                        Double.parseDouble(
                                marksFields[i].getText()
                        );

                if (marks[i] < 0 || marks[i] > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100."
                    );

                    return;
                }
            }

            Student student =
                    new Student(rollNo, name, marks);

            students.add(student);

            resultArea.setText(
                    "========================================\n"
                    + "           STUDENT REPORT\n"
                    + "========================================\n\n"
                    + "Roll Number : " + student.getRollNo() + "\n"
                    + "Name        : " + student.getName() + "\n\n"
                    + "Total Marks : "
                    + student.getTotalMarks()
                    + " / 500\n"
                    + String.format(
                            "Percentage  : %.2f%%\n",
                            student.getPercentage()
                    )
                    + "Grade       : "
                    + student.getGrade()
                    + "\n\n"
                    + "Student added successfully!"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!"
            );

            clearFields();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );
        }
    }

    // ===== SUMMARY =====

    private void showSummary() {

        if (students.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No students added yet."
            );

            return;
        }

        double totalPercentage = 0;
        double highest = -1;
        double lowest = 101;

        String highestStudent = "";
        String lowestStudent = "";

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n"
        );

        report.append(
                "           STUDENT REPORT\n"
        );

        report.append(
                "========================================\n\n"
        );

        for (Student student : students) {

            double percentage =
                    student.getPercentage();

            report.append(
                    "Roll Number : "
            ).append(student.getRollNo()).append("\n");

            report.append(
                    "Name        : "
            ).append(student.getName()).append("\n");

            report.append(
                    "Total Marks : "
            ).append(student.getTotalMarks())
                    .append("/500\n");

            report.append(
                    String.format(
                            "Percentage  : %.2f%%\n",
                            percentage
                    )
            );

            report.append(
                    "Grade       : "
            ).append(student.getGrade()).append("\n");

            report.append(
                    "----------------------------------------\n"
            );

            totalPercentage += percentage;

            if (percentage > highest) {

                highest = percentage;
                highestStudent =
                        student.getName();
            }

            if (percentage < lowest) {

                lowest = percentage;
                lowestStudent =
                        student.getName();
            }
        }

        double average =
                totalPercentage / students.size();

        report.append("\n");
        report.append(
                "============= SUMMARY =============\n\n"
        );

        report.append(
                String.format(
                        "Average Percentage : %.2f%%\n",
                        average
                )
        );

        report.append(
                String.format(
                        "Highest Percentage : %.2f%% (%s)\n",
                        highest,
                        highestStudent
                )
        );

        report.append(
                String.format(
                        "Lowest Percentage  : %.2f%% (%s)\n",
                        lowest,
                        lowestStudent
                )
        );

        report.append(
                "\nTotal Students     : "
        ).append(students.size());

        resultArea.setText(
                report.toString()
        );
    }

    // ===== CLEAR =====

    private void clearFields() {

        rollField.setText("");
        nameField.setText("");

        for (JTextField field : marksFields) {
            field.setText("");
        }
    }

    // ===== MAIN =====

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new StudentGradeGUI()
        );
    }
}

