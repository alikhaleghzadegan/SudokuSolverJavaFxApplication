package com.khaleghzadegan.gui;

import com.khaleghzadegan.gui.service.UserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

    private UserInterface ui;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui = new UserInterface(primaryStage);
    }
}
