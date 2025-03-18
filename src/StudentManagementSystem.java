import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem extends JFrame {
    private JTextField studentNameField;
    private JTextField studentIdField;
    private JButton addStudentButton;
    private JButton updateStudentButton;
    private JButton viewStudentDetailsButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> courseDropdown;
    private JList<String> studentList;
    private JButton enrollButton;

    private JComboBox<String> studentDropdown;
    private JComboBox<String> courseDropdownForGrades;
    private JTextField gradeField;
    private JButton assignGradeButton;

    private List<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();

        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        studentNameField = new JTextField(20);
        studentIdField = new JTextField(20);
        addStudentButton = new JButton("Add Student");
        updateStudentButton = new JButton("Update Student");
        viewStudentDetailsButton = new JButton("View Student Details");

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Student ID", "Student Name"}, 0);
        studentTable = new JTable(tableModel);

        // Course enrollment components
        courseDropdown = new JComboBox<>(new String[]{"Course 1", "Course 2", "Course 3"});
        studentList = new JList<>(new DefaultListModel<>());
        enrollButton = new JButton("Enroll");

        // Grade management components
        studentDropdown = new JComboBox<>(new String[]{"Student 1", "Student 2", "Student 3"});
        courseDropdownForGrades = new JComboBox<>(new String[]{"Course 1", "Course 2", "Course 3"});
        gradeField = new JTextField(5);
        assignGradeButton = new JButton("Assign Grade");

        // Create panels
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(studentNameField);
        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdField);
        inputPanel.add(addStudentButton);
        inputPanel.add(updateStudentButton);
        inputPanel.add(viewStudentDetailsButton);

        JPanel tablePanel = new JPanel();
        tablePanel.add(new JScrollPane(studentTable));

        JPanel enrollmentPanel = new JPanel();
        enrollmentPanel.add(new JLabel("Select Course:"));
        enrollmentPanel.add(courseDropdown);
        enrollmentPanel.add(new JLabel("Select Student:"));
        enrollmentPanel.add(new JScrollPane(studentList));
        enrollmentPanel.add(enrollButton);

        JPanel gradePanel = new JPanel();
        gradePanel.add(new JLabel("Select Student:"));
        gradePanel.add(studentDropdown);
        gradePanel.add(new JLabel("Select Course:"));
        gradePanel.add(courseDropdownForGrades);
        gradePanel.add(new JLabel("Enter Grade:"));
        gradePanel.add(gradeField);
        gradePanel.add(assignGradeButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(enrollmentPanel, BorderLayout.SOUTH);
        add(gradePanel, BorderLayout.EAST);

        // Add event handlers
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle adding student
                String name = studentNameField.getText();
                String id = studentIdField.getText();
                if (name.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "Please enter both name and ID.");
                } else {
                    students.add(new Student(id, name));
                    updateTable();
                    updateStudentList();
                    updateStudentDropdown();
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "Student added: " + name + " (ID: " + id + ")");
                }
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle updating student
                String id = studentIdField.getText();
                for (Student student : students) {
                    if (student.getId().equals(id)) {
                        student.setName(studentNameField.getText());
                        updateTable();
                        updateStudentList();
                        updateStudentDropdown();
                        JOptionPane.showMessageDialog(StudentManagementSystem.this, "Student updated: " + student.getName() + " (ID: " + id + ")");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(StudentManagementSystem.this, "Student ID not found: " + id);
            }
        });

        viewStudentDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle viewing student details
                updateTable();
            }
        });

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseDropdown.getSelectedItem();
                String selectedStudent = studentList.getSelectedValue();
                if (selectedStudent != null) {
                    // Enroll student in the course
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "Student " + selectedStudent + " enrolled in " + selectedCourse);
                } else {
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "No student selected for enrollment.");
                }
            }
        });

        assignGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudent = (String) studentDropdown.getSelectedItem();
                String selectedCourse = (String) courseDropdownForGrades.getSelectedItem();
                String grade = gradeField.getText();
                if (!grade.isEmpty()) {
                    // Assign grade to student
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "Grade " + grade + " assigned to " + selectedStudent + " for " + selectedCourse);
                } else {
                    JOptionPane.showMessageDialog(StudentManagementSystem.this, "Please enter a grade.");
                }
            }
        });
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getId(), student.getName()});
        }
    }

    private void updateStudentList() {
        DefaultListModel<String> listModel = (DefaultListModel<String>) studentList.getModel();
        listModel.clear();
        for (Student student : students) {
            listModel.addElement(student.getName());
        }
    }

    private void updateStudentDropdown() {
        studentDropdown.removeAllItems();
        for (Student student : students) {
            studentDropdown.addItem(student.getName());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementSystem().setVisible(true);
            }
        });
    }
}

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}