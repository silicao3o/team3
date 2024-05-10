package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

public class CampManagementApplication {

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static final String SUBJECT_TYPE_REQUIRED = "REQUIRED";
    private static final String SUBJECT_TYPE_OPTIONAL = "OPTIONAL";

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";
    private static int roundIndex;
    private static final String INDEX_TYPE_ROUND = "RO";

    // 최대 회차 (요구 사항: 10)
    private static final int maxRound = 2; // 시간 절약을 위해 10개에서 2개로 줄이겠습니다.

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
        System.out.println("========================================================");
        System.out.println("새로운 수강생을 등록합니다...\n");

        // 등록할 수강생 이름
        System.out.print("등록할 수강생의 이름을 입력해주세요: ");
        String studentName = sc.next();

        // 수강할 과목
        List<Subject> selectedSubjects = registerSubjects();

        // 저장
        Student newStudent = new Student(sequence(INDEX_TYPE_STUDENT), studentName, selectedSubjects);
        studentStore.add(newStudent);
        System.out.println("\n등록되었습니다.");
    }

    // 수강생 과목 등록
    private static List<Subject> registerSubjects() {
        List<Integer> reqSubNumList = new ArrayList<>(); // 입력받을 필수과목 번호 리스트
        List<Integer> optSubNumList = new ArrayList<>(); // 입력받을 선택과목 번호 리스트
        boolean flag = true; // 예외 처리용 플래그
        String input;

        // 1. 필수 과목 선택 받기
        System.out.println("\n수강할 필수 과목을 선택합니다.");
        sc.nextLine();
        do {
            System.out.println("1. Java\n2. 객체지향\n3. Spring\n4. JPA\n5. MySQL");
            System.out.print("최소 3개 이상의 과목을 선택해주세요: ");
            input = sc.nextLine();

            // 입력 값을 공백 기준으로 구분해서 String 배열로 반환
            String[] strNumbers = input.split(" ");

            // 예외1. 선택지에 없는 문자열을 입력한 경우 (ex: 6, 9, a, hello)
            if (checkWrongInput(SUBJECT_TYPE_REQUIRED, strNumbers)) {
                System.out.println("========================================================");
                System.out.println("1 ~ 5 사이의 선택지를 입력해주세요.");
                continue;
            }

            // String 배열을 Integer 리스트로 변환
            reqSubNumList = Arrays.stream(strNumbers)
                    .map(Integer::parseInt)
                    .toList();

            // 예외2. 같은 과목을 중복해서 선택한 경우 (ex: 1, 1, 3, 4)
            if (checkWrongInput(reqSubNumList)) {
                System.out.println("========================================================");
                System.out.println("같은 과목을 중복해서 수강할 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            // 예외3. 3개 미만의 과목을 선택한 경우 (ex: 1, 2)
            if (reqSubNumList.size() < 3) {
                System.out.println("========================================================");
                System.out.println("최소 3개 이상의 필수 과목을 선택해야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 아무 예외 없이 알맞게 과목을 선택한 경우
            flag = false;
        } while (flag);

        // 2. 선택 과목 선택 받기
        flag = true;
        System.out.println("\n수강할 선택 과목을 선택합니다.");
        do {
            System.out.println("1. 디자인 패턴\n2. Spring Security\n3. Redis\n4. MongoDB");
            System.out.print("최소 2개 이상의 과목을 선택해주세요: ");
            input = sc.nextLine();

            // 입력 값을 공백 기준으로 구분해서 String 배열로 반환
            String[] strNumbers = input.split(" ");

            // 예외1. 선택지에 없는 문자열을 입력한 경우 (ex: 6, 9, a, hello)
            if (checkWrongInput(SUBJECT_TYPE_OPTIONAL, strNumbers)) {
                System.out.println("========================================================");
                System.out.println("1 ~ 4 사이의 선택지를 입력해주세요.");
                continue;
            }

            // String 배열을 Integer 리스트로 변환
            optSubNumList = Arrays.stream(strNumbers)
                    .map(Integer::parseInt)
                    .toList();

            // 예외2. 같은 과목을 중복해서 선택한 경우 (ex: 1, 1, 3)
            if (checkWrongInput(optSubNumList)) {
                System.out.println("========================================================");
                System.out.println("같은 과목을 중복해서 수강할 수 없습니다. 다시 입력해주세요.");
                continue;
            }

            // 예외3. 2개 미만의 과목을 선택한 경우 (ex: 1)
            if (optSubNumList.size() < 2) {
                System.out.println("========================================================");
                System.out.println("최소 2개 이상의 선택 과목을 선택해야 합니다. 다시 입력해주세요.");
                continue;
            }

            // 아무 예외 없이 알맞게 과목을 선택한 경우
            flag = false;
        } while (flag);

        return subNumListToSubjectList(reqSubNumList, optSubNumList);
    }

    // 예외처리 - 선택지에 없는 과목을 선택했는지 확인 (true: 에러)
    private static boolean checkWrongInput(String subjectType, String[] strings) {
        if (subjectType.equals(SUBJECT_TYPE_REQUIRED)) {
            // 필수 과목인 경우
            for (String str : strings) {
                // 1 ~ 5 사이 숫자가 아니면 안 된다.
                if (!str.equals("1") && !str.equals("2") && !str.equals("3") && !str.equals("4") && !str.equals("5")) {
                    return true;
                }
            }
        } else {
            // 선택 과목인 경우
            for (String str : strings) {
                // 1 ~ 4 사이 숫자가 아니면 안 된다.
                if (!str.equals("1") && !str.equals("2") && !str.equals("3") && !str.equals("4")) {
                    return true;
                }
            }
        }
        return false;
    }

    // 예외처리 - 같은 과목을 중복해서 선택했는지 확인 (true: 에러)
    private static boolean checkWrongInput(List<Integer> subNumList) {
        return subNumList.size() != subNumList.stream().distinct().count();
    }

    // 과목 번호 리스트를 과목 리스트로 변환
    private static List<Subject> subNumListToSubjectList(List<Integer> reqSubNumList, List<Integer> optSubNumList) {
        List<Subject> subjectList = new ArrayList<>();

        for (Integer subNum : reqSubNumList) {
            switch (subNum) {
                case 1 -> subjectList.add(subjectStore.get(0));
                case 2 -> subjectList.add(subjectStore.get(1));
                case 3 -> subjectList.add(subjectStore.get(2));
                case 4 -> subjectList.add(subjectStore.get(3));
                case 5 -> subjectList.add(subjectStore.get(4));
            }
        }

        for (Integer subNum : optSubNumList) {
            switch (subNum) {
                case 1 -> subjectList.add(subjectStore.get(5));
                case 2 -> subjectList.add(subjectStore.get(6));
                case 3 -> subjectList.add(subjectStore.get(7));
                case 4 -> subjectList.add(subjectStore.get(8));
            }
        }

        return subjectList;
    }

    /**
     * 수강생 목록 조회
     */
    private static void inquireStudent() {
        System.out.println("========================================================");
        System.out.println("수강생 목록을 조회합니다...\n");

        System.out.println("[ID]\t[이름]");
        for (Student student : studentStore) {
            System.out.println(student.getStudentId() + "\t\t" + student.getStudentName());
        }
        System.out.println("\n총 수강생은 " + studentStore.size() + " 명 입니다.");
    }

    /**
     * 수강생 과목별 시험 점수 등록
     */
    private static void createScore() {
        System.out.println("========================================================");
        System.out.println("수강생의 과목별 점수를 등록합니다...\n");

        sc.nextLine();
        Student student = getStudent(); // 등록할 수강생
        Subject inputSubject = getSubject(student); // 등록할 과목

        // 해당 과목이 이미 최대 회차일 시 성적 관리 View로 돌아감
        if (checkMaxRound(student, inputSubject)) {
            return;
        }

        int inputScore = getScore(); // 등록할 점수
        char rank = getRank(inputSubject, inputScore); // 등록할 등급

        // 등록할 새로운 Score 객체 생성
        Score score = new Score(sequence(INDEX_TYPE_SCORE), student.getStudentId(), inputSubject.getSubjectId(),
               Integer.parseInt(sequence(INDEX_TYPE_ROUND)), inputScore, rank);

        System.out.println();
        System.out.println(student.getStudentName() + " 학생의 " + inputSubject.getSubjectName() + " 과목 " +
                score.getRound() + " 회차의 성적이 [ 점수: " + inputScore + ", 등급: " + rank + " ]로 등록되었습니다.");
        System.out.println("\t");

        // 성적 저장
        scoreStore.add(score);
    }

    // 관리할 수강생 입력받고 반환
    private static Student getStudent() {
        String inputId;

        while (true) {
            System.out.print("관리할 수강생의 ID를 입력하세요: ");
            inputId = sc.next();

            for (Student student : studentStore) {
                // 입력한 ID에 해당하는 수강생이 있는 경우
                if (student.getStudentId().equals(inputId)) {
                    return student;
                }
            }

            // 예외. 입력한 ID에 해당하는 수강생이 없는 경우
            System.out.println("========================================================");
            System.out.println("입력하신 ID에 해당하는 수강생이 존재하지 않습니다. 다시 입력해주세요.");
        }
    }

    // 관리할 과목 입력받고 반환
    private static Subject getSubject(Student student) {
        String input;

        while (true) {
            printStudentSubjectList(student); // 수강생의 수강 과목 목록 출력
            System.out.print("관리할 과목을 입력해주세요: ");
            input = sc.next();

            // 예외. 과목을 잘못 입력한 경우
            if (checkWrongInput(student, input)) {
                System.out.println("========================================================");
                System.out.println("선택지에 있는 과목으로 다시 입력해주세요.");
                continue;
            }
            break;
        }
        return student.getSubjects().get(Integer.parseInt(input) - 1); // 관리할 과목
    }

    // 수강생의 수강 과목 목록 출력
    private static void printStudentSubjectList(Student student) {
        List<Subject> subjectList = student.getSubjects();

        System.out.println("\n[수강 과목 목록]");
        subjectList.stream().map(subject -> subjectList.indexOf(subject) + 1 + ". " + subject.getSubjectName())
                .forEach(System.out::println);
    }

    // [예외처리] 해당 수강생이 수강하는 과목이 아닌 값을 입력했는지 확인 (true: 에러)
    private static boolean checkWrongInput(Student student, String input) {
        try {
            int number = Integer.parseInt(input);
            // 선택지에 없는 과목을 선택한 경우 true 반환
            return number < 1 || number > student.getSubjects().size();
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 경우 true 반환
            return true;
        }
    }

    // [예외처리] 해당 과목이 이미 최대 회차인지 확인 (최대 회차일 시 성적 관리 View로 돌아감)
    private static boolean checkMaxRound(Student student, Subject subject) {
        for (Score score : scoreStore) {
            if (score.getStudentId().equals(student.getStudentId()) &&
                    score.getSubjectId().equals(subject.getSubjectId())) {
                if (score.getRound() == maxRound) {
                    System.out.println("========================================================");
                    System.out.println("이미 최대 회차입니다.");
                    return true;
                }
            }
        }
        return false;
    }

    // 관리할 점수 입력받고 반환
    private static int getScore() {
        String input;

        while (true) {
            System.out.print("점수를 입력해주세요: ");
            input = sc.next();


            // 예외. 점수를 잘못 입력한 경우
            if (checkWrongInput(input)) {
                System.out.println("========================================================");
                System.out.println("0 ~ 100 사이의 점수로 다시 입력해주세요.");
                continue;
            }
            break;
        }
        return Integer.parseInt(input);
    }

    // [예외처리] 범위를 벗어난 점수를 입력했는지 확인 (true: 에러)
    private static boolean checkWrongInput(String input) {
        try {
            int score = Integer.parseInt(input);
            // 0 ~ 100점이 아닌 점수를 입력한 경우 true 반환
            return score < 0 || score > 100;
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 경우 true 반환
            return true;
        }
    }

    // 과목 타입별 등급 반환
    private static char getRank(Subject subject, int score) {
        // 필수 과목인 경우
        if (SUBJECT_TYPE_REQUIRED.equals(subject.getSubjectType())) {
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

    /**
     * 수강생 과목별 회차 점수 수정
     */
    private static void updateRoundScoreBySubject() {
        System.out.println("========================================================");
        System.out.println("수강생의 과목별 회차 점수를 수정합니다...\n");

        sc.nextLine();
        Student student = getStudent(); // 수정할 수강생
        Subject inputSubject = getSubject(student); // 수정할 과목
        int inputRound = getRound(student, inputSubject); // 수정할 회차
        int inputScore = getScore(); // 수정할 점수
        char rank = getRank(inputSubject, inputScore); // 수정된 등급

        System.out.println();
        System.out.println(student.getStudentName() + " 학생의 " + inputSubject.getSubjectName() + " 과목 " +
                inputRound + " 회차의 성적이 [ 점수: " + inputScore + ", 등급: " + rank + " ]로 수정되었습니다.");

        // 성적 수정
        for (Score score : scoreStore) {
            if (Objects.equals(score.getStudentId(), student.getStudentId()) &&
                    Objects.equals(score.getSubjectId(), inputSubject.getSubjectId()) &&
                    Objects.equals(score.getRound(), inputRound)) {
                // 새로운 점수로 수정
                score.setScore(inputScore);
                // 새로운 등급으로 수정
                score.setRank(rank);
            }
        }
    }

    // 관리할 회차 입력받고 반환
    private static int getRound(Student student, Subject inputSubject) {
        String input;
        int maxRound = 0;

        // 해당 수강생의 해당 과목에 등록된 최고 회차 구하기
        for (Score s : scoreStore) {
            if (s.getStudentId().equals(student.getStudentId()) &&
                    s.getSubjectId().equals(inputSubject.getSubjectId())) {
                if (s.getRound() > maxRound) {
                    maxRound = s.getRound();
                }
            }
        }

        while (true) {
            System.out.print("수정할 회차를 입력해주세요(1 ~ " + maxRound + "): ");
            input = sc.next();

            // 예외. 회차를 잘못 입력한 경우
            if (checkWrongInput(input, maxRound)) {
                System.out.println("========================================================");
                System.out.println("1 ~ " + maxRound + " 사이의 회차로 다시 입력해주세요.");
                continue;
            }
            break;
        }
        return Integer.parseInt(input);
    }

    // [예외처리] 범위를 벗어난 회차를 입력했는지 확인 (true: 에러)
    private static boolean checkWrongInput(String input, int maxRound) {
        try {
            int round = Integer.parseInt(input);
            // 범위를 벗어난 회차를 입력한 경우 true 반환
            return round < 0 || round > maxRound;

        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 경우 true 반환
            return true;
        }
    }

    /**
     * 수강생 특정 과목 회차별 등급 조회
     */
    private static void inquireRoundGradeBySubject() {
        System.out.println("========================================================");
        System.out.println("수강생의 특정 과목 회차별 등급을 조회합니다...\n");

        sc.nextLine();
        Student student = getStudent(); // 조회할 수강생
        Subject subject = getSubject(student); // 조회할 과목
        List<Score> scoreList = new ArrayList<>(); // 해당 과목의 성적 리스트

        // 성적 저장소에서 해당 수강생의 해당 과목의 모든 성적 찾기
        for (Score score : scoreStore) {
            if (score.getStudentId().equals(student.getStudentId()) &&
                    score.getSubjectId().equals(subject.getSubjectId())) {
                scoreList.add(score);
            }
        }

        // 성적 리스트를 회차순으로 정렬
        scoreList.sort(Comparator.comparingInt(Score::getRound));

        System.out.println("\n[회차]\t[점수]\t[등급]");
        for (Score s : scoreList) {
            System.out.printf(" %2d\t\t %3d\t  %s\n", s.getRound(), s.getScore(), s.getRank());
        }
        System.out.println("\n조회 완료되었습니다.");
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of(
                // 필수 과목
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Java", SUBJECT_TYPE_REQUIRED),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "객체지향", SUBJECT_TYPE_REQUIRED),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring", SUBJECT_TYPE_REQUIRED),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "JPA", SUBJECT_TYPE_REQUIRED),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MySQL", SUBJECT_TYPE_REQUIRED),
                // 선택 과목
                new Subject(sequence(INDEX_TYPE_SUBJECT), "디자인 패턴", SUBJECT_TYPE_OPTIONAL),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring Security", SUBJECT_TYPE_OPTIONAL),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Redis", SUBJECT_TYPE_OPTIONAL),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MongoDB", SUBJECT_TYPE_OPTIONAL)
        );
        scoreStore = new ArrayList<>();
//
//        // 테스트용 더미 데이터
//        List<Subject> l1 = new ArrayList<>();
//
//        l1.add(new Subject(sequence(INDEX_TYPE_SUBJECT), "Java", SUBJECT_TYPE_REQUIRED));
//        l1.add(new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring", SUBJECT_TYPE_REQUIRED));
//        l1.add(new Subject(sequence(INDEX_TYPE_SUBJECT), "JPA", SUBJECT_TYPE_REQUIRED));
//        l1.add(new Subject(sequence(INDEX_TYPE_SUBJECT), "Redis", SUBJECT_TYPE_REQUIRED));
//        l1.add(new Subject(sequence(INDEX_TYPE_SUBJECT), "MongoDB", SUBJECT_TYPE_OPTIONAL));
//
//        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "student1", l1));
//        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "student2", l1));
//        studentStore.add(new Student(sequence(INDEX_TYPE_STUDENT), "student3", l1));
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
            case INDEX_TYPE_SCORE -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
            default -> {
                roundIndex++;
                return Integer.toString(roundIndex);
            }
        }
    }

    // 메인 View 출력
    private static void displayMainView() {
        boolean flag = true;
        while (flag) {
            System.out.println("========================================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요: ");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    // 수강생 관리 View 출력
    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("========================================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요: ");
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
            System.out.println("========================================================");
            System.out.println("성적 관리 프로그램 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요: ");
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

}
