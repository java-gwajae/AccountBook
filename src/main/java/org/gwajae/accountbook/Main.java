package org.gwajae.accountbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gwajae.accountbook.controller.CalendarController;
import org.gwajae.accountbook.controller.SideController;

import java.util.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene scene = new Scene(root);

        CalendarController controller = new CalendarController();
        controller.setPrimaryStage(primaryStage);

        SideController sideController = new SideController();
        sideController.setPrimaryStage(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}