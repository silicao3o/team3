package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

/**
 * 3조 소개글
 * ...
 */
public class CampManagementApplication {

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static final Scanner sc = new Scanner(System.in);

    // 메인 메서드
    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    /**
     * 수강생 등록
     */

    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");

        // 수강생 이름 입력
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        // 선택한 과목을 저장할 리스트
        List<Subject> selectedSubjects = new ArrayList<>();

        // 필수 과목 선택

        int mandatoryCount = 0;
        while (true) {
            int displayCount = 0;
            if (mandatoryCount < 3) {
                System.out.println("과목 목록:");
                for (Subject subject : subjectStore) {
                    if (SUBJECT_TYPE_MANDATORY.equals(subject.getSubjectType())) {
                        System.out.print(subject.getSubjectName() + " ");
                    }
                }
                System.out.println();
                String subjectName = sc.next();
                boolean found = false;
                for (Subject subject : subjectStore) {
                    if (subject.getSubjectName().equals(subjectName) && SUBJECT_TYPE_MANDATORY.equals(subject.getSubjectType())) {
                        selectedSubjects.add(subject);
                        mandatoryCount++;
                        displayCount++;
                        System.out.println("현재 등록한 필수 과목 수: " + displayCount);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("해당 과목을 찾을 수 없습니다. 다시 입력해주세요.");
                }
            } else {
                System.out.println("과목을 추가로 더 입력하시겠습니까? (Y/N)");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("Y")) {
                    mandatoryCount -=1;
                } else {
                    System.out.println("필수 과목 입력을 종료합니다");
                    break;
                }
            }
        }
        sc.nextLine();

        // 선택 과목 선택

        int choiceCount = 0;
        while (true) {
            int displayCount = 0;
            if (choiceCount < 2) {
                System.out.println("과목 목록:");
                for (Subject subject : subjectStore) {
                    if (SUBJECT_TYPE_CHOICE.equals(subject.getSubjectType())) {
                        System.out.print(subject.getSubjectName() + ",");
                    }
                }
                System.out.println();
                String subjectName = sc.nextLine();
                boolean found = false;
                for (Subject subject : subjectStore) {
                    if (subject.getSubjectName().equals(subjectName) && SUBJECT_TYPE_CHOICE.equals(subject.getSubjectType())) {
                        selectedSubjects.add(subject);
                        choiceCount++;
                        displayCount++;
                        System.out.println("현재 등록한 선택 과목 수: " + displayCount);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("해당 과목을 찾을 수 없습니다. 다시 입력해주세요.");
                }
            } else {
                System.out.println("과목을 추가로 더 입력하시겠습니까? (Y/N)");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("Yes") || answer.equalsIgnoreCase("Y")) {
                    choiceCount -= 1;
                } else {
                    System.out.println("선택 과목 입력을 종료합니다");
                    break;
                }
            }
        }
        // 저장
        Student newStudent = new Student(sequence(INDEX_TYPE_STUDENT), studentName, selectedSubjects);
        studentStore.add(newStudent);
    }



    /**
     * 수강생 목록 조회
     */
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        System.out.println("==========================================");
        for (int i = 0; i < studentStore.size(); i++) {
            String studentId = studentStore.get(i).getStudentId();
            String studentName = studentStore.get(i).getStudentName();
            System.out.println((i + 1) + ". " + "ID: " + studentId + ", Name: " + studentName);
        }
        System.out.println("총 수강생은  " + studentStore.size() + "명 입니다.");
        System.out.println("\n수강생 목록 조회 성공!");
        System.out.println("=========================================");
    }

    /**
     * 수강생의 과목별 시험 회차 및 점수 등록 -> null 처리 필요!
     */
    private static void createScore() {
        System.out.println("시험 점수를 등록합니다...");

        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId); // 관리할 수강생

        printStudentSubjects(student); // 수강생의 수강 과목 목록 출력 -> null 처리 필요!

        System.out.print("점수를 등록할 과목의 번호를 입력해주세요: ");
        int input = Integer.parseInt(sc.next());
        Subject subject = student.getSubjects().get(input - 1); // 등록할 과목

        System.out.print("점수를 입력해주세요: ");

        int inputScore = enterScore();

        char rank = getRank(subject, inputScore); // 과목 타입에 따른 성적 등급
        Score score = null;
        boolean storeable = false;

        // 새로운 Score 객체 생성
        for(Score scoreCheck : scoreStore){
            if(scoreCheck.getSubjectId().equals(subject.getSubjectId()) && scoreCheck.getStudentId().equals(student.getStudentId())){
                boolean checkRound = scoreCheck.increaseRound();
                score = scoreCheck;
                if(!checkRound){
                    displayScoreView();
                }
                break;
            }
        }
        if(score == null){
            score = new Score(sequence(INDEX_TYPE_SCORE),studentId,subject.getSubjectId(),inputScore,rank);
            storeable = true;
        }

        checkSave(); // 저장 여부 확인    (취소할 시 성적 관리 View로 돌아감)
        if(storeable){
            scoreStore.add(score); // 성적 저장
        }
        System.out.println(student.getStudentName() + " 학생의 " + subject.getSubjectName() + " 과목 " +
                score.getRound() + " 회차의 점수는 " + inputScore + " 점이고, 등급은 " + rank + " 입니다.");
    }

    private static int enterScore(){
        int inputScore;// 점수 입력받는 변수
        while(true){
            try{
                inputScore = Integer.parseInt(sc.next()); // 등록할 점수
                if( inputScore < 0 || inputScore > 100) {
                    System.out.println("\n점수의 범위가 알맞지 않습니다." + "\n다시 입력해주세요." + "\n(점수의 범위 : 0 ~ 100)");
                }
                else {
                    break;
                }
            }catch (NumberFormatException e){
                System.out.println("숫자를 입력해주세요.");
            }
        }
        return inputScore;
    }





    /**
     * 수강생의 과목별 회차 점수 수정 -> null 처리 필요!
     */
    private static void updateRoundScoreBySubject() {
        System.out.println("시험 점수를 수정합니다...");

        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId); // 관리할 수강생 -> null 처리 필요!

        printStudentSubjects(student); // 수강생의 수강 과목 목록 출력

        System.out.println("수정할 과목의 번호를 입력해주세요: ");
        int input = Integer.parseInt(sc.next());
        Subject subject = student.getSubjects().get(input - 1); // 수정할 과목

        System.out.println("수정할 회차를 입력해주세요: ");
        int round = Integer.parseInt(sc.next()); // 수정할 회차

        System.out.println("새로운 점수를 입력해주세요: ");
        int inputScore = Integer.parseInt(sc.next()); // 새로운 점수
        // 점수가 0 ~ 100점 사이로 입력되게끔

        char rank = getRank(subject, inputScore); // 과목 타입에 따른 새로운 성적 등급

        System.out.println(student.getStudentName() + " 학생의 " + subject.getSubjectName() + " 과목 " +
                round + " 회차의 수정된 점수는 " + inputScore + " 점이고, 수정된 등급은 " + rank + " 입니다.");

        checkSave(); // 저장 여부 확인 (취소할 시 성적 관리 View로 돌아감)

        for (Score score : scoreStore) {
            if (Objects.equals(score.getStudentId(), studentId) &&
                    Objects.equals(score.getSubjectId(), subject.getSubjectId()) &&
                    Objects.equals(score.getRound(), round)) {
                score.setScore(inputScore); // 새로운 점수로 수정
                score.setRank(rank); // 새로운 등급으로 수정
            }
        }
    }

    /**
     * 수강생의 특정 과목 회차별 등급 조회
     */
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        Optional<Student> selectStudent = studentStore.stream().filter(student -> student.getStudentId().equals(studentId)).findAny();
        if (selectStudent.isPresent()) {

            if (selectStudent.get().getSubjects().size() < 1) {
                System.out.println("Error!! 학생이 수강하고 있는 과목이 없습니다!");
                return;
            }
            List<Subject> subjects = selectStudent.get().getSubjects();
            System.out.println("————— 수강 목록 —————");
            int i = 1;
            for (Subject subject : subjects) {
                System.out.println(i + ". " + subject.getSubjectName());
                i++;
            }
            System.out.print("———————————————");
            System.out.print("\n조회할 과목의 번호를 입력해주세요 : ");
            //String subjectName = sc.next();
            int subjectNumber = sc.nextInt();

            //subjectStore
            // 학생id 와 과목id 모두 포함하는 점수 찾기
            List<Score> scoreList = scoreStore.stream().filter(
                            score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjects.get(subjectNumber - 1).getSubjectId()))
                    .toList();
            //.forEach(score -> System.out.println("회차 : " + "(개발중)   등급 : " + score.getRank()));
            if (scoreList.size() == 0) {
                System.out.println("Error!! 해당 과목 점수가 없습니다!");
                return;
            } else {
                for (Score score : scoreList) {
                    System.out.println("회차 : " + score.getRound() + "   등급 : " + score.getRank());
                }
            }
            // 기능 구현
            System.out.println("\n등급 조회 완료!!");
        } else {
            System.out.println("Error!! 선택된 학생이 존재하지 않습니다!!");
        }
    }

    // 수강생 ID 입력받고 반환
    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하세오 : ");
        return sc.next();
    }

    // 수강생 ID를 통해 수강생 객체 찾고 반환
    private static Student findStudentById(String studentId) {
        for (Student student : studentStore) {
            if (studentId.equals(student.getStudentId())) {
                return student;
            }
        }
        System.out.println("입력하신 ID에 해당하는 수강생이 존재하지 않습니다.");
        return null;
    }

    // 수강생의 수강 과목 목록 출력
    private static void printStudentSubjects(Student student) {
        for (int i = 1; i <= student.getSubjects().size(); i++) {
            System.out.println(i + ". " + student.getSubjects().get(i - 1).getSubjectName());
        }
    }

    // 과목 타입별 등급 반환
    private static char getRank(Subject subject, int score) {
        // 필수 과목인 경우
        if (SUBJECT_TYPE_MANDATORY.equals(subject.getSubjectType())) {
            if (score >= 95 && score <= 100) {
                return 'A';
            } else if (score >= 90 && score < 95) {
                return 'B';
            } else if (score >= 80 && score < 90) {
                return 'C';
            } else if (score >= 70 && score < 80) {
                return 'D';
            } else if (score >= 60 && score < 70) {
                return 'F';
            } else {
                return 'N';
            }
        }
        // 선택 과목인 경우
        return switch (score / 10) {
            case 10, 9 -> 'A';
            case 8 -> 'B';
            case 7 -> 'C';
            case 6 -> 'D';
            case 5 -> 'F';
            default -> 'N';
        };
    }

    // 성적 저장 여부 확인
    private static void checkSave() {
        System.out.print("저장하려면 y를, 취소하려면 아무거나 입력해주세요: ");
        if (Objects.equals(sc.next(), "y")) {
            System.out.println("\n저장되었습니다.\n");
        } else {
            System.out.println("\n취소되었습니다.\n");
            displayScoreView();
        }
    }

    // 메인 View 출력
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

    // 수강생 관리 View 출력
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

    // 성적 관리 View 출력
    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("성적 관리 프로그램 실행 중...");
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

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of ( //수정 불가능
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
        List<Subject> l1 = new ArrayList<>();

        l1.add(new Subject("1", "Java", SUBJECT_TYPE_MANDATORY));
        l1.add(new Subject("2", "Spring", SUBJECT_TYPE_MANDATORY));
        l1.add(new Subject("3", "JPA", SUBJECT_TYPE_MANDATORY));
        l1.add(new Subject("4", "MySQL", SUBJECT_TYPE_MANDATORY));

        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "테스트1", l1));
        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "테스트2", new ArrayList<Subject>()));
        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "테스트3", new ArrayList<Subject>()));
    }

    // index 자동 증가
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
}
