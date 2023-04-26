package com.mycompany.colby_edell_lab_4_javafx_widgets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application implements EventHandler<ActionEvent> {
    private FlowPane mainPane;
    private Pane viewPane;
    private EyeSeeYou eyePane;
    private Clock clockPane;
    private Button eyeButton;
    private Button clockButton;
    
    @Override
    public void start(Stage stage) {
        mainPane = new FlowPane();
        mainPane.setPrefHeight(575);
        mainPane.setPrefWidth(500);
        mainPane.setHgap(20);
        mainPane.setVgap(25);
        
        eyePane = new EyeSeeYou();
        
        clockPane = new Clock();
        
        viewPane = new Pane();
        viewPane.setPrefHeight(500);
        viewPane.setPrefWidth(500);
        viewPane.getChildren().add(eyePane); // Default selection.
        mainPane.getChildren().add(viewPane);
        
        eyeButton = new Button();
        eyeButton.setText("Eye See You");
        eyeButton.setPrefWidth(240);
        eyeButton.setPrefHeight(50);
        eyeButton.setOnAction(this);
        mainPane.getChildren().add(eyeButton);
        
        clockButton = new Button();
        clockButton.setText("24-Hour Clock");
        clockButton.setPrefWidth(240);
        clockButton.setPrefHeight(50);
        clockButton.setOnAction(this);
        mainPane.getChildren().add(clockButton);
        

        Scene mainScene = new Scene(mainPane);
        
        stage.setScene(mainScene);
        stage.setTitle("Colby Edell: JavaFX Lab 4 - Widgets");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void handle(ActionEvent t) {
        if(t.getSource().toString().contains("Eye See You")) {
            viewPane.getChildren().clear();
            viewPane.getChildren().add(eyePane);
        }
        if(t.getSource().toString().contains("24-Hour Clock")) {
            viewPane.getChildren().clear();
            viewPane.getChildren().add(clockPane);
        }
    }

}