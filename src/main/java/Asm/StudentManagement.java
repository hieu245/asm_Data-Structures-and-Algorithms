/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asm;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
/**
 *
 * @author admin
 */

public class StudentManagement {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Stack<Student> studentStack = new Stack<>();  // Định nghĩa stack để lưu trữ học sinh

    public static void addStudent(int id, String name, double marks) {
        Student newStudent = new Student(id, name, marks);
        students.add(newStudent);
        studentStack.push(newStudent);  // Đưa học sinh vào stack
        System.out.println("Student added successfully.");
    }

    public static boolean editStudent(int id, String newName, double newMarks) {
        for (Student student : students) {
            if (student.id == id) {
                student.name = newName;
                student.marks = newMarks;
                return true;
            }
        }
        return false;
    }

    public static void deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id == id) {
                students.remove(i);
                System.out.println("Student with ID " + id + " deleted successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public static void sortStudents(int sortOption) {
        if (sortOption == 1) {
            bubbleSort();  // Sắp xếp theo tên
        } else if (sortOption == 2) {
            mergeSort(0, students.size() - 1);  // Sắp xếp theo điểm
        } else {
            System.out.println("Invalid sorting option.");
            return;
        }
        displayAllStudents();
    }

    // Bubble Sort implementation: Sắp xếp theo tên
    public static void bubbleSort() {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (students.get(j).name.compareTo(students.get(j + 1).name) > 0) {
                    // Swap if the name is lexicographically greater
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        System.out.println("Sorted by Bubble Sort (Name).");
    }

    // Merge Sort implementation: Sắp xếp theo điểm
    public static void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Sort the left half
            mergeSort(left, mid);

            // Sort the right half
            mergeSort(mid + 1, right);

            // Merge the two halves
            merge(left, mid, right);
        }
    }

    private static void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Student> leftArray = new ArrayList<>(n1);
        ArrayList<Student> rightArray = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            leftArray.add(students.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            rightArray.add(students.get(mid + 1 + i));
        }

        int i = 0, j = 0;
        int k = left;

        // Merge the arrays, compare by marks (descending order)
        while (i < n1 && j < n2) {
            if (leftArray.get(i).marks >= rightArray.get(j).marks) {
                students.set(k, leftArray.get(i));
                i++;
            } else {
                students.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // Copy the remaining elements from leftArray
        while (i < n1) {
            students.set(k, leftArray.get(i));
            i++;
            k++;
        }

        // Copy the remaining elements from rightArray
        while (j < n2) {
            students.set(k, rightArray.get(j));
            j++;
            k++;
        }
        System.out.println("Sorted by Merge Sort (Marks).");
    }

    public static Student searchStudentById(int id) {
        for (Student student : students) {
            if (student.id == id) {
                return student;
            }
        }
        return null;
    }
    
    // Binary Search theo tên
public static Student binarySearchByName(String name) {
    int left = 0;
    int right = students.size() - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        Student midStudent = students.get(mid);
        int compareResult = midStudent.name.compareToIgnoreCase(name);

        if (compareResult == 0) {
            return midStudent; 
        } else if (compareResult < 0) {
            left = mid + 1; 
        } else {
            right = mid - 1;  
        }
    }
    return null;  
}


    public static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }
        System.out.println("Student List:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static int inputStudentId(Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Enter ID (integer): ");
            try {
                id = scanner.nextInt();
                if (isIdUnique(id)) {
                    break;
                } else {
                    System.out.println("ID " + id + " already exists. Please enter a different ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. ID must be an integer.");
                scanner.nextLine();
            }
        }
        return id;
    }

    private static boolean isIdUnique(int id) {
        for (Student student : students) {
            if (student.id == id) {
                return false;
            }
        }
        return true;
    }

    public static int inputExistingStudentId(Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Enter ID to edit (must exist): ");
            try {
                id = scanner.nextInt();
                if (!isIdUnique(id)) {
                    break;
                } else {
                    System.out.println("ID " + id + " does not exist. Please enter a valid ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. ID must be an integer.");
                scanner.nextLine();
            }
        }
        return id;
    }

    public static double inputStudentMarks(Scanner scanner) {
        double marks;
        while (true) {
            System.out.print("Enter Marks (0 to 10): ");
            try {
                marks = scanner.nextDouble();
                if (marks >= 0 && marks <= 10) {
                    break;
                } else {
                    System.out.println("Invalid input. Marks must be between 0 and 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Marks must be a number from 0 to 10.");
                scanner.nextLine();
            }
        }
        return marks;
    }

    // Phương thức kiểm tra tính hợp lệ của tên
    public static boolean isValidName(String name) {
        // Kiểm tra nếu tên không chứa ký tự số và chỉ có chữ cái
        return name.matches("[a-zA-Z]+");
    }

    // Đẩy thông tin học sinh vào stack khi cần thiết (Chức năng undo)
    public static void undoLastAction() {
        if (!studentStack.isEmpty()) {
            Student lastStudent = studentStack.pop();
            System.out.println("Last student removed: " + lastStudent);
            students.remove(lastStudent);  // Loại bỏ học sinh khỏi danh sách
        } else {
            System.out.println("No action to undo.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n----- Student Management System -----");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students");
            System.out.println("5. Search Student");
            System.out.println("6. Display All Students");
            System.out.println("7. Undo Last Action");  // Tùy chọn để undo
            System.out.println("8. Exit");

            int choice = -1;
            while (choice < 1 || choice > 8) {
                System.out.print("Select an option: ");
                try {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > 8) {
                        System.out.println("Invalid choice. Please choose a valid option (1-8).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please choose a valid option (1-8).");
                    scanner.nextLine(); // Clear buffer
                }
            }

            switch (choice) {
                case 1:
                    int id = inputStudentId(scanner);
                    scanner.nextLine();  // Clear the newline character
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    if (!isValidName(name)) {
                        System.out.println("Invalid name. Please enter a valid name.");
                        break;
                    }
                    double marks = inputStudentMarks(scanner);
                    addStudent(id, name, marks);
                    break;
                case 2:
                    id = inputExistingStudentId(scanner);
                    scanner.nextLine();  // Clear the newline character
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    if (!isValidName(newName)) {
                        System.out.println("Invalid name. Please enter a valid name.");
                        break;
                    }
                    double newMarks = inputStudentMarks(scanner);
                    if (editStudent(id, newName, newMarks)) {
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Student ID to delete: ");
                    id = scanner.nextInt();
                    deleteStudent(id);
                    break;
                case 4:
                    System.out.println("1. Bubble Sort by Name");
                    System.out.println("2. Merge Sort by Marks");
                    int sortOption = scanner.nextInt();
                    sortStudents(sortOption);
                    break;
     case 5:
    System.out.println("1. Search by ID");
    System.out.println("2. Search by Name");
    int searchOption = scanner.nextInt();
    scanner.nextLine();  // Clear newline

    if (searchOption == 1) {
        System.out.print("Enter Student ID to search: ");
        id = scanner.nextInt();
        Student studentById = searchStudentById(id);
        if (studentById != null) {
            System.out.println(studentById);
        } else {
            System.out.println("Student not found.");
        }
    } else if (searchOption == 2) {
        System.out.print("Enter Student Name to search: ");
        String searchName = scanner.nextLine();
        // Đảm bảo sắp xếp danh sách trước khi tìm kiếm
        bubbleSort();  // Đây là lý do hiển thị thông báo sắp xếp
        Student studentByName = binarySearchByName(searchName);
        if (studentByName != null) {
            System.out.println(studentByName);
        } else {
            System.out.println("Student not found.");
        }
    } else {
        System.out.println("Invalid search option.");
    }
    break;



                case 6:
                    displayAllStudents();
                    break;
                case 7:
                    undoLastAction();  // Thực hiện undo
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting... Thank you!");
                    break;
            }
        }

        scanner.close();
    }
}