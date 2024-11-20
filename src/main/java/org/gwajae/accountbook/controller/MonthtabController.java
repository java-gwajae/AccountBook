package org.gwajae.accountbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MonthtabController implements Initializable {

    @FXML
    private Label income;

    @FXML
    private Label outcome;

    @FXML
    private Button more;

    @FXML
    private Label date;

    private int currentMonth;
    private int currentYear;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        more.setOnAction(e -> {
            MonthDetailView modal = new MonthDetailView();
            modal.showDialog(primaryStage);
        });

        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();
        updateMenu(currentMonth);
    }

    public void updateMenu(int currentMonth) {
      int totalin = 0;
      int totalout = 0;
      
        month.setText(currentMonth + "월 결산");

        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();

        calendarList = calendarService.read();


        for(Calendar calendar : calendarList) {
                    if(calendar.getYearMonth().equals(currentYear + "-" + currentMonth)) {
                        if(Objects.equals(calendar.getType(), "수입")) {
                            totalin += calendar.getAmount();
                        } else if(Objects.equals(calendar.getType(), "지출")) {
                            totalout += calendar.getAmount();
                        }
                    }
         }


        income.setText("+" + String.format("%,d", totalin) + "원");
        outcome.setText("-" + String.format("%,d", totalout) + "원");

    }
}
