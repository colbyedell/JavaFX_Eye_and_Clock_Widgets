package com.mycompany.colby_edell_lab_4_javafx_widgets;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class EyeSeeYou extends Pane implements EventHandler<MouseEvent> {
    private Circle leftEye;
    private Circle rightEye;
    private Circle leftPupil;
    private Circle rightPupil;
    private Rectangle frame;
    private Rectangle eyelids;
    private Pane Viewer;
    private boolean areEyesClosed;
    
    public EyeSeeYou() {
        Viewer = new Pane();
        Viewer.setPrefHeight(500);
        Viewer.setPrefWidth(500);
        
        frame = new Rectangle();
        frame.setWidth(500);
        frame.setHeight(500);
        frame.setFill(Color.WHITE);
        frame.setStroke(Color.BLACK);
        frame.setStrokeWidth(5);
        Viewer.getChildren().add(frame);
        
        leftEye = new Circle();
        leftEye.setRadius(100);
        leftEye.setLayoutX(125);
        leftEye.setLayoutY(225);
        leftEye.setFill(Color.WHITE);
        leftEye.setStroke(Color.BLACK);
        leftEye.setStrokeWidth(5);
        Viewer.getChildren().add(leftEye);
        
        rightEye = new Circle();
        rightEye.setRadius(100);
        rightEye.setLayoutX(375);
        rightEye.setLayoutY(225);
        rightEye.setFill(Color.WHITE);
        rightEye.setStroke(Color.BLACK);
        rightEye.setStrokeWidth(5);
        Viewer.getChildren().add(rightEye);
        
        leftPupil = new Circle();
        leftPupil.setRadius(25);
        leftPupil.setLayoutX(125);
        leftPupil.setLayoutY(225);
        Viewer.getChildren().add(leftPupil);
        
        rightPupil = new Circle();
        rightPupil.setRadius(25);
        rightPupil.setLayoutX(375);
        rightPupil.setLayoutY(225);
        Viewer.getChildren().add(rightPupil);
        
        eyelids = new Rectangle();
        eyelids.setHeight(125);
        eyelids.setWidth(495);
        eyelids.setLayoutX(2.5);
        eyelids.setLayoutY(100);
        eyelids.setFill(Color.WHITE);
        
        Viewer.setOnMouseMoved(this);
        Viewer.setOnMouseClicked(this);
        areEyesClosed = false;
        this.getChildren().add(Viewer);
        
    }

    public void handle(MouseEvent t) {
        if(t.getEventType() == MouseEvent.MOUSE_MOVED) {
            adjustLeftPupil(t.getX(), t.getY());
            adjustRightPupil(t.getX(), t.getY());
        }
        if(t.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if(!areEyesClosed) {
                Viewer.getChildren().clear();
                Viewer.getChildren().addAll(frame,leftEye,rightEye, eyelids);
                areEyesClosed = true;
            } else {
                Viewer.getChildren().clear();
                Viewer.getChildren().addAll(frame, leftEye, rightEye, leftPupil, rightPupil);
                areEyesClosed = false;
            }
        }
    }
    
    public void adjustLeftPupil(double x, double y) {
        // RECTANGULAR COORDINATE VERSION
        /*if(x > 175)
            leftPupil.setLayoutX(175);
        else if(x < 75)
            leftPupil.setLayoutX(75);
        else
            leftPupil.setLayoutX(x);
                
        if(y < 175)
            leftPupil.setLayoutY(175);
        else if(y > 275)
            leftPupil.setLayoutY(275);
        else
            leftPupil.setLayoutY(y);*/
        
        // CIRCULAR COORDINATE VERSION
        /*double mouseDistance = Math.sqrt(Math.pow((x - 125), 2) + Math.pow((y - 225), 2));
        if (100 >= mouseDistance + 25) {
            leftPupil.setLayoutX(x);
            leftPupil.setLayoutY(y);
        } else {
            leftPupil.setLayoutX(125 + ((x - 125) * (100 / (mouseDistance + 25))));
            leftPupil.setLayoutY(225 + ((y - 225) * (100 / (mouseDistance + 25))));
        }*/
        
        // BOUNDED CIRCLAR COORDINATE VERSION
        double maxDistance = leftEye.getRadius() - leftPupil.getRadius();
        double mouseDistance = Math.sqrt(Math.pow((x - leftEye.getLayoutX()), 2) + Math.pow((y - leftEye.getLayoutY()), 2));
        
        if(mouseDistance > maxDistance) {
            double angleToMouse = Math.atan2(y - leftEye.getLayoutY(), x - leftEye.getLayoutX());
            leftPupil.setLayoutX(leftEye.getLayoutX() + (maxDistance * Math.cos(angleToMouse)));
            leftPupil.setLayoutY(leftEye.getLayoutY() + (maxDistance * Math.sin(angleToMouse)));
        } else {
            leftPupil.setLayoutX(x);
            leftPupil.setLayoutY(y);
        }
    }
    
    public void adjustRightPupil(double x, double y) {
        // RECTANGULAR COORDINATE VERSION
        /*if(x > 425)
            rightPupil.setLayoutX(425);
        else if(x < 325)
            rightPupil.setLayoutX(325);
        else
            rightPupil.setLayoutX(x);*/
        
        /*if(y < 175)
            rightPupil.setLayoutY(175);
        else if(y > 275)
            rightPupil.setLayoutY(275);
        else
            rightPupil.setLayoutY(y);*/
        
        // CIRCULAR COORDINATE VERSION
        /*double mouseDistance = Math.sqrt(Math.pow((x - 375), 2) + Math.pow((y - 225), 2));
        if (100 >= mouseDistance + 25) {
            rightPupil.setLayoutX(x);
            rightPupil.setLayoutY(y);
        } else {
            rightPupil.setLayoutX(375 + ((x - 375) * (100 / (mouseDistance + 25))));
            rightPupil.setLayoutY(225 + ((y - 225) * (100 / (mouseDistance + 25))));
        }*/
        
        // BOUNDED CIRCULAR COORDINATE VERSION
        double maxDistance = rightEye.getRadius() - rightPupil.getRadius();
        double mouseDistance = Math.sqrt(Math.pow((x - rightEye.getLayoutX()), 2) + Math.pow((y - rightEye.getLayoutY()), 2));
        
        if(mouseDistance > maxDistance) {
            double angleToMouse = Math.atan2(y - rightEye.getLayoutY(), x - rightEye.getLayoutX());
            rightPupil.setLayoutX(rightEye.getLayoutX() + (maxDistance * Math.cos(angleToMouse)));
            rightPupil.setLayoutY(rightEye.getLayoutY() + (maxDistance * Math.sin(angleToMouse)));
        } else {
            rightPupil.setLayoutX(x);
            rightPupil.setLayoutY(y);
        }
    } 
}
