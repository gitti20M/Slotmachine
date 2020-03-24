package controller;

import gui.GameCounter;
import gui.MainGui;
import gui.Reel;
import gui.Score;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utils.Utils;
import slotmachine.hardwareInteface.IBanditInputhandler;

import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements IBanditInputhandler {
  private Timer timer = null;
  private TimerTask timerTask = null;
  private MediaPlayer mediaPlayer = null;
  private int counter = 0;

  public final void spinReel(Reel[] reels, Score scoreInstance, GameCounter gameCounter) {
    this.checkIfEffects(scoreInstance);
    this.changeGamblerIcon();
    URL resource = getClass().getResource("../sounds/SlotMachineSound.mp3");
    Media media = new Media(resource.toString());
    this.mediaPlayer = new MediaPlayer(media);
    this.mediaPlayer.play();

    timer = new Timer();
    timerTask = createNewTimerTask(reels, scoreInstance, gameCounter);
    timer.schedule(timerTask, 1600, 100);
  }

  private void changeGamblerIcon() {
    for (Node node : Utils.gamblerElements) {
      HBox hBox = (HBox) node;
      hBox.getChildren().clear();
      hBox.getChildren().add(Utils.gamblerSymbols.get(new Random().nextInt(Utils.gamblerSymbols.size())).createGamblerImage());
    }
  }

  private void checkIfEffects(Score scoreInstance) {
    if (scoreInstance.isTransitionRunning) {
      scoreInstance.isTransitionRunning = false;
      scoreInstance.fadeTransition.playFrom(Duration.millis(0));
      scoreInstance.fadeTransition.stop();
      scoreInstance.removeEffect(scoreInstance.hBox);
    }
    if (scoreInstance.isRotateTransitionRunning) {
      for (RotateTransition transition : scoreInstance.rotateTransitions) {
        transition.playFrom(Duration.millis(0));
        transition.stop();
      }
      scoreInstance.isRotateTransitionRunning = false;
    }
    if (scoreInstance.isGlowing) {
      scoreInstance.removeGlowingLabel(MainGui.hBoxReels);
      scoreInstance.isGlowing = false;
    }
  }

  private TimerTask createNewTimerTask(Reel[] reels, Score scoreInstance, GameCounter gameCounter) {
    return new TimerTask() {
      @Override
      public void run() {
        counter++;
        if (counter < 62) {
          timerContent(reels, scoreInstance, gameCounter);
        }
      }
    };
  }

  private void timerContent(Reel[] reels, Score scoreInstance, GameCounter gameCounter) {
    Platform.runLater(() -> {
      for (Reel reel : reels) {
        reel.setRandomImage();
      }
      if (counter > 60) {
        this.timerTask.cancel();
        this.mediaPlayer.stop();
        this.timer.purge();
        for (Reel reel : reels) {
          reel.checkIcon(reel.currentImageLabel);
        }
        scoreInstance.scoreLabel.setText(String.valueOf(scoreInstance.prepareCalc(gameCounter)));
        counter = 0;
      }
    });
  }

  @Override
  public void resetRotoryEncoderPosition() {

  }

  @Override
  public boolean getButtonIsPushed() {
    return false;
  }

  @Override
  public int getRotoryEncoderPosition() {
    return 0;
  }

  @Override
  public void setRotoryEncoderPosition(int value) {
  }

  @Override
  public boolean getMoneyPuscherIsActive() {
    return false;
  }

  @Override
  public void pushCoins(int count) {
  }

  @Override
  public void setLightActive(int number, boolean state) {
  }

  @Override
  public boolean getLightState(int number) {
    return false;
  }
}
