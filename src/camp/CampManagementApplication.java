package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
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
        studentStore = new ArrayList<>();
        subjectStore = List.of(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
        scoreStore = new ArrayList<>();
    }

    /**
     * index 자동 증가
     */
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

        System.out.print("사용할 수강생 번호를 입력하세요: ");
        String studentId = sc.next();

        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        // 필수 과목 선택
        List<Subject> mandatorySubjects = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            System.out.println("필수과목을 1개씩 입력하여 " + (3 - i) + "개 선택하세요(Java, 객체지향, Spring, JPA, MySQL)");
            String subjectName;
            do {
                System.out.print("필수과목 " + (i + 1) + " 입력: ");
                subjectName = sc.next();
                if (!isValidSubject(subjectName)) {
                    System.out.println("해당 리스트(Java, 객체지향, Spring, JPA, MySQL) 중에서만 선택하세요!");
                }
            } while (!isValidSubject(subjectName));
            mandatorySubjects.add(new Subject("SU",subjectName, "MANDATORY"));
        }

        // 선택 과목 선택
        List<Subject> choiceSubjects = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            System.out.println("선택과목을 1개씩 입력하여 " + (2 - i) + "개 선택하세요(디자인 패턴, Spring Security, Redis, MongoDB)");
            String subjectName;
            do {
                System.out.print("선택과목 " + (i + 1) + " 입력: ");
                subjectName = sc.next();
                if (!isValidSubject(subjectName)) {
                    System.out.println("해당 리스트(디자인 패턴, Spring Security, Redis, MongoDB) 중에서만 선택하세요!");
                }
            } while (!isValidSubject(subjectName));
            choiceSubjects.add(new Subject("SU",subjectName, "CHOICE"));
        }

        List<Subject> allSubjects = new ArrayList<>();
        allSubjects.addAll(mandatorySubjects);
        allSubjects.addAll(choiceSubjects);

        // 수강생 중복 체크
        for (Student student : studentStore) {
            if (student.getStudentId().equals(studentId)) {
                System.out.println("이미 등록된 수강생입니다. 다시 등록해주세요.");
                return;
            }
        }

        // 수강생 등록
        Student student = new Student(studentId, studentName, allSubjects);
        studentStore.add(student);
        System.out.println("수강생 등록 성공!\n");
    }

    //과목 유효 확인
    private static boolean isValidSubject(String subjectName) {
        List<String> validSubjects = List.of("Java", "객체지향", "Spring", "JPA", "MySQL", "디자인 패턴", "Spring Security", "Redis", "MongoDB");
        return validSubjects.contains(subjectName);
    }


    /**
     * 수강생 목록 조회
     */
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
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




    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        for(Student student : studentStore){
            if(studentId.equals(student.getStudentId())){
                for (int i = 1; i < student.getSubjects().size(); i++){
                    System.out.println(i + "." + student.getSubjects().get(i).getSubjectName());
                }
                //점수에 따른 등급 설정 (필수 과목일시. 선택 과목일 시)
                //점수 입력
                //등급 저장
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
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }
}