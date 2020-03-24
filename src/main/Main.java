package main;

import gui.MainGui;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    new MainGui(primaryStage);
  }


  public static void main(String[] args) {
    launch(args);
  }
}
