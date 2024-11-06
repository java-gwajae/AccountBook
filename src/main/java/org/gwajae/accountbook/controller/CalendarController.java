package org.gwajae.accountbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthLabel;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button calendarModal;

    private int currentYear;
    private int currentMonth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentYear = LocalDate.now().getYear();
        currentMonth = LocalDate.now().getMonthValue();

        prevButton.setText("<");
        nextButton.setText(">");

        updateCalendar();

        prevButton.setOnAction(e -> {
            currentMonth--;
            if(currentMonth < 1){
                currentMonth = 12;
                currentYear--;
            }
            updateCalendar();
        });

        nextButton.setOnAction(e -> {
            currentMonth++;
            if(currentMonth > 12){
                currentMonth = 1;
                currentYear++;
            }
            updateCalendar();
        });
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        monthLabel.setText(currentYear + "년 " + currentMonth + "월");
        monthLabel.setTextFill(Color.BLACK);
        monthLabel.setId("currentMonth");

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setTextFill(Color.BLACK);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setPrefSize(100, 20);
            dayLabel.setId("month-header");

            VBox dayBox = new VBox(dayLabel);

            calendarGrid.add(dayBox, i, 0);
        }

        int dayInMonth = LocalDate.of(currentYear, currentMonth, 1).lengthOfMonth();
        int startDayOfWeek = LocalDate.of(currentYear, currentMonth, 1).getDayOfWeek().getValue();
        int totalCells = 35;

        if (startDayOfWeek == 7) {
            startDayOfWeek = 0;
        }

        for (int i = 0; i < totalCells; i++) {
            VBox dayBox = new VBox();
            dayBox.setAlignment(Pos.TOP_RIGHT);
            dayBox.setSpacing(5);
            dayBox.setPrefSize(100, 100);

            String date = "";
            Label dayLabel = new Label();
            dayLabel.setTextFill(Color.web("#22641c"));

            if (i >= startDayOfWeek && i < startDayOfWeek + dayInMonth) {
                int day = i - startDayOfWeek + 1;

                if (day < 10) {
                    date = "0" + day;
                }
                else date = day + "";

                dayLabel.setText(date);
                dayLabel.setId("date-day");
                dayBox.setId("date-cell");

                if (i % 7 == 0)
                    dayBox.setId("date-start");

                if (currentMonth == LocalDate.now().getMonthValue() && day == LocalDate.now().getDayOfMonth()) {
                    if(i % 7 == 0)
                        dayBox.setId("date-start-today");
                    else dayBox.setId("date-today");
                }

                dayBox.getChildren().add(dayLabel);

                CalendarService calendarService = new CalendarService();
                List<Calendar> calendarList = new ArrayList<>();

                calendarList = calendarService.read();

                for (Calendar calendar : calendarList) {
                    if(calendar.getDate().equals(currentYear + "-" + currentMonth + "-" + date)) {
                        if(Objects.equals(calendar.getType(), "수입")) {
                            Label label = new Label();
                            label.setText("+" + calendar.getAmount());
                            label.setAlignment(Pos.CENTER_RIGHT);
                            label.setId("income");

                            dayBox.getChildren().add(label);
                        } else if(Objects.equals(calendar.getType(), "지출")) {
                            Label label = new Label();
                            label.setText("-" + calendar.getAmount());
                            label.setAlignment(Pos.CENTER_RIGHT);
                            label.setId("outcome");

                            dayBox.getChildren().add(label);
                        }
                    }
                }

                calendarGrid.add(dayBox, i % 7, (i / 7) + 1);
            } else if(i < startDayOfWeek) {
                if (currentMonth == 1) {
                    dayInMonth = LocalDate.of(currentYear, 12, 1).lengthOfMonth();
                } else {
                    dayInMonth = LocalDate.of(currentYear, currentMonth - 1, 1).lengthOfMonth();
                }

                int day = (dayInMonth - startDayOfWeek + i) + 1;

                dayLabel.setText(day + "");
                dayLabel.setId("date-day");
                dayBox.setId("n-date-cell");

                if (i == 0)
                    dayBox.setId("n-date-start");

                if (i == 2)
                    dayBox.setId("n-date-error");

                dayBox.getChildren().add(dayLabel);

                calendarGrid.add(dayBox, i, 1);
            } else if(i >= dayInMonth) {
                int day = (i - dayInMonth - startDayOfWeek) + 1;

                dayLabel.setText(day + "");
                dayLabel.setId("date-day");
                dayBox.setId("n-date-cell");

                if (i == 34)
                    dayBox.setId("n-date-error");

                dayBox.getChildren().add(dayLabel);

                calendarGrid.add(dayBox, i % 7, 5);
            }
        }
    }
}
