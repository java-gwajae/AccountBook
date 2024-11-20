package org.gwajae.accountbook.controller;

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

public class MonthtabModal implements Initializable {
    @FXML
    public Label diff;

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

    private String resource = "/org/accountbook/menu/";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentMonth = LocalDate.now().getMonthValue();
        currentYear = LocalDate.now().getYear();

        XYChart.Series<Integer, String> series1 = new XYChart.Series<>();
        series1.setName("Fiat");

        series1.getData().add(new XYChart.Data<>(10, "test"));
        series1.getData().add(new XYChart.Data<>(40, "test2"));

        barchart.getData().addAll(series1);

        loadMonthDetail();
    }

    public void loadMonthDetail() {
        piedate.setText(String.valueOf(currentYear) + "/" + String.valueOf(currentMonth + 1));
        bardate.setText(piedate.getText());

        int income = 0;
        int outcome = 0;
        int percentage, total;

        int cat1 = 0;
        int cat2 = 0;
        int cat3 = 0;

        int prevmonth = 0;

        Node n = this.barchart.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: #ff8c8c");

        n = this.barchart.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: #8aa8ff");

        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();
        calendarList = calendarService.read();

        for(Calendar calendar : calendarList) {
            if(calendar.getType().equals("수입")) {
                income += calendar.getAmount();
            } else {
                outcome += calendar.getAmount();
                String category = calendar.getCategory();
                switch (category) {
                    case "교통비" : cat1 += calendar.getAmount(); break;
                    case "식비" : cat2 += calendar.getAmount(); break;
                    case "기타" : cat3 += calendar.getAmount(); break;
                    default: break;
                }
            }

            if(calendar.getDate().equals(currentMonth - 1)) {
                prevmonth += calendar.getAmount();
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

        total = income + outcome;

        bartotal.setText(String.format("%,d", total) + "원");
        this.income.setText(String.format("%,d", income) + "₩");
        this.outcome.setText(String.format("%,d", outcome) + "₩");

        double diff = (prevmonth - total) / ((prevmonth + total) / 2) * 100;
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

    }

    public void showDialog(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource + "monthdetailview.fxml")));
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(resource + "monthtab.css")).toString());


            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
