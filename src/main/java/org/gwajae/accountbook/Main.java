package org.gwajae.accountbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gwajae.accountbook.controller.CalendarModalController;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class Main extends Application {

    private int currentMonth = LocalDate.now().getMonthValue(); // 현재 월
    private int currentYear = LocalDate.now().getYear(); // 현재 연도
    private Label monthLabel; // 영어로 된 달 표시
    private GridPane calendarGrid; // 달력 그리드
    private List<Calendar> calendars = new ArrayList<Calendar>();

//    @Override
//    public void start(Stage primaryStage){
//        CalendarService calendarService = new CalendarService();
//        calendars = calendarService.read();
//        // 달력을 그릴 GridPane 생성
//        calendarGrid = new GridPane();
//        calendarGrid.setHgap(0); // 열 간격을 얇은 선으로 설정
//        calendarGrid.setVgap(0); // 행 간격을 얇은 선으로 설정
//        calendarGrid.setPadding(new Insets(20, 20, 20, 20)); // 패딩 추가
//        //ASas
//        ///aaaaa
//        // 좌상단 월 이동 버튼과 영어 달 이름 레이블
//        HBox header = new HBox(10);
//        header.setAlignment(Pos.CENTER_LEFT);
//        Button prevButton = new Button("<");
//        Button nextButton = new Button(">");
//        monthLabel = new Label(); // 영어 달 이름 표시
//        monthLabel.setFont(new Font("Arial", 20));
//        header.getChildren().addAll(prevButton, monthLabel, nextButton);
//
//        prevButton.setOnAction(e -> {
//            currentMonth--;
//            if (currentMonth < 1) {
//                currentMonth = 12;
//
//                currentYear--;
//            }
//            updateCalendar();
//        });
//
//        nextButton.setOnAction(e -> {
//            currentMonth++;
//            if (currentMonth > 12) {
//                currentMonth = 1;
//                currentYear++;
//            }
//            updateCalendar();
//        });
//
//        CalendarModalController modal = new CalendarModalController();
//
//        Button addTransactionButton = new Button("기록하기");
//        addTransactionButton.setFont(new Font("Arial", 16)); // 버튼 폰트 크기 설정
//        addTransactionButton.setTextFill(Color.WHITE);
//        addTransactionButton.setBackground(new Background(new BackgroundFill(Color.web("#2e7d32"), new CornerRadii(10), Insets.EMPTY))); // 녹색 배경 및 둥근 모서리
//        addTransactionButton.setOnAction(e -> modal.showDialog(primaryStage));
//
//        // '기록하기' 버튼을 우상단에 배치하기 위한 레이아웃
//        HBox topRightBox = new HBox(addTransactionButton);
//        topRightBox.setAlignment(Pos.TOP_RIGHT); // 우측 정렬
//        topRightBox.setPadding(new Insets(10, 20, 10, 20)); // 패딩 추가
//
//        // 달력 레이아웃
//        VBox calendarLayout = new VBox(20);
//        calendarLayout.setAlignment(Pos.CENTER);
//        calendarLayout.getChildren().add(calendarGrid);
//
//        // BorderPane을 사용하여 컴포넌트를 배치
//        BorderPane rootLayout = new BorderPane();
//        rootLayout.setTop(new HBox(header, topRightBox)); // 상단에 월/년도와 기록하기 버튼 배치
//        rootLayout.setCenter(calendarLayout); // 가운데에 달력 배치
//
//        // 초기 달력 생성
//        updateCalendar();
//
//        // Scene 생성 및 스타일 적용
//        Scene scene = new Scene(rootLayout, 800, 800);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("가계부 어플리케이션");
//        primaryStage.show();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 달력 업데이트 메소드
    private void updateCalendar() {
        // 달력 그리드 비우기
        calendarGrid.getChildren().clear();

        // 요일 배열 추가
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        // 스타일링 변수
        Font dayFont = new Font("Arial", 16); // 요일 폰트 크기 조정
        Background dayBackground = new Background(new BackgroundFill(Color.web("#66FF66"), new CornerRadii(5), Insets.EMPTY)); // 초록색 바
        Background defaultBackground = new Background(new BackgroundFill(Color.web("#f5f5f5"), new CornerRadii(10), Insets.EMPTY)); // 기본 배경
        Background grayBackground = new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)); // 빈 날짜 배경
        Border grayBorder = new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))); // 회색 구분선
        Border blackBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))); // 검은색 테두리

        // 요일을 상단에 표시 (초록색 바)
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setFont(dayFont);
            dayLabel.setTextFill(Color.WHITE); // 요일 텍스트 색상
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setPrefSize(100, 20);
            VBox dayBox = new VBox(dayLabel);
            dayBox.setBackground(dayBackground); // 요일 바는 초록색
            dayBox.setBorder(grayBorder); // 요일 바에 회색 테두리 추가
            calendarGrid.add(dayBox, i, 0); // 첫 번째 행에 요일 추가
        }

        // 현재 달에 맞는 영어 달 이름 설정
        String englishMonth = LocalDate.of(currentYear, currentMonth, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthLabel.setText(currentYear + "-" + currentMonth);

        // 해당 달의 총 일수 계산
        int daysInMonth = LocalDate.of(currentYear, currentMonth, 1).lengthOfMonth();

        // 시작 요일 계산 (1일이 무슨 요일인지)
        int startDayOfWeek = LocalDate.of(currentYear, currentMonth, 1).getDayOfWeek().getValue();
        if (startDayOfWeek == 7) {
            startDayOfWeek = 0; // 일요일부터 시작하기 위해 수정
        }

        // 달력 고정된 6x7 구조를 유지하고 날짜 추가
        int totalCells = 7 * 5; // 7일 x 6주 = 42칸을 고정으로 사용

        for (int i = 0; i < totalCells; i++) {
            VBox dayBox = new VBox();
            dayBox.setAlignment(Pos.TOP_RIGHT); // 날짜와 거래내역을 오른쪽에 배치
            dayBox.setSpacing(5); // 날짜와 거래내역 사이 간격
            dayBox.setPrefSize(100, 100); // 셀 크기 설정
            dayBox.setBorder(grayBorder); // 회색 구분선 추가
            dayBox.setBackground(grayBackground); // 기본적으로 회색 배경으로 설정

            if (i >= startDayOfWeek && i < startDayOfWeek + daysInMonth) {
                // 현재 달에 속하는 날짜인 경우
                int day = i - startDayOfWeek + 1;
                String date = new String();
                if (day < 10) {
                    date = currentYear + "-" + currentMonth + "-0" + day;
                }
                else date = currentYear + "-" + currentMonth + "-" + day;

                Label dateLabel = new Label(day + " ");
                dateLabel.setFont(new Font("Arial", 17)); // 날짜 폰트 설정
                dateLabel.setTextFill(Color.web("#006600")); // 날짜 텍스트 색상
                dayBox.getChildren().add(dateLabel);

                for (Calendar calendar : calendars) {
                    if(Objects.equals(calendar.getDate(), date)) {
                        if(Objects.equals(calendar.getType(), "수입")) {
                            dayBox.getChildren().add(new Button("+" + calendar.getAmount()));
                        }
                        else if(Objects.equals(calendar.getType(), "지출")) {
                            dayBox.getChildren().add(new Button("-" + calendar.getAmount()));
                        }

                    }
                }

                dayBox.setBackground(defaultBackground); // 해당 날짜는 기본 배경 사용
            }

            // 그리드에 해당 셀 추가
            calendarGrid.add(dayBox, i % 7, (i / 7) + 1); // +1 to account for weekday labels in row 0
        }
    }

    // 선택된 날짜에 대해 거래 폼을 여는 메소드
    private void openTransactionForm() {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL); // 모달 창
        formStage.setTitle("거래 입력");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        // 수입/지출 선택
        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton incomeButton = new RadioButton("수입");
        incomeButton.setToggleGroup(typeGroup);
        incomeButton.setSelected(true);
        RadioButton expenseButton = new RadioButton("지출");
        expenseButton.setToggleGroup(typeGroup);

        HBox typeBox = new HBox(10, incomeButton, expenseButton);
        typeBox.setAlignment(Pos.CENTER_LEFT);

        // 금액 입력 필드
        TextField amountField = new TextField();
        amountField.setPromptText("금액 입력");

        // 메모 입력 필드
        TextArea memoField = new TextArea();
        memoField.setPromptText("메모 입력");
        memoField.setPrefRowCount(3); // 메모 필드의 기본 행 수 설정

        // 분류 선택
        ChoiceBox<String> categoryBox = new ChoiceBox<>();
        categoryBox.getItems().addAll("식비", "교통비", "월급", "기타");
        categoryBox.setValue("기타");

        // 저장 버튼
        Button saveButton = new Button("작성 완료");
        saveButton.setFont(new Font("Arial", 14));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setBackground(new Background(new BackgroundFill(Color.web("#2e7d32"), CornerRadii.EMPTY, Insets.EMPTY))); // 버튼 색상 설정
        saveButton.setOnAction(e -> {
            String type = incomeButton.isSelected() ? "수입" : "지출";
            String amount = amountField.getText();
            String memo = memoField.getText();
            String category = categoryBox.getValue();
            LocalDate locale_date = datePicker.getValue();
            Date date = java.sql.Date.valueOf(locale_date);

            // 폼에서 입력한 데이터를 처리하는 로직 (임시로 콘솔에 출력)

            Calendar inputs = new Calendar("666666", "1", type, category, Integer.parseInt(amount), date, memo);

            System.out.println("날짜: " + inputs.getDate());
            System.out.println("유형: " + inputs.getType());
            System.out.println("금액: " + inputs.getAmount());
            System.out.println("메모: " + inputs.getDescription());
            System.out.println("분류: " + inputs.getCategory());

            formStage.close(); // 창 닫기
        });

        // 취소 버튼
        Button cancelButton = new Button("취소");
        cancelButton.setFont(new Font("Arial", 14));
        cancelButton.setOnAction(e -> formStage.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // 폼 레이아웃 구성
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER_LEFT);
        formLayout.getChildren().addAll(datePicker, amountField, typeBox, categoryBox, memoField, buttonBox);

        Scene formScene = new Scene(formLayout, 400, 400);
        formStage.setScene(formScene);
        formStage.show();
    }

    // 메인 메소드
    public static void main(String[] args) {
        launch(args);
    }
}