package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WonScore {
  public boolean isVisible = false;

  private HBox hBoxWonSocre;
  private Label labelWonScore;

  public WonScore(HBox hBoxWonSocre, Label labelWonScore) {
    this.hBoxWonSocre = hBoxWonSocre;
    this.labelWonScore = labelWonScore;
    this.createWonScore();
  }

  private void createWonScore() {
    this.labelWonScore.setFont(Font.font("impact", 55));
    this.labelWonScore.setTextFill(Color.YELLOW);
    this.hBoxWonSocre.getChildren().add(this.labelWonScore);
    this.hBoxWonSocre.setAlignment(Pos.BOTTOM_CENTER);
    this.hBoxWonSocre.setPadding(new Insets(0, 0, 250, 0));
    this.hBoxWonSocre.setVisible(false);
  }

  public final Label getWonScoreLabel() {
    return this.labelWonScore;
  }

  public final HBox getWonScoreHbox() {
    return this.hBoxWonSocre;
  }
}
