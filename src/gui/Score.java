package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Score extends ScoreEffect {
  public Label scoreLabel = new Label();
  public HBox hBox = new HBox();

  private int score = 0;
  private WonScore wonScore;

  public Score(WonScore wonScore) {
    this.wonScore = wonScore;
  }

  public final HBox createScore(String labelText, Pos position) {
    this.scoreLabel.setText(labelText);
    this.scoreLabel.setFont(Font.font("impact", 60));
    this.scoreLabel.setTextFill(Color.YELLOW);
    this.hBox.setAlignment(position);
    this.hBox.setSpacing(10);
    this.hBox.setPadding(new Insets(0, 0, 150, 0));
    this.hBox.getChildren().add(this.scoreLabel);
    return hBox;
  }

  public final void setScore(int value) {
    score = value;
  }

  public final int prepareCalc(GameCounter gameCounter) {
    int bellAmount = 0, cherryAmout = 0, lemonAmount = 0, plumAmount = 0, redsevenAmount = 0, watermelonAmount = 0;
    int i = 0;
    Map<String, Integer> amountIcon = new HashMap<>();
    Map<String, Integer> valueIcon = new HashMap<>();

    for (Reel.icon icon : Reel.iconsAfterSpinning) {
      switch (icon) {
        case BELL:
          bellAmount += 1;
          amountIcon.put("bell", bellAmount);
          valueIcon.put("bell", Reel.iconsAfterSpinningValues.get(i));
          break;
        case CHERRY:
          cherryAmout += 1;
          amountIcon.put("cherry", cherryAmout);
          valueIcon.put("cherry", Reel.iconsAfterSpinningValues.get(i));
          break;
        case LEMON:
          lemonAmount += 1;
          amountIcon.put("lemon", lemonAmount);
          valueIcon.put("lemon", Reel.iconsAfterSpinningValues.get(i));
          break;
        case PLUM:
          plumAmount += 1;
          amountIcon.put("plum", plumAmount);
          valueIcon.put("plum", Reel.iconsAfterSpinningValues.get(i));
          break;
        case REDSEVEN:
          redsevenAmount += 1;
          amountIcon.put("redseven", redsevenAmount);
          valueIcon.put("redseven", Reel.iconsAfterSpinningValues.get(i));
          break;
        case WATERMELON:
          watermelonAmount += 1;
          amountIcon.put("watermelon", watermelonAmount);
          valueIcon.put("watermelon", Reel.iconsAfterSpinningValues.get(i));
          break;
      }
      i++;
    }
    i = 0;
    for (String key : valueIcon.keySet()) {
      switch (amountIcon.get(key)) {
        case 1:
          i++;
          if (i == 3) {
            if (gameCounter.gameCounter == 0) {
              gameCounter.setGameCounter(gameCounter);
              gameCounter.setValueToGameCounter(0, false);
              this.scoreLabel.setFont(Font.font("impact", 75));
              MainGui.isApplicationRunning = true;
            } else {
              Utils.mainMediaPlayer.play();
              MainGui.isApplicationRunning = false;
            }
          }
          break;
        case 2:
          this.setValueToWonScore((valueIcon.get(key)) * 6);
          gameCounter.setValueToGameCounter(1, true);
          this.setValueToGameCounterLabel(gameCounter);
          setTwoIconEffect(this.hBox);
          break;
        case 3:
          if (key.equals("redseven")) {
            this.setValueToWonScore((valueIcon.get(key)) * 1000);
            gameCounter.setValueToGameCounter(4, true);
            this.setValueToGameCounterLabel(gameCounter);
            setJackpotEffect(this.hBox);
          } else {
            this.setValueToWonScore((valueIcon.get(key)) * 100);
            gameCounter.setValueToGameCounter(2, true);
            this.setValueToGameCounterLabel(gameCounter);
            setThreeIconEffect(this.hBox);
          }
          break;
      }
    }
    Reel.iconsAfterSpinning.clear();
    Reel.iconsAfterSpinningValues.clear();
    return this.score;
  }

  private void setValueToGameCounterLabel(GameCounter gameCounter) {
    gameCounter.setValueToAmountLabel(String.valueOf(gameCounter.getValueFromGameCounter()));
  }

  private void setValueToWonScore(int won) {
    this.score += won;
    Label label = wonScore.getWonScoreLabel();
    label.setText("+" + " " + won);
    wonScore.isVisible = true;
    wonScore.getWonScoreHbox().setVisible(true);
  }
}
