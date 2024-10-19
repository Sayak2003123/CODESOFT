import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Course {
    String code, title, description, schedule;
    int capacity, enrolled;

    public Course(String code, String title, String description, String schedule, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.schedule = schedule;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public boolean hasAvailableSlots() {
        return enrolled < capacity;
    }

    public void registerStudent() {
        enrolled++;
    }

    public void removeStudent() {
        enrolled--;
    }

    @Override
    public String toString() {
        return code + ": " + title + " | " + description + " | Schedule: " + schedule + " | Slots: " + (capacity - enrolled) + "/" + capacity;
    }
}

// Class to represent a Student
class Student {
    String id, name;
    List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    public void displayRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("Registered Courses:");
            for (Course course : registeredCourses) {
                System.out.println(course);
            }
        }
    }
}

class RegistrationSystem {
    static HashMap<String, Course> courseCatalog = new HashMap<>();
    static HashMap<String, Student> studentDatabase = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        populateCourseCatalog();

        System.out.println("Welcome to the Student Course Registration System!");
        while (true) {
            System.out.println("\n1. View Course Catalog");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    displayCourseCatalog();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    viewRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    public static void populateCourseCatalog() {
        courseCatalog.put("CS101", new Course("CS101", "Introduction to Programming", "Learn the basics of programming.", "MWF 10:00-11:00", 30));
        courseCatalog.put("MTH102", new Course("MTH102", "Calculus I", "Introduction to differential calculus.", "TTh 14:00-15:30", 25));
        courseCatalog.put("PHY103", new Course("PHY103", "Physics I", "Classical mechanics and thermodynamics.", "MWF 11:00-12:00", 40));
    }

    public static void displayCourseCatalog() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseCatalog.values()) {
            System.out.println(course);
        }
    }
    public static void registerForCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = getOrCreateStudent(studentID);

        displayCourseCatalog();
        System.out.print("\nEnter the Course Code you want to register for: ");
        String courseCode = scanner.nextLine();

        Course course = courseCatalog.get(courseCode);
        if (course != null) {
            if (course.hasAvailableSlots()) {
                student.registerCourse(course);
                course.registerStudent();
                System.out.println("Successfully registered for " + course.title);
            } else {
                System.out.println("Sorry, no available slots for this course.");
            }
        } else {
            System.out.println("Invalid course code.");
        }
    }

    public static void dropCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = getOrCreateStudent(studentID);

        student.displayRegisteredCourses();
        if (!student.registeredCourses.isEmpty()) {
            System.out.print("\nEnter the Course Code you want to drop: ");
            String courseCode = scanner.nextLine();

            Course courseToRemove = null;
            for (Course course : student.registeredCourses) {
                if (course.code.equals(courseCode)) {
                    courseToRemove = course;
                    break;
                }
            }

            if (courseToRemove != null) {
                student.dropCourse(courseToRemove);
                courseToRemove.removeStudent();
                System.out.println("Successfully dropped " + courseToRemove.title);
            } else {
                System.out.println("You are not registered for this course.");
            }
        }
    }
    public static void viewRegisteredCourses() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = getOrCreateStudent(studentID);

        student.displayRegisteredCourses();
    }
    public static Student getOrCreateStudent(String studentID) {
        Student student = studentDatabase.get(studentID);
        if (student == null) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            student = new Student(studentID, name);
            studentDatabase.put(studentID, student);
        }
        return student;
    }
}
