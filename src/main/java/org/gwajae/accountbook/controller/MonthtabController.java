package org.gwajae.accountbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gwajae.accountbook.model.Calendar;
import org.gwajae.accountbook.model.CalendarService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();
        updateMenu(currentYear, currentMonth);

        more.setOnAction(e -> {
            showDialog(primaryStage, currentYear, currentMonth);
        });
    }

    public void updateMenu(int currentYear, int currentMonth) {
        this.currentMonth = currentMonth;

        int totalin = 0;
        int totalout = 0;

        date.setText(currentMonth + "월 결산");

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

    public void showDialog(Stage primaryStage, int year, int month) {
        try {
            String resource = "/org/gwajae/accountbook/";

            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(getClass().getResource("/org/gwajae/accountbook/view/monthtab-modal.fxml").openStream());
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "styles/monthtab-modal.css")).toString());
            Stage dialog = new Stage();

            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setScene(scene);
            dialog.show();

            MonthtabModalController mc = fxmlLoader.getController();
            mc.loadMonthDetail(year, month);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
