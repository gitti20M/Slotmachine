package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import utils.Utils;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class GameEnded {
  private GameCounter gameCounter;
  private MediaPlayer mediaPlayer = null;
  private Reel[] reels = null;

  public final void setGameCounter(GameCounter gameCounter) {
    this.gameCounter = gameCounter;
  }

  private Lighting createLightning() {
    Light.Spot light = new Light.Spot();
    light.setPointsAtX(0);
    light.setPointsAtY(0);
    light.setPointsAtZ(-50);
    light.setSpecularExponent(5);
    return new Lighting();
  }

  private void breakForMinute(HBox hBox, Score scoreInstance, StackPane layout) {
    Timer timer = new Timer();
    TimerTask timerTask = this.createTimeLine(hBox, timer, scoreInstance, layout);
    timer.schedule(timerTask, 30000, 100);
  }

  private void preparePlayLoseMedia() {
    URL resource = getClass().getResource("../sounds/LoseSound.mp3");
    Media media = new Media(String.valueOf(resource));
    this.mediaPlayer = new MediaPlayer(media);
    this.mediaPlayer.setOnEndOfMedia(() -> Utils.mainMediaPlayer.play());
  }

  private TimerTask createTimeLine(HBox hBox, Timer timer, Score scoreInstance, StackPane layout) {
    return new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          clearRandomReels();
          MainGui.hBoxReels.setVisible(true);
          layout.getChildren().remove(hBox);
          scoreInstance.scoreLabel.setText("0");
          scoreInstance.scoreLabel.setFont(Font.font("impact", 55));
          scoreInstance.setScore(0);
          gameCounter.gameCounter = 5;
          gameCounter.setValueToAmountLabel(String.valueOf(gameCounter.gameCounter));
          timer.cancel();
          MainGui.isApplicationRunning = false;
        });
      }
    };
  }

  private void clearRandomReels() {
    System.out.println(reels.length);
    for (Reel reel : reels) {
      reel.clearRandomReels();
      reel.createRandomReels();
    }
  }

  void createGameEnded(Score scoreInstance, StackPane layout, Reel[] reels) {
    this.reels = reels;
    this.preparePlayLoseMedia();
    MainGui.hBoxReels.setVisible(false);
    HBox hBox = new HBox();
    Label label = new Label();
    label.setTextAlignment(TextAlignment.CENTER);
    label.setText("The Game is finished\nYou win:");
    label.setTextFill(Color.RED);
    label.setFont(Font.font("impact", 70));
    label.setEffect(createLightning());
    hBox.getChildren().add(label);
    hBox.setAlignment(Pos.CENTER);
    layout.getChildren().add(hBox);
    this.mediaPlayer.play();
    this.breakForMinute(hBox, scoreInstance, layout);
  }
}
