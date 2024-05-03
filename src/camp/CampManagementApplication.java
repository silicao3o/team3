package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static final Scanner sc = new Scanner(System.in);

    /**
     * main 메서드
     */
    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();

        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    /**
     * 초기 데이터 생성
     */
    private static void setInitData() {
        /*String s = "S"; // String s = new String (S); */
        studentStore = new ArrayList<>();
        subjectStore = List.of( //수정 불가능
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Java", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "객체지향", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "JPA", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MySQL", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "디자인 패턴", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring Security", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Redis", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MongoDB", SUBJECT_TYPE_CHOICE)
        );
        scoreStore = new ArrayList<>();

        // 테스트용 더미 데이터
        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT),"테스트1",new ArrayList<Subject>()));
        studentStore.get(0).getSubjects().add(subjectStore.get(0));
        studentStore.get(0).getSubjects().add(subjectStore.get(1));
        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT),"테스트2",new ArrayList<Subject>()));
        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT),"테스트3",new ArrayList<Subject>()));
    }

    /**
     * index 자동 증가
     */
    // 고유번호 만들 수 있음, 학생, 과목, 점수 ->
    private static String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
        }
    }

    /**
     * 메인 View 출력
     */
    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    /**
     * 수강생 관리 View 출력
     */
    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    /**
     * 점수 관리 View 출력
     */
    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    /**
     * 수강생 등록
     */
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();
        List<Subject> subjectList = new ArrayList<>();
        // 기능 구현 (필수 과목, 선택 과목)

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, subjectList); // 수강생 인스턴스 생성 예시 코드
        // 기능 구현
        System.out.println("수강생 등록 성공!\n");
    }

    /**
     * 수강생 목록 조회
     */
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        /* 준모님 확인용 테스트 코드 */
        for (Student student : studentStore) {
            System.out.println("id : " + student.getStudentId() + ", name : " + student.getStudentName());
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    /**
     * 수강생 번호 입력 받는 메서드
     */
    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하세오 : ");
        return sc.next();
    }


    /**
     * 수강생의 과목별 시험 회차 및 점수 등록
     */
    //수강생 id로 수강생 찾기




    // 수강생의 과목별 시험 회차 및 점수 등록 회차 빠짐
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        for(Student student : studentStore){
            if(studentId.equals(student.getStudentId())){
                for (int i = 1; i <= student.getSubjects().size(); i++){
                    System.out.println(i + "." + student.getSubjects().get(i-1).getSubjectName());
                }
                int input = Integer.parseInt(sc.next());// 과목선택
                Subject subject = student.getSubjects().get(input-1);
                //input을 인덱스 값으로 받아서 student 클래스에 저장되어있는 수강목록 리스트에 접근해서 과목 찾기
                subject.getSubjectName();
                System.out.println("점수를 등록하려는 과목이 맞으신가요? 과목: " + subject.getSubjectName());

               int score = Integer.parseInt(sc.next()); //점수 입력
               char rank = 'N';
                //수강과목이 필수 또는 선택에 따라서 등급 차등
                if(subject.getSubjectType().equals(SUBJECT_TYPE_MANDATORY)){
                    if (score >= 95 && score <= 100){
                        rank = 'A';
                    }
                    else if (score >= 90 && score < 95){
                        rank = 'B';
                    }
                    else if (score >= 80 && score < 90){
                        rank = 'C';
                    }
                    else if (score >= 70 && score < 80){
                        rank = 'D';
                    }
                    else if (score >= 60 && score < 70) {
                        rank = 'F';
                    }
                    else{
                        rank = 'N';
                    }
                }
                else if(subject.getSubjectType().equals(SUBJECT_TYPE_CHOICE)){
                    switch (score/10){
                        case 10:
                        case 9:
                            rank = 'A';
                            break;
                        case 8:
                            rank = 'B';
                            break;
                        case 7:
                            rank = 'C';
                            break;
                        case 6:
                            rank = 'D';
                            break;
                        case 5:
                            rank = 'F';
                            break;
                        default:
                            rank = 'N';
                    }
                }
                Score scoreDB = new Score(subject.getSubjectId(),studentId,sequence(INDEX_TYPE_SCORE),scoreIndex,score,rank);
                scoreStore.add(scoreDB); //등급 저장
            }
        }
        System.out.println("\n점수 등록 성공!");
    }

    /**
     * 수강생의 과목별 회차 점수 수정
     */
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    /**
     * 수강생의 특정 과목 회차별 등급 조회
     */
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        Optional<Student> select_Student = studentStore.stream().filter(student ->student.getStudentId().equals(studentId)).findAny();
        if(select_Student.isPresent()){
            if(select_Student.get().getSubjects().size()<1){
                System.out.println("Error!! 학생이 수강하고 있는 과목이 없습니다!");
                return;
            }
            List<Subject> subjects = select_Student.get().getSubjects();
            System.out.println("---------- 수강 목록 ----------");
            int i=1;
            for(Subject subject : subjects){
                System.out.println(i + ". " + subject.getSubjectName());
                i++;
            }
            System.out.print("------------------------------");
            System.out.print("\n조회할 과목의 번호를 입력해주세요 : ");
            //String subjectName = sc.next();
            int subjectNumber = sc.nextInt();

            //subjectStore
            // 학생id 와 과목id 모두 포함하는 점수 찾기
            List<Score> scoreList = scoreStore.stream().filter(
                    score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjects.get(subjectNumber-1).getSubjectId()) )
                    .toList();
            //.forEach(score -> System.out.println("회차 : " + "(개발중)   등급 : " + score.getRank()));
            if(scoreList.size() == 0){
                System.out.println("Error!! 해당 과목 점수가 없습니다!");
                return;
            }
            else
            {
                for(Score score : scoreList){
                    System.out.println("회차 : " + "(개발중)   등급 : " + score.getRank());
                }
            }
            // 기능 구현
            System.out.println("\n등급 조회 완료!!");
        }
        else
            System.out.println("Error!! 선택된 학생이 존재하지 않습니다!!");
    }
}
