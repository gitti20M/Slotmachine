package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameCounter extends GameEnded {
  public int gameCounter = 5;
  private HBox counterBox;
  private Label counterAmountLabel;
  private Score scoreInstance;
  private StackPane layout;
  private Reel[] reels;

  public GameCounter(HBox counterBox, Label counterAmountLabel, Reel[] reels, StackPane pane) {
    this.counterBox = counterBox;
    this.counterAmountLabel = counterAmountLabel;
    this.reels = reels;
    this.layout = pane;
  }

  public final HBox createGameCounter(Score scoreInstance) {
    this.scoreInstance = scoreInstance;
    this.counterAmountLabel.setText(String.valueOf(gameCounter));
    this.counterAmountLabel.setFont(Font.font("impact", 45));
    this.counterAmountLabel.setTextFill(Color.RED);
    this.counterBox.setPadding(new Insets(10, 0, 0, 25));
    this.counterBox.setAlignment(Pos.TOP_LEFT);
    this.counterBox.getChildren().add(this.counterAmountLabel);
    return this.counterBox;
  }

  public final void setValueToAmountLabel(String value) {
    this.counterAmountLabel.setText(value);
  }

  public final void setValueToGameCounter(int value, boolean plusOrMinus) {
    if (value == 0 && !plusOrMinus) {
      createGameEnded(scoreInstance, layout, this.reels);
    } else {
      if (gameCounter >= 0) {
        if (plusOrMinus) {
          this.gameCounter += value;
        } else {
          this.gameCounter -= value;
        }
      }
    }
  }

  public final int getValueFromGameCounter() {
    return this.gameCounter;
  }
}
