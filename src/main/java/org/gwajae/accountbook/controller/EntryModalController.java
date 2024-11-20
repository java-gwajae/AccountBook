package org.gwajae.accountbook.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class EntryModalController implements Initializable {
    @FXML
    private Button type;

    @FXML
    private ComboBox category;

    @FXML
    private BarChart graph;

    @FXML
    private Rectangle icon;

    @FXML
    private Rectangle graphicon;

    @FXML
    private TextField amount;

    @FXML
    private Label date;

    @FXML
    private Label monthamount;

    @FXML
    private Label thisamount;

    @FXML
    private Label avg;

    private Calendar entry;
    private int t;
    private int count = 1;
    private int total = 0;
    private boolean buttonp = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<Integer, String> series1 = new XYChart.Series<>();
        series1.setName("Fiat");

        series1.getData().add(new XYChart.Data<>(10, "test"));
        series1.getData().add(new XYChart.Data<>(40, "test2"));

        this.graph.getData().add(series1);
        this.category.getItems().addAll("월급","교통비", "식비비", "기타");

        type.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(t == 1) {
                    t = 2;
                } else {
                    t = 1;
                }

                buttonp = true;
                loadDetailView(entry);
            }
        });
    }

    public void loadDetailView(Calendar entry) {
        this.date.setText(entry.getDate());
        this.amount.setText(String.valueOf(entry.getAmount()));

        this.category.getSelectionModel().select(2);
        this.entry = entry;

        if(!buttonp) {
            if( entry.getType().equals("수입")) {
                t = 1;
            } else {
                t = 2;
            }
        }

        if(t == 1) {
            Node n = this.graph.lookup(".data0.chart-bar");
            n.setStyle("-fx-bar-fill: #c9e1ff");
            n = this.graph.lookup(".data1.chart-bar");
            n.setStyle("-fx-bar-fill: #bbfad2");
            this.icon.setStyle("-fx-fill : #c9e1ff ");
            this.graphicon.setStyle("-fx-fill : #c9e1ff ");
        } else {
            Node n = this.graph.lookup(".data0.chart-bar");
            n.setStyle("-fx-bar-fill: #ffdada");
            n = this.graph.lookup(".data1.chart-bar");
            n.setStyle("-fx-bar-fill: #bbfad2");
            this.icon.setStyle("-fx-fill : #ffdada ");
            this.graphicon.setStyle("-fx-fill : #ffdada");
        }

        CalendarService calendarService = new CalendarService();
        List<Calendar> calendarList = new ArrayList<>();

        calendarList = calendarService.read();


        if(t == 1) {
            this.type.setStyle("-fx-background-radius: 15 15 15 15; -fx-background-color: #c9e1ff; -fx-text-fill: #4c99f8");
            this.type.setText("수입");
        } else {
            this.type.setStyle("-fx-background-radius: 15 15 15 15; -fx-background-color: #ffdada; -fx-text-fill:  #eb7070");
            this.type.setText("지출");
        }


        for(Calendar calendar : calendarList) {
            if(calendar.getType().equals(this.type.getText())) {
                count++;
                total += calendar.getAmount();
            }
        }

        double monthavg = (double)total / count;
        double thisavg = Double.parseDouble(amount.getText());

        monthamount.setText("평균 " + this.type.getText() + " : " + String.valueOf((int)monthavg));
        thisamount.setText("10월 2일 : " + amount.getText());

        double diff = (monthavg - thisavg) / ((monthavg + thisavg) / 2) * 100;
        int diff2 = (int)Math.abs(diff);

        if(monthavg < thisavg) {
            avg.setText("+" + String.valueOf(diff2) +"%");
        } else if (monthavg > thisavg) {
            avg.setText("-" + String.valueOf(diff2) +"%");
        } else {
            avg.setText("0%");
        }

        buttonp = false;
    }
}

