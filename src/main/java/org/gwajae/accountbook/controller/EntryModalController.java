package org.gwajae.accountbook;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML
    private TextArea memo;

    private Calendar entry;
    private int t;
    private int count = 1;
    private int total = 0;
    private boolean buttonp = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.getItems().addAll("월급","교통", "식비", "기타");


        type.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(t == 1) {
                    t = 2;
                } else {
                    t = 1;
                }

                buttonp = true;
                count = 1;
                total = 0;
                loadDetailView(entry);
            }
        });
    }

    public void loadDetailView(Calendar entry) {
        date.setText(entry.getDate());
        amount.setText(String.valueOf(entry.getAmount()));
        memo.setText(entry.getDescription());

        switch (entry.getCategory()) {
            case "월급" : category.getSelectionModel().select(0); break;
            case "교통" : category.getSelectionModel().select(1); break;
            case "식비" : category.getSelectionModel().select(2); break;
            case "기타" : category.getSelectionModel().select(3); break;
        }

        if(!buttonp) {
            if( entry.getType().equals("수입")) {
                t = 1;
            } else {
                t = 2;
            }
        }

        this.entry = entry;

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

        monthamount.setText("평균 " + this.type.getText() + " : " + String.format("%,d", (int)monthavg) + "원");
        String pattern = "MM월 dd일 : ";
        DateFormat df = new SimpleDateFormat(pattern);
        thisamount.setText(df.format(entry.getPureDate()) + String.format("%,d", Integer.valueOf(amount.getText())) + "원");

        double diff = (monthavg - thisavg) / ((monthavg + thisavg) / 2) * 100;
        int diff2 = (int)Math.abs(diff);

        if(monthavg < thisavg) {
            avg.setText("+" + String.valueOf(diff2) +"%");
            avg.setStyle("-fx-text-fill: #67bc73");
        } else if (monthavg > thisavg) {
            avg.setText("-" + String.valueOf(diff2) +"%");
            avg.setStyle("-fx-text-fill: #ff4242");
        } else {
            avg.setText("0%");
        }

        XYChart.Series<Integer, String> series1 = new XYChart.Series<>();
        series1.setName("Fiat");

        series1.getData().add(new XYChart.Data<>(Integer.valueOf(amount.getText()), "today"));
        series1.getData().add(new XYChart.Data<>((int)monthavg, "month"));

        graph.getData().add(series1);

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

        buttonp = false;
    }

    public org.gwajae.accountbook.Calendar getEntry() {
        String cat = category.getValue().toString();
        return new Calendar(entry.getCalendarId(), entry.getUserId(), type.getText(), cat, Integer.parseInt(amount.getText()), entry.getPureDate(), memo.getText());
    }
}
