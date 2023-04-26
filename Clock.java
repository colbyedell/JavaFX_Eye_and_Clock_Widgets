package com.mycompany.colby_edell_lab_4_javafx_widgets;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.animation.PathTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Clock extends Pane /*implements EventHandler<ActionEvent>*/ {
    private Pane Viewer;
    private Pane clockHands;
    private Rectangle frame;
    private Circle clockOutline, clockCenter;
    private Line hourHand, minuteHand, secondHand;
    private Line[] hourLines;
    private Text[] hourLabels;
    private Timeline clockAnimation;
    private Circle animationPath;
    private Calendar currentTime;
    private int hour, minute, second;

    public Clock() {
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
        
        clockOutline = new Circle();
        clockOutline.setRadius(200);
        clockOutline.setLayoutX(250);
        clockOutline.setLayoutY(250);
        clockOutline.setFill(Color.WHITE);
        clockOutline.setStroke(Color.BLACK);
        clockOutline.setStrokeWidth(5);
        Viewer.getChildren().add(clockOutline);
        
        hourLines = new Line[24];
        hourLabels = new Text[24];
        
        for (int i = 0; i < 24; i++) {
            hourLines[i] = new Line(250, 250, 250 + (175 * Math.sin((i % 24) * (2 * Math.PI / 24))), 250 - (175 * Math.cos((i % 24) * (2 * Math.PI / 24))));
            hourLines[i].setStroke(Color.WHITE);
            hourLines[i].setStrokeWidth(5);
            
            hourLabels[i] = new Text(235 + (225 * Math.sin((i % 24) * (2 * Math.PI / 24))), 260 - (225 * Math.cos((i % 24) * (2 * Math.PI / 24))), String.valueOf(i));
            hourLabels[i].setFont(new Font("Calibri", 30));
            
            if(i == 0)
                hourLabels[i].setText("24");
            
            Viewer.getChildren().add(hourLabels[i]);
            
            Viewer.getChildren().add(new Line(250, 250, 250 + (200 * Math.sin((i % 24) * (2 * Math.PI / 24))), 250 - (200 * Math.cos((i % 24) * (2 * Math.PI / 24))))); // Hour notches.
            Viewer.getChildren().add(hourLines[i]); // White covers over hour notches.
        }
        
        // TIMELINE VERSION
        // Set the clock animation to a timeline that runs with each frame lasting 1 second, updated with the event handler in this class.
        /*clockHands = new Pane();
        clockHands.setPrefHeight(500);
        clockHands.setPrefWidth(500);
        
        Viewer.getChildren().add(clockHands);
        clockAnimation = new Timeline(new KeyFrame(Duration.millis(1000), this));
        clockAnimation.setCycleCount(Timeline.INDEFINITE); // Set the animation to run indefinitely.
        clockAnimation.play(); // Start the clock animation.*/
        
  
        //PATHTRANSITION VERSION
        getCurrentTime();
        
        animationPath = new Circle(250,250,75);
        animationPath.setFill(Color.TRANSPARENT);
        //animationPath.setStroke(Color.BLACK); // Uncomment to view the path used for clock hand animation.
        Viewer.getChildren().add(animationPath);
        
        Rectangle secondHandRectangle = new Rectangle(0,0,1,160);
        secondHandRectangle.setFill(Color.ORANGE);
        Viewer.getChildren().add(secondHandRectangle);
        
        Rectangle minuteHandRectangle = new Rectangle(0,0,3,130);
        minuteHandRectangle.setFill(Color.PURPLE);
        Viewer.getChildren().add(minuteHandRectangle);
        
        Rectangle hourHandRectangle = new Rectangle(0,0,5,100);
        hourHandRectangle.setFill(Color.PINK);
        Viewer.getChildren().add(hourHandRectangle);
        
        clockCenter = new Circle(250, 250, 25);
        clockCenter.setStroke(Color.GRAY);
        clockCenter.setStrokeWidth(2);
        Viewer.getChildren().add(clockCenter);
       
        PathTransition secondHandAnimation = new PathTransition();
        secondHandAnimation.setPath(animationPath);
        secondHandAnimation.setNode(secondHandRectangle);
        secondHandAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        secondHandAnimation.setCycleCount(Timeline.INDEFINITE);
        secondHandAnimation.setDuration(Duration.seconds(60));
        secondHandAnimation.setInterpolator(Interpolator.LINEAR); // Without this line, the animation slows and speeds up during playback.
        animationPath.setRotate(-90 + (6 * second));
        secondHandAnimation.play();
        
        PathTransition minuteHandAnimation = new PathTransition();
        minuteHandAnimation.setPath(animationPath);
        minuteHandAnimation.setNode(minuteHandRectangle);
        minuteHandAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        minuteHandAnimation.setCycleCount(Timeline.INDEFINITE);
        minuteHandAnimation.setDuration(Duration.minutes(60));
        minuteHandAnimation.setInterpolator(Interpolator.LINEAR);
        animationPath.setRotate(-90 + (6 * minute) + (0.25 * second));
        minuteHandAnimation.play();
        
        PathTransition hourHandAnimation = new PathTransition();
        hourHandAnimation.setPath(animationPath);
        hourHandAnimation.setNode(hourHandRectangle);
        hourHandAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        hourHandAnimation.setCycleCount(Timeline.INDEFINITE);
        hourHandAnimation.setDuration(Duration.hours(24));
        hourHandAnimation.setInterpolator(Interpolator.LINEAR);
        animationPath.setRotate(-90 + (15 * hour) + (0.25 * minute));
        hourHandAnimation.play();
        
        this.getChildren().add(Viewer);    
    }
    
    public void getCurrentTime() {
        currentTime = new GregorianCalendar();
        this.hour = currentTime.get(Calendar.HOUR_OF_DAY);
        this.minute = currentTime.get(Calendar.MINUTE);
        this.second = currentTime.get(Calendar.SECOND);
    }

    /*public void drawClockHands() {
        clockHands.getChildren().clear();
        hourHand = new Line(250, 250, 250 + (100 * Math.sin((hour % 24 + minute / 60.0) * (2 * Math.PI / 24))), 250 - (100 * Math.cos((hour % 24 + minute / 60.0) * (2 * Math.PI / 24))));
        minuteHand = new Line(250, 250, 250 + (130 * Math.sin(minute * (2 * Math.PI / 60))), 250 - (130 * Math.cos(minute * (2 * Math.PI / 60))));
        secondHand = new Line(250, 250, 250 + (160 * Math.sin(second * (2 * Math.PI / 60))), 250 - (160 * Math.cos(second * (2 * Math.PI / 60))));
        hourHand.setStroke(Color.RED);
        minuteHand.setStroke(Color.GREEN);
        secondHand.setStroke(Color.BLUE);
        clockHands.getChildren().addAll(hourHand,minuteHand,secondHand);
    }
    
    @Override
    public void handle(ActionEvent t) { // Every second, get the updated time and draw the clock hands accordingly.
        getCurrentTime();
        drawClockHands();
    }*/
}
