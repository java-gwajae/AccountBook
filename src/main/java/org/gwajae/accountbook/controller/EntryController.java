package org.accountbook.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryController implements Initializable {

    @FXML
    public Button detail;

    @FXML
    private Label date;

    @FXML
    private Label type;

    @FXML
    private Label category;

    @FXML
    private Label amount;

    private Stage primaryStage;

    private Calendar entry = new Calendar("", "", "", 0, "", "");

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detail.setOnAction(e -> {
            showDialog(primaryStage);
        });

        updateEntry(new Calendar("", "", "", 0, "", ""), primaryStage);
    }

    public void updateEntry(Calendar entry, Stage primaryStage) {
        this.entry = entry;
        this.type.setText(entry.getType());
        this.category.setText(entry.getCategory());


        String pattern = "MM월 dd일";
        DateFormat df = new SimpleDateFormat(pattern);
        this.date.setText(df.format(entry.getDate()));

        if(this.type.getText().equals("수입")) {
            this.amount.setText("+" + String.format("%,d", entry.getAmount()) + "원");
            updateLabel(1);
        } else {
            this.amount.setText("-" + String.format("%,d", entry.getAmount()) + "원");
            updateLabel(2);
        }
    }

    private void updateLabel(int n) {
        if(n == 1) {
            type.setStyle("-fx-background-radius: 15 15 15 15; -fx-background-color: #c9e1ff; -fx-text-fill: #4c99f8");
            type.setText("수입");
        } else {
            type.setStyle("-fx-background-radius: 15 15 15 15; -fx-background-color: #ffdada; -fx-text-fill:  #eb7070");
            type.setText("지출");
        }
    }

    public void showDialog(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(getClass().getResource("/org/gwajae/accountbook/view/detail-view.fxml").openStream());
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/gwajae/accountbook/style/detail.css")).toString());
            Stage dialog = new Stage();

            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setScene(scene);
            dialog.show();

            DetailViewController dt = fxmlLoader.getController();
            dt.loadDetailView(entry);
        } catch (IOException o) {
            System.out.println(o.getMessage());
        }
    }
}
