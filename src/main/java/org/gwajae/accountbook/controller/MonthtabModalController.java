package org.gwajae.accountbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MonthtabModalController implements Initializable {
    @FXML
    public Label diff;

    @FXML
    public Label label;

    @FXML
    private Label piedate;

    @FXML
    private Label pietotal;

    @FXML
    private PieChart piechart;

    @FXML
    private Label category1;

    @FXML
    private Label category2;

    @FXML
    private Label category3;

    @FXML
    private Label categoryn1;

    @FXML
    private Label categoryn2;

    @FXML
    private Label categoryn3;

    @FXML
    private Label bardate;

    @FXML
    private Label bartotal;

    @FXML
    private Label income;

    @FXML
    private Label outcome;

    @FXML
    private BarChart barchart;

    private int currentMonth;
    private int currentYear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentYear = LocalDate.now().getYear();

    }

    public void loadMonthDetail(int month) {
        currentMonth = month;
        String monthlabel;

        piedate.setText(String.valueOf(currentYear) + "/" + String.valueOf(currentMonth));
        bardate.setText(piedate.getText());

        switch (currentMonth) {
            case 1: monthlabel = "JANUARY"; break;
            case 2: monthlabel = "FEBUARY"; break;
            case 3: monthlabel = "MARCH"; break;
            case 4: monthlabel = "APRIL"; break;
            case 5: monthlabel = "MAY"; break;
            case 6: monthlabel = "JUNE"; break;
            case 7: monthlabel = "JULY"; break;
            case 8: monthlabel = "AUGUST"; break;
            case 9: monthlabel = "SEPTEMBER"; break;
            case 10: monthlabel = "OCTOBER"; break;
            case 11: monthlabel = "NOVEMBER"; break;
            case 12: monthlabel = "DECEMBER"; break;
            default: monthlabel = ""; break;
        }

        label.setText(monthlabel);

        int income = 0;
        int outcome = 0;
        int percentage, total;

        int cat1 = 0;
        int cat2 = 0;
        int cat3 = 0;

        int prevmonth = 0;

        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();
        calendarList = calendarService.read();

        for(Calendar calendar : calendarList) {
            if (calendar.getYearMonth().equals(currentYear + "-" + currentMonth)) {
                if(calendar.getType().equals("수입")) {
                    income += calendar.getAmount();
                } else {
                    outcome += calendar.getAmount();
                    String category = calendar.getCategory();
                    switch (category) {
                        case "교통" : cat1 += calendar.getAmount(); break;
                        case "식비" : cat2 += calendar.getAmount(); break;
                        case "기타" : cat3 += calendar.getAmount(); break;
                        default: break;
                    }
                }
            } else if(Integer.parseInt(calendar.getMonth()) == currentMonth -1) {
                if(calendar.getType().equals("수입")) {
                    prevmonth += calendar.getAmount();
                } else {
                    prevmonth -= calendar.getAmount();
                }
            }
        }


        category1.setText("교통비");
        category2.setText("식비");
        category3.setText("기타");

        this.pietotal.setText(String.format("%,d", outcome) + "원");
        categoryn1.setText(String.format("%,d", cat1) + "₩");
        categoryn2.setText(String.format("%,d", cat2) + "₩");
        categoryn3.setText(String.format("%,d", cat3) + "₩");

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("교통비", cat1),
                        new PieChart.Data("식비", cat2),
                        new PieChart.Data("기타", cat3));

        piechart.getData().addAll(pieChartData);

        if(income > outcome) {
            total = income - outcome;
            bartotal.setText(String.format("+" + "%,d", total) + "원");
        } else {
            total = outcome - income;
            bartotal.setText(String.format("-" + "%,d", total) + "원");
        }

        this.income.setText(String.format("%,d", income) + "₩");
        this.outcome.setText(String.format("%,d", outcome) + "₩");

        double diff = (double) (prevmonth - total) / ((double) (prevmonth + total) / 2) * 100;
        int diff2 = (int)Math.abs(diff);

        if(prevmonth < total) {
            this.diff.setText("+" + String.valueOf(diff2) +"%");
            this.diff.setStyle("-fx-text-fill: #67bc73");
        } else if (prevmonth > total) {
            this.diff.setText("-" + String.valueOf(diff2) +"%");
            this.diff.setStyle("-fx-text-fill: #ff4242");
        } else {
            this.diff.setText("0%");
        }

        XYChart.Series<Integer, String> series1 = new XYChart.Series<>();
        series1.setName("Fiat");

        series1.getData().add(new XYChart.Data<>(outcome, "outcome"));
        series1.getData().add(new XYChart.Data<>(income, "income"));

        barchart.getData().addAll(series1);

        Node n = this.barchart.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: #ff8c8c");

        n = this.barchart.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: #8aa8ff");

    }
}