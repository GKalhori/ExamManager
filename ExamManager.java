import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Exam {
    ArrayList<String> educationDir = new ArrayList<>();
    ArrayList<String> master = new ArrayList<>();
    ArrayList<String> lessonName = new ArrayList<>();
    ArrayList<String> examType = new ArrayList<>();
    ArrayList<String> examDate = new ArrayList<>();
    ArrayList<String> examCode = new ArrayList<>();
    ArrayList<Integer> numOfQuestions = new ArrayList<>();
    ArrayList<String> examTime = new ArrayList<>();

    public Exam() {
        educationDir.add(0, "Ali Alavi");
        lessonName.add(0, "AP");
        examType.add(0, "Test");
        examDate.add(0, "98-03-10");
        examCode.add(0, "97243-A");
        numOfQuestions.add(0, 20);
        examTime.add(0, "02:30:00");
    }

    public void listLessons() {
        for (int i = 0; i < lessonName.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, lessonName.get(i));
        }
    }
}

class Tasks {
    public void teacherTasks() {
        System.out.println("What Do You Want to Do?");
        System.out.println("1- Specifying the Type of an Exam");
        System.out.println("2- Adding Questions to an Exam");
        System.out.println("3- Changing the Questions of an Exam");
        System.out.println("4- Deleting a Question of an Exam");
        System.out.println("5- Identifying the Answer for an Exam");
        System.out.println("6- Marking an Exam Papers");
        System.out.println("7- Changing a Student's Exam Scores");
        System.out.println("8- Answering Student Questions in the Exam");
    }

    public void studentTasks() {
        System.out.println("What Do You Want to Do?");
        System.out.println("1- Participating in the Exam");
        System.out.println("2- Seeing My Grades in the Exam");
        System.out.println("3- See the Master's Answer to Your Question");
        System.out.println("4- Continue Previous Exam");
    }

    public void eduTasks() {
        System.out.println("What Do You Want to Do?");
        System.out.println("1- Defining a New test");
        System.out.println("2- Specifying Students of an Exam");
        System.out.println("3- Specifying Master of an Exam");
        System.out.println("4- Changing the Information of an Exam");
        System.out.println("5- Changing the Students of an Exam");
        System.out.println("6- Changing the Master of an Exam");
        System.out.println("7- Deleting an Exam");
        System.out.println("8- Printing the Grades of All Students in an Exam");
    }
}

class InfoBase {
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<Integer> types = new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();

    public InfoBase() {
        usernames.add(0, "Ali");
        passwords.add(0, "11111");
        types.add(0, 1); // director of education

        usernames.add(1, "Amir");
        passwords.add(1, "22222");
        types.add(1, 2); // teacher

        usernames.add(2, "Sara");
        passwords.add(2, "33333");
        types.add(2, 3); // student

        usernames.add(3, "Zahra");
        passwords.add(3, "44444");
        types.add(3, 3); // student
    }

    public ArrayList<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(String password) {
        this.passwords.add(password);
    }

    public void listUsers() {
        for (int i = 0; i < usernames.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, usernames.get(i));
        }
    }
}

public class ExamManager extends InfoBase {

    Scanner input = new Scanner(System.in);
    String siusername; // sign in username
    double cta = 0; // counter for true answers
    Date date = Calendar.getInstance().getTime();
    DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss");
    String today = formatter.format(date);
    Exam NewExam = new Exam();
    Tasks NewTasks = new Tasks();

    ArrayList<ArrayList<String>> examQuestionsMain = new ArrayList<>();
    ArrayList<ArrayList> examAnswersMain = new ArrayList<>();
    ArrayList<ArrayList<String>> examStudentsMain = new ArrayList<>();
    HashMap<String, String> login = new HashMap<>();
    HashMap<String, Integer> loginType = new HashMap<>();
    HashMap<String, HashMap<String, ArrayList<String>>> answersCollection = new HashMap<>();
    HashMap<String, Integer> masterLesson = new HashMap<>();
    HashMap<String, String> questionOfMaster = new HashMap<>();
    HashMap<String, String> answerToStudent = new HashMap<>();
    HashMap<Integer, Double> StudentGrade = new HashMap<>();
    HashMap<String, HashMap<Integer, Double>> StudentGrades = new HashMap<>();

    //////////////////////////////////////////////////////////////////////////////////////////////  next order
    public void nextOrder() {
        System.out.println("Now What Do You Want to Do? (Please Enter a Number)");
        System.out.println("1. Back to the Welcome Menu");
        System.out.println("2. Back to the User Actions Menu");
        System.out.println("3. Exit");
        int order = input.nextInt();
        while (order < 1 || order > 3) {
            System.out.println("Please Enter a Valid Value");
            order = input.nextInt();
        }
        if (order == 1)
            MainMenu();
        else if (order == 2) {
            if (loginType.get(siusername) == 1) // edu
                EduMenu();
            else if (loginType.get(siusername) == 2) // teacher
                TeacherMenu();
            else if (loginType.get(siusername) == 3) // student
                StudentMenu();
            else if (loginType.get(siusername) == 4) // Viewer
                ViewerMenu();
        } else
            System.out.println("Thank You & Goodbye :)");
    }

    public void MainMenu() {
        System.out.println("\t\t\t\t\t\t\t\t\t\t" + today);
        System.out.println("Welcome to the Exam Helper System.");
        System.out.println("Enter A Number:");
        System.out.printf("1- Register\n2- Sign In\n");
        int choice = input.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Please Enter a Valid Number");
            choice = input.nextInt();
        }
        if (choice == 1)
            Register();
        else
            SignIn();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// register
    public void Register() {
        System.out.println("Please Enter an Username:");
        input.nextLine();
        String username = input.nextLine();
        for (int i = 0; i < usernames.size(); i++) {
            while (usernames.get(i).equals(username)) {
                System.out.println("This User Name Is Taken, Please Enter Another One");
                username = input.nextLine();
                i = 0;
            }
        }
        usernames.add(username);
        System.out.println("Please Enter a Password:");
        String password = input.next();
        while (password.length() < 5) {
            System.out.printf("Password Must Be Longer than 4 Characters\nPlease Enter a New Password\n");
            password = input.next();
        }
        setPasswords(password);
        System.out.println("Please Enter Your User Type");
        System.out.printf("1- Director of Education\n2- Teacher\n3- Student\n4- Viewer\n");
        int type = input.nextInt();
        while (type < 1 || type > 4) {
            System.out.println("Please Enter a Valid Number");
            type = input.nextInt();
        }
        types.add(type);
        System.out.println("You Registered Successfully ");
        nextOrder();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// sign in
    public void SignIn() {
        System.out.println("Please Enter Your Username:");
        input.nextLine();
        siusername = input.nextLine();
        System.out.println("Please Enter Your Password:");
        String sipassword = input.next();
        for (int i = 0; i < usernames.size(); i++) {
            login.put(usernames.get(i), getPasswords().get(i)); // every username has it's passwords
            loginType.put(usernames.get(i), types.get(i)); // every username has it's type
        }
        while (!sipassword.equals(login.get(siusername))) {
            // username is "key" & it's password is "value" so every username has a different pass
            System.out.println("Invalid Username or Password, Try again");
            System.out.println("New Username:");
            input.nextLine();
            siusername = input.nextLine();
            System.out.println("New Password:");
            sipassword = input.next();
        }
        System.out.println("You Logged In Successfully");
        if (loginType.get(siusername) == 1) // edu
            EduMenu();
        else if (loginType.get(siusername) == 2) // teacher
            TeacherMenu();
        else if (loginType.get(siusername) == 3) // student
            StudentMenu();
        else if (loginType.get(siusername) == 4) // student
            ViewerMenu();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// edu
    public void EduMenu() {
        ArrayList<String> examStudentsSub = new ArrayList<>();
        NewTasks.eduTasks();
        int eduorder = input.nextInt();
        while (eduorder < 1 || eduorder > 8) {
            System.out.println("Please Enter a Valid Number");
            eduorder = input.nextInt();
        }
        switch (eduorder) {
            case 1: // Defining a New Exam
                System.out.println("Please Answer the Questions in order to Define an Exam");
                System.out.println("What's the Name of the Lesson?");
                input.nextLine();
                String lessonName = input.nextLine();
                NewExam.lessonName.add(lessonName);
                System.out.println("What's the Exam Code?");
                String examCode = input.next();
                for (int i = 0; i < NewExam.examCode.size(); i++) {
                    while (NewExam.examCode.get(i).equals(examCode)) {
                        System.out.println("This Exam Code Is Taken, Please Enter Another One");
                        examCode = input.next();
                        i = 0;
                    }
                }
                NewExam.examCode.add(examCode);
                System.out.println("When's the Exam Date? (YY-MM-DD)");
                String examDate = input.next();
                NewExam.examDate.add(examDate);
                System.out.println("How Much Time Is Exam Duration? (HH:MM:SS)");
                String examTime = input.next();
                NewExam.examTime.add(examTime);
                System.out.println("Please Enter the ID of the Education Director of the Exam");
                listUsers();
                int EdID = input.nextInt();
                while (loginType.get(usernames.get(EdID - 1)) != 1) {
                    System.out.println("Only Education Director Type Can Be Choose, Please Enter an Education Director ID Number");
                    EdID = input.nextInt();
                }
                NewExam.educationDir.add(usernames.get(EdID - 1));
                nextOrder();
                break;
            case 2: // Specifying Students of an Exam
                System.out.println("For Which Exam You Want to Specify Students?");
                NewExam.listLessons();
                int ENspecifyStudents = input.nextInt();
                System.out.println("How Many Students Are Supposed to Take the Exam?");
                int studentNumbers = input.nextInt();
                System.out.println("Please Enter the Number of the Student IDs You Want:");
                listUsers();
                int IDnumbers;
                for (int i = 0; i < studentNumbers; i++) {
                    IDnumbers = input.nextInt();
                    while (loginType.get(usernames.get(IDnumbers - 1)) != 3) {
                        System.out.println("Only Student Type Can Be Choose, Please Enter a Student ID Number");
                        IDnumbers = input.nextInt();
                    }
                    examStudentsSub.add(i, usernames.get(IDnumbers - 1));
                }
                examStudentsMain.add(ENspecifyStudents - 1, examStudentsSub);
                nextOrder();
                break;
            case 3: // Specifying Master of an Exam
                System.out.println("For Which Exam You Want to Specify Master?");
                NewExam.listLessons();
                int ENspecifyMaster = input.nextInt();
                System.out.println("Please Enter the ID of the Master You Want");
                listUsers();
                int masterID = input.nextInt();
                while (loginType.get(usernames.get(masterID - 1)) != 2) {
                    System.out.println("Only Master Type Can Be Choose, Please Enter a Master ID Number");
                    masterID = input.nextInt();
                }
                NewExam.master.add(ENspecifyMaster - 1, usernames.get(masterID - 1));
                masterLesson.put(usernames.get(masterID - 1), ENspecifyMaster - 1);
                nextOrder();
                break;
            case 4: // Changing the Information of an Exam
                System.out.println("For Which Exam You Want to Change the Information?");
                NewExam.listLessons();
                int ENinfo = input.nextInt();
                System.out.println("Which Information You Want to Change?");
                System.out.printf("1- Education Director\n2- Exam Time\n3- Lesson Name\n4- Exam Type\n");
                System.out.printf("5- Exam Date\n6- Exam Code\n7- Number of Questions\n");
                int info = input.nextInt();
                while (info < 1 || info > 7) {
                    System.out.println("Please Enter a Valid Number");
                    info = input.nextInt();
                }
                switch (info) {
                    case 1:
                        System.out.println("Who Is the New Education Director?");
                        listUsers();
                        int newED = input.nextInt();
                        while (loginType.get(usernames.get(newED - 1)) != 1) {
                            System.out.println("Only Education Director Type Can Be Choose, Please Enter an Education Director ID Number");
                            newED = input.nextInt();
                        }
                        NewExam.educationDir.set(ENinfo - 1, usernames.get(newED - 1));
                        break;
                    case 2:
                        System.out.println("How Much Time Is Exam Duration? (HH:MM:SS)");
                        String newTime = input.next();
                        NewExam.examTime.set(ENinfo - 1, newTime);
                        break;
                    case 3:
                        System.out.println("What Is the New Lesson Name?");
                        input.nextLine();
                        String newLesson = input.nextLine();
                        NewExam.lessonName.set(ENinfo - 1, newLesson);
                        break;
                    case 4:
                        System.out.println("What Is the New Exam Type? (Test, Explanatory, TE+EX)");
                        String newType = input.next();
                        while (!newType.equals("Test") && !newType.equals("Explanatory") && !newType.equals("TE+EX")) {
                            System.out.println("Please Enter a Valid Value");
                            newType = input.next();
                        }
                        NewExam.examType.set(ENinfo - 1, newType);
                        break;
                    case 5:
                        System.out.println("When Is the New Exam Date? (YY-MM-DD)");
                        String newDate = input.next();
                        NewExam.examDate.set(ENinfo - 1, newDate);
                        break;
                    case 6:
                        System.out.println("What Is the New Exam Code");
                        String newCode = input.next();
                        NewExam.examCode.set(ENinfo - 1, newCode);
                        break;
                    case 7:
                        System.out.println("How Many Questions Do You Want to Be in the Exam?");
                        int newQuesNum = input.nextInt();
                        NewExam.numOfQuestions.set(ENinfo - 1, newQuesNum);
                        break;
                }
                nextOrder();
                break;
            case 5: // Changing the Students of an Exam
                System.out.println("For Which Exam You Want to Change the Students?");
                NewExam.listLessons();
                int ENchangeStudents = input.nextInt();
                System.out.println("Please Enter the Student ID You Want to Change?");
                for (int i = 0; i < examStudentsMain.get(ENchangeStudents - 1).size(); i++) {
                    System.out.printf("%d: %s\n", i + 1, examStudentsMain.get(ENchangeStudents - 1).get(i));
                } // show the list of students who participate in the exam
                int StudentID = input.nextInt();
                System.out.println("Please Enter the New Student ID?");
                listUsers();
                int newStudentID = input.nextInt();
                while (loginType.get(usernames.get(newStudentID - 1)) != 3) {
                    System.out.println("Only Student Type Can Be Choose, Please Enter a Student ID Number");
                    newStudentID = input.nextInt();
                }
                examStudentsMain.get(ENchangeStudents - 1).set(StudentID - 1, usernames.get(newStudentID - 1));
                nextOrder();
                break;
            case 6: // Changing the Master of an Exam
                System.out.println("For Which Exam You Want to Change the Master?");
                NewExam.listLessons();
                int ENchangeMaster = input.nextInt();
                System.out.println("Please Enter the New Master ID?");
                listUsers();
                int newMaster = input.nextInt();
                while (loginType.get(usernames.get(newMaster - 1)) != 2) {
                    System.out.println("Only Master Type Can Be Choose, Please Enter a Master ID Number");
                    newMaster = input.nextInt();
                }
                NewExam.master.set(ENchangeMaster - 1, usernames.get(newMaster - 1));
                nextOrder();
                break;
            case 7: // Deleting an Exam
                System.out.println("Which Exam You Want to Be Deleted?");
                NewExam.listLessons();
                int ENdelete = input.nextInt();
                NewExam.educationDir.remove(ENdelete - 1);
                NewExam.master.remove(ENdelete - 1);
                NewExam.lessonName.remove(ENdelete - 1);
                NewExam.examType.remove(ENdelete - 1);
                NewExam.examDate.remove(ENdelete - 1);
                NewExam.examCode.remove(ENdelete - 1);
                NewExam.numOfQuestions.remove(ENdelete - 1);
                NewExam.examTime.remove(ENdelete - 1);
                examQuestionsMain.remove(ENdelete - 1);
                System.out.println("Done, the Exam Is Deleted");
                nextOrder();
                break;
            case 8: // Printing the Grades of All Students in an Exam
                System.out.println("Which Exam Scores Do You Want to Print?");
                NewExam.listLessons();
                int ENprintScores = input.nextInt();
                for (int j = 0; j < StudentGrades.size(); j++) {
                    System.out.printf("%s: %f\n", examStudentsMain.get(ENprintScores - 1).get(j), StudentGrades.get(examStudentsMain.get(ENprintScores - 1).get(j)).get(ENprintScores - 1));
                }
                nextOrder();
                break;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// Viewer
    public void ViewerMenu() {
        System.out.println("What Do You Want To Do?");
        System.out.println("1- Access to the List of the Users\n2- Access to Questions of the Exam");
        int choice = input.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Please enter a Valid Number");
            choice = input.nextInt();
        }
        if (choice == 1) {
            listUsers();
        } else {
            System.out.println("Which Exam?");
            NewExam.listLessons();
            int examChoice = input.nextInt();
            for (int i = 0; i < examQuestionsMain.get(examChoice - 1).size(); i++) {
                System.out.printf("Question %d: %s\n", i + 1, examQuestionsMain.get(examChoice - 1).get(i));
            }
        }
        nextOrder();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// teacher
    public void TeacherMenu() {
        ArrayList<String> examQuestionsSub = new ArrayList<>();
        ArrayList<String> examAnswersSub = new ArrayList<>();
        NewTasks.teacherTasks();
        int teacherorder = input.nextInt();
        while (teacherorder < 1 || teacherorder > 8) {
            System.out.println("Please Enter a Valid Number");
            teacherorder = input.nextInt();
        }
        switch (teacherorder) {
            case 1: // Specifying the Type of an Exam
                System.out.println("For Which Exam You Want to Specify Type?");
                NewExam.listLessons();
                int ENspecifyType = input.nextInt();
                System.out.println("What Kind of Exam Is It? (Test, Explanatory, TE+EX)");
                String examType = input.next();
                while (!examType.equals("Test") && !examType.equals("Explanatory") && !examType.equals("TE+EX")) {
                    System.out.println("Please Enter a Valid Value");
                    examType = input.next();
                }
                NewExam.examType.add(ENspecifyType - 1, examType);
                nextOrder();
                break;
            case 2: // Adding a Question to an Exam
                System.out.println("For Which Exam You Want to Add Question?");
                NewExam.listLessons();
                int ENaddQues = input.nextInt();
                System.out.println("How Many Questions Do You Want to Design for the Exam?");
                int countNewQues = input.nextInt();
                input.nextLine();
                String Question;
                for (int j = 0; j < countNewQues; j++) {
                    System.out.printf("Question %d:\n", j + 1);
                    Question = input.nextLine();
                    examQuestionsSub.add(j, Question);
                }
                examQuestionsMain.add(ENaddQues - 1, examQuestionsSub);
                NewExam.numOfQuestions.add(ENaddQues - 1, examQuestionsMain.get(ENaddQues - 1).size());
                for (int k = 0; k < examQuestionsMain.size(); k++) // show
                    System.out.println(examQuestionsMain.get(k));
                nextOrder();
                break;
            case 3: // Changing the Questions of an Exam
                System.out.println("For Which Exam You Want to Change the Questions?");
                NewExam.listLessons();
                int ENchangeQues = input.nextInt();
                System.out.println("How Many Questions You Want to Change?");
                int countQuesChange = input.nextInt();
                int numQuesChange;
                String newQuestion;
                for (int j = 0; j < countQuesChange; j++) {
                    for (int k = 0; k < examQuestionsMain.get(ENchangeQues - 1).size(); k++) {
                        System.out.printf("%d: %s\n", k + 1, examQuestionsMain.get(ENchangeQues - 1).get(k));
                    }
                    System.out.println("What's the Number of the Question You Want to Change?");
                    numQuesChange = input.nextInt();
                    System.out.println("What's the New Question?");
                    input.nextLine();
                    newQuestion = input.nextLine();
                    examQuestionsMain.get(ENchangeQues - 1).set(numQuesChange - 1, newQuestion);
                }
                for (int k = 0; k < examQuestionsMain.size(); k++) // show
                    System.out.println(examQuestionsMain.get(k));
                nextOrder();
                break;
            case 4: // Deleting a Question of an Exam
                System.out.println("For Which Exam You Want to Delete Question?");
                NewExam.listLessons();
                int ENdelete = input.nextInt();
                System.out.println("How Many Questions You Want to Delete?");
                int countDelete = input.nextInt();
                int numQuesDelete;
                for (int j = 0; j < countDelete; j++) {
                    for (int k = 0; k < examQuestionsMain.get(ENdelete - 1).size(); k++) {
                        System.out.printf("%d: %s\n", k + 1, examQuestionsMain.get(ENdelete - 1).get(k));
                    }
                    System.out.println("What's the Number of the Question You Want to Delete?");
                    numQuesDelete = input.nextInt();
                    examQuestionsMain.get(ENdelete - 1).remove(numQuesDelete - 1);
                }
                NewExam.numOfQuestions.set(ENdelete - 1, examQuestionsMain.get(ENdelete - 1).size());
                for (int k = 0; k < examQuestionsMain.size(); k++) // show
                    System.out.println(examQuestionsMain.get(k));
                nextOrder();
                break;
            case 5: // Identifying the Answers for an Exam
                System.out.println("For Which Exam You Want to Identifying the Answers?");
                NewExam.listLessons();
                int ENanswer = input.nextInt();
                input.nextLine();
                for (int j = 0; j < examQuestionsMain.get(ENanswer - 1).size(); j++) {
                    System.out.printf("%d: %s\n", j + 1, examQuestionsMain.get(ENanswer - 1).get(j));
                    System.out.printf("Write the Answer for Question Number %d:\n", j + 1);
                    String answer = input.nextLine();
                    examAnswersSub.add(j, answer);
                }
                examAnswersMain.add(ENanswer - 1, examAnswersSub);
                nextOrder();
                break;
            case 6: // Marking an Exam Papers
                double finalGrade = 0;
                System.out.println("Which Student Paper You Want to Mark?");
                for (int i = 0; i < examStudentsMain.get(masterLesson.get(siusername)).size(); i++) {
                    System.out.printf("%d: %s\n", i + 1, examStudentsMain.get(masterLesson.get(siusername)).get(i));
                }
                int studentIDMark = input.nextInt();
                System.out.println("Please Enter the Grade of Every Question After Seeing Student's Answer");
                for (int j = 0; j < answersCollection.get(examStudentsMain.get(masterLesson.get(siusername)).get(studentIDMark - 1)).get(NewExam.lessonName.get(masterLesson.get(siusername))).size(); j++) {
                    System.out.printf("Answer to Question Number %d: %s\n", j + 1, answersCollection.get(examStudentsMain.get(masterLesson.get(siusername)).get(studentIDMark - 1)).get(NewExam.lessonName.get(masterLesson.get(siusername))).get(j));
                    System.out.printf("Score Q%d:\n", j + 1);
                    double questionGrade = input.nextDouble();
                    finalGrade += questionGrade;
                }
                StudentGrade.put(masterLesson.get(siusername), finalGrade);
                StudentGrades.put(examStudentsMain.get(masterLesson.get(siusername)).get(studentIDMark - 1), StudentGrade);// student name & grade
                nextOrder();
                break;
            case 7: // Changing a Student's Exam Scores
                System.out.println("Which Student You Want to Change the Score?");
                for (int i = 0; i < examStudentsMain.get(masterLesson.get(siusername)).size(); i++) {
                    System.out.printf("%d- %s: %f\n", i + 1, examStudentsMain.get(masterLesson.get(siusername)).get(i), StudentGrades.get(examStudentsMain.get(masterLesson.get(siusername)).get(i)).get(masterLesson.get(siusername)));
                }
                int IDChangeGrade = input.nextInt();
                System.out.println("What's the New Grade?");
                Double newGrade = input.nextDouble();
                String studentID = examStudentsMain.get(masterLesson.get(siusername)).get(IDChangeGrade - 1);
                StudentGrades.get(studentID).put(masterLesson.get(siusername), newGrade);
                for (int i = 0; i < examStudentsMain.get(masterLesson.get(siusername)).size(); i++) {
                    System.out.printf("%d- %s: %f\n", i + 1, examStudentsMain.get(masterLesson.get(siusername)).get(i), StudentGrades.get(examStudentsMain.get(masterLesson.get(siusername)).get(i)).get(masterLesson.get(siusername)));
                }
                nextOrder();
                break;
            case 8: // Answering Student Questions in the Exam
                System.out.println("Please Answer to the Student Questions if You Want.");
                System.out.println(questionOfMaster.get(siusername));
                input.nextLine();
                String answer = input.nextLine();
                answerToStudent.put(siusername, answer);
                nextOrder();
                break;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////// student
    public void StudentMenu() {
        ArrayList<String> studentAnswersSub = new ArrayList<>();
        HashMap<String, ArrayList<String>> lessonAnswers = new HashMap<>();
        NewTasks.studentTasks();
        int studentOrder = input.nextInt();
        while (studentOrder < 1 || studentOrder > 4) {
            System.out.println("Please Enter a Valid Number");
            studentOrder = input.nextInt();
        }
        switch (studentOrder) {
            case 1: // Participating in the Exam
                System.out.println("Which Exam You Want to Participate?");
                NewExam.listLessons();
                int ENPart = input.nextInt();
                for (int j = 0; j < examStudentsMain.get(ENPart - 1).size(); j++) {
                    if (examStudentsMain.get(ENPart - 1).get(j).equals(siusername)) {
                        // exam information table
                        String startTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                        String examTime = NewExam.examTime.get(ENPart - 1);
                        System.out.println("================================================================================*");
                        System.out.printf("* Start Time: %s |\t\t\t\t\t\t| Exam Time: %s\n", startTime, examTime);
                        System.out.println("=======================|========================|===============================*");
                        System.out.printf("* Lesson: %s\t\tMaster: %s\t\tExam Code: %s\n", NewExam.lessonName.get(ENPart - 1), NewExam.master.get(ENPart - 1), NewExam.examCode.get(ENPart - 1));
                        System.out.println("================================================================================*");
                        System.out.printf("* Education Director: %s\t\t\t\tExam Type: %s\n", NewExam.educationDir.get(ENPart - 1), NewExam.examType.get(ENPart - 1));
                        System.out.println("=======================|========================|===============================*");
                        System.out.printf("* Question Numbers: %02d |\t\t\t\t\t\t| Date: %s\n", NewExam.numOfQuestions.get(ENPart - 1), NewExam.examDate.get(ENPart - 1));
                        System.out.println("================================================================================*");
                        System.out.println("\t\t\t\t\t\t\"Please Answer the Questions\"\n");
                        input.nextLine();
                        for (int k = 0; k <= examQuestionsMain.get(ENPart - 1).size() / 2; k++) {
                            System.out.printf("Question %d: %s\n", k + 1, examQuestionsMain.get(ENPart - 1).get(k)); // show the questions
                            String stAnswer = input.nextLine(); // get the answer
                            System.out.println("*===============================================================================*");
                            studentAnswersSub.add(k, stAnswer); // جواب سوالا رو میریزیم توی یه لیست
                            if (k == examQuestionsMain.get(ENPart - 1).size() / 2) {
                                System.out.println("Do You Want to Know How Much Time Has Left Since the Exam? (Y/N)");
                                char answerTime = input.next().charAt(0);
                                if (answerTime == 'Y') {
                                    String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    Date startT = null;
                                    try {
                                        startT = timeFormat.parse(startTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Date examT = null;
                                    try {
                                        examT = timeFormat.parse(examTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Date currentT = null;
                                    try {
                                        currentT = timeFormat.parse(currentTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    long result = ((startT.getTime() + examT.getTime()) - currentT.getTime());
                                    String TimeLeft = timeFormat.format(new Date(result));
                                    System.out.println("The Time Left of the Exam Is: " + TimeLeft);
                                } // end of show time left

                                System.out.println("Do You Have Any Question from the Master? (Y/N)");
                                char answerQuestion = input.next().charAt(0);
                                while (answerQuestion != 'Y' && answerQuestion != 'N') {
                                    System.out.println("Please Enter a Valid Value");
                                    answerQuestion = input.next().charAt(0);
                                }
                                input.nextLine();
                                if (answerQuestion == 'Y') {
                                    System.out.println("What's Your Question?");
                                    String studentQuestion = input.nextLine();
                                    questionOfMaster.put(NewExam.master.get(ENPart - 1), studentQuestion);
                                    nextOrder();
                                } // end of answering from master
                                else {
                                    for (int l = examQuestionsMain.get(ENPart - 1).size() / 2 + 1; l < examQuestionsMain.get(ENPart - 1).size(); l++) {
                                        System.out.printf("Question %d: %s\n", l + 1, examQuestionsMain.get(ENPart - 1).get(l)); // show the questions
                                        stAnswer = input.nextLine(); // get the answer
                                        System.out.println("*===============================================================================*");
                                        studentAnswersSub.add(l, stAnswer); // question answers goes to the list
                                    }
                                }
                            } // end of middle question
                        } // end of exam questions
                        lessonAnswers.put(NewExam.lessonName.get(ENPart - 1), studentAnswersSub); // list go to it's own lesson
                        answersCollection.put(siusername, lessonAnswers); // lesson's list goes to student username
                        // scoring exam with type "test"
                        if (NewExam.examType.get(ENPart - 1).equals("Test")) {
                            for (int k = 0; k < answersCollection.get(siusername).get(NewExam.lessonName.get(ENPart - 1)).size(); k++) {
                                if (answersCollection.get(siusername).get(NewExam.lessonName.get(ENPart - 1)).get(k).equals(examAnswersMain.get(ENPart - 1).get(k)))
                                    cta++;
                            }
                            int all = NewExam.numOfQuestions.get(ENPart - 1);
                            double result = (cta * 3 - (all - cta)) / (all * 3) * 100;
                            StudentGrade.put(ENPart - 1, result);
                            StudentGrades.put(siusername, StudentGrade);
                            cta = 0;
                        }
                        System.out.println("Finish Time: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                    } else
                        System.out.println("You're not Allowed to Participate in This Exam. Please Contact Director of Education");
                } // allowed student (for)
                nextOrder();
                break;
            case 2: // Seeing My Grades in the Exam
                System.out.println("Which Exam Grades Do You Want to See?");
                NewExam.listLessons();
                int ENGrade = input.nextInt();
                if (NewExam.examType.get(ENGrade - 1).equals("Test")) {
                    System.out.printf("Your Grade in the %s Exam Is: %f %%\n", NewExam.lessonName.get(ENGrade - 1), StudentGrades.get(siusername).get(ENGrade - 1));
                } else if (NewExam.examType.get(ENGrade - 1).equals("Explanatory") || NewExam.examType.get(ENGrade - 1).equals("TE+EX")) {
                    // show master grade
                    System.out.printf("Your Grade in the %s Exam Is: %f\n", NewExam.lessonName.get(ENGrade - 1), StudentGrades.get(siusername).get(ENGrade - 1));
                }
                nextOrder();
                break;
            case 3: // see master's response
                System.out.println("What's the Exam that You Asked Your Question?");
                NewExam.listLessons();
                int ENansweredQuestion = input.nextInt();
                System.out.println("Master's Answer to Your Question Is:");
                System.out.println(answerToStudent.get(NewExam.master.get(ENansweredQuestion - 1)));
                nextOrder();
                break;
            case 4:
                System.out.println("What's the Exam You Want to Continue?");
                NewExam.listLessons();
                int ENContinue = input.nextInt();
                input.nextLine();
                for (int h = examQuestionsMain.get(ENContinue - 1).size() / 2 + 1; h < examQuestionsMain.get(ENContinue - 1).size(); h++) {
                    System.out.printf("Question %d: %s\n", h + 1, examQuestionsMain.get(ENContinue - 1).get(h)); // show the questions
                    String stAnswerc = input.nextLine(); // get the answer
                    System.out.println("*===============================================================================*");
                    studentAnswersSub.add(h, stAnswerc); // question answers go to the list
                }
                lessonAnswers.put(NewExam.lessonName.get(ENContinue - 1), studentAnswersSub); // list goes to it's own lesson
                answersCollection.put(siusername, lessonAnswers); // lesson's list goes to student username
                // scoring test exam
                if (NewExam.examType.get(ENContinue - 1).equals("Test")) {
                    for (int k = 0; k < answersCollection.get(siusername).get(NewExam.lessonName.get(ENContinue - 1)).size(); k++) {
                        if (answersCollection.get(siusername).get(NewExam.lessonName.get(ENContinue - 1)).get(k).equals(examAnswersMain.get(ENContinue - 1).get(k)))
                            cta++;
                    }
                    int all = NewExam.numOfQuestions.get(ENContinue - 1);
                    double result = (cta * 3 - (all - cta)) / (all * 3) * 100;
                    StudentGrade.put(ENContinue - 1, result);
                    StudentGrades.put(siusername, StudentGrade);
                    cta = 0;
                }
                nextOrder();
                break;
        } // end of student orders
    } // end of student menu
}// end of class exam manager

class TestProgram {
    public static void main(String[] args) {
        ExamManager Test = new ExamManager();
        Test.MainMenu();
    }
}