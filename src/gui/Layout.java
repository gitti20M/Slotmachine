package gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import utils.Utils;

public class Layout {
  StackPane mainStackPane = new StackPane();
  double screenWidth;

  void setElement(Node element) {
    this.mainStackPane.getChildren().add(element);
  }

  void configureLayout() {
    Rectangle2D screen = Screen.getPrimary().getBounds();
    this.screenWidth = screen.getWidth();
    this.mainStackPane.setBackground(Utils.createBackgroundImage("image/background.png", null));
    this.mainStackPane.setPrefSize(screen.getWidth(), screen.getHeight());
  }
}
