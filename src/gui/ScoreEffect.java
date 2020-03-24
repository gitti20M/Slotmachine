package gui;

import com.sun.istack.internal.NotNull;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreEffect {
  public FadeTransition fadeTransition;
  public ArrayList<RotateTransition> rotateTransitions;
  public boolean isTransitionRunning = false, isRotateTransitionRunning = false, isGlowing = false;

  private MediaPlayer mediaPlayer = null;

  private void createFadeTransition(HBox box, int sec) {
    this.fadeTransition = new FadeTransition(Duration.millis(sec), box);
    this.fadeTransition.setFromValue(1.0);
    this.fadeTransition.setToValue(0.0);
    this.fadeTransition.setCycleCount(Animation.INDEFINITE);
  }

  private void setFont(@NotNull HBox box, String fontFamily, int fontSize, Effect effect) {
    for (Node node : box.getChildren()) {
      Label label = (Label) node;
      label.setFont(Font.font(fontFamily, fontSize));
      label.setEffect(effect);
    }
  }

  @NotNull
  private RotateTransition createRotateTransition(Node node) {
    RotateTransition rotateTransition = new RotateTransition();
    rotateTransition.setAxis(Rotate.Z_AXIS);
    rotateTransition.setByAngle(360);
    rotateTransition.setCycleCount(500);
    rotateTransition.setDuration(Duration.millis(1000));
    rotateTransition.setAutoReverse(true);
    rotateTransition.setNode(node);
    return rotateTransition;
  }

  @NotNull
  private Reflection createReflection() {
    Reflection reflection = new Reflection();
    reflection.setBottomOpacity(0.2);
    reflection.setTopOffset(10);
    reflection.setFraction(12);
    reflection.setTopOffset(10);
    reflection.setTopOpacity(0.2);
    return reflection;
  }

  @NotNull
  private Glow createGlowing(int level) {
    Glow glow = new Glow();
    glow.setLevel(level);
    return glow;
  }

  private void checkAmountAndSetGlowing(int countIcon, Label label) {
    if (countIcon > 1) {
      label.setEffect(this.createGlowing(6));
    }
  }

  private void createSound(String resource) {
    URL resourceMedia = getClass().getResource(resource);
    Media media = new Media(resourceMedia.toString());
    this.mediaPlayer = new MediaPlayer(media);
    this.mediaPlayer.setOnEndOfMedia(() -> {
      Utils.mainMediaPlayer.play();
      MainGui.isApplicationRunning = false;
    });
  }

  public final void removeEffect(@NotNull HBox box) {
    box.getChildren().remove(fadeTransition);
    for (Node node : box.getChildren()) {
      Label label = (Label) node;
      label.setFont(Font.font("impact", 55));
      label.setEffect(null);
    }
  }

  public final void removeGlowingLabel(HBox hBox) {
    for (Node node : hBox.getChildren()) {
      StackPane pane = (StackPane) node;
      for (Node nodeLabel : pane.getChildren()) {
        Label label = (Label) nodeLabel;
        label.setEffect(null);
      }
    }
  }

  private void setGamblerIcon(Utils.gamblerIcon icon) {
    for (HBox hBox : Utils.gamblerElements) {
      hBox.getChildren().clear();
      for (GamblerSymbol gamblerSymbol : Utils.gamblerSymbols) {
        if (gamblerSymbol.id == icon) {
          hBox.getChildren().add(gamblerSymbol.createGamblerImage());
        }
      }
    }
  }

  void setJackpotEffect(HBox box) {
    int count = 0;
    this.rotateTransitions = new ArrayList<>();
    this.createFadeTransition(box, 2000);
    this.setFont(box, "broadway", 80, null);
    this.setGamblerIcon(Utils.gamblerIcon.symbolJackpot);

    for (Node node : MainGui.hBoxReels.getChildren()) {
      StackPane pane = (StackPane) node;
      for (Node nodeLabel : pane.getChildren()) {
        Label label = (Label) nodeLabel;
        label.setEffect(this.createGlowing(4));
      }
      this.rotateTransitions.add(this.createRotateTransition(pane));
      this.rotateTransitions.get(count).play();
      count++;
    }

    this.createSound("../sounds/JackPotSound.mp3");
    this.mediaPlayer.play();
    this.fadeTransition.play();
    this.isRotateTransitionRunning = true;
    this.isTransitionRunning = true;
    this.isGlowing = true;
  }

  void setThreeIconEffect(HBox box) {
    this.createFadeTransition(MainGui.hBoxReels, 6000);
    this.setFont(box, "impact", 70, new Lighting());
    this.setGamblerIcon(Utils.gamblerIcon.casinoChips);
    this.createSound("../sounds/ThreeFruitSound.mp3");
    this.mediaPlayer.play();
    this.fadeTransition.play();
    this.isTransitionRunning = true;
  }

  void setTwoIconEffect(HBox box) {
    this.createFadeTransition(box, 6000);
    List<Label> iconLabel = new ArrayList<>();
    List<String> icon = new ArrayList<>();
    this.setFont(box, "impact", 65, this.createReflection());
    this.setGamblerIcon(Utils.gamblerIcon.symbolSpade);

    for (Node node : MainGui.hBoxReels.getChildren()) {
      StackPane pane = (StackPane) node;
      for (Node nodeLabel : pane.getChildren()) {
        Label label = (Label) nodeLabel;
        iconLabel.add(label);
        icon.add(label.getGraphic().getId());
      }
    }

    int countCherry = Collections.frequency(icon, "CHERRY");
    int countLemon = Collections.frequency(icon, "LEMON");
    int countPlum = Collections.frequency(icon, "PLUM");
    int countWatermelon = Collections.frequency(icon, "WATERMELON");
    int countBell = Collections.frequency(icon, "BELL");
    int countRedSeven = Collections.frequency(icon, "REDSEVEN");

    for (Label label : iconLabel) {
      switch (label.getGraphic().getId()) {
        case "CHERRY":
          this.checkAmountAndSetGlowing(countCherry, label);
          break;
        case "LEMON":
          this.checkAmountAndSetGlowing(countLemon, label);
          break;
        case "PLUM":
          this.checkAmountAndSetGlowing(countPlum, label);
          break;
        case "WATERMELON":
          this.checkAmountAndSetGlowing(countWatermelon, label);
          break;
        case "BELL":
          this.checkAmountAndSetGlowing(countBell, label);
          break;
        case "REDSEVEN":
          this.checkAmountAndSetGlowing(countRedSeven, label);
          break;
      }
    }
    this.createSound("../sounds/TwoFruitSound.mp3");
    this.mediaPlayer.play();
    this.fadeTransition.play();
    this.isTransitionRunning = true;
    this.isGlowing = true;
  }
}
