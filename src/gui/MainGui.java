package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MainGui extends Layout {
  public static HBox hBoxReels = null;
  public static boolean isApplicationRunning = false;

  private final Reel[] reels = {new Reel(1), new Reel(2), new Reel(3)};
  private final GameCounter GAME_COUNTER = new GameCounter(new HBox(), new Label(), this.mainStackPane);
  private final WonScore WON_SCORE = new WonScore(new HBox(), new Label());
  private final  Score SCORE = new Score(this.WON_SCORE);
  private final Controller CONTROLLER = new Controller();

  public MainGui(Stage stage) {
    this.configureLayout();
    this.setElement(this.SCORE.createScore("0", Pos.BOTTOM_CENTER));
    this.setGamblerIcon();
    this.createReels();
    this.setElement(GAME_COUNTER.createGameCounter(SCORE));
    this.createTestButton();
    this.configureScene(this.mainStackPane, stage);
    this.createMainMedia();
  }

  private void createMainMedia() {
    Utils.createBackgroundMedia("../sounds/MainSoundSlotMachine.mp3");
    Utils.mainMediaPlayer.play();
  }

  private void createReels() {
    hBoxReels = new HBox();
    hBoxReels.setSpacing(this.screenWidth / 15);
    hBoxReels.setAlignment(Pos.CENTER);
    for (Reel reel : this.reels) {
      StackPane imageContainer = new StackPane();
      imageContainer.getChildren().add(reel.currentImageLabel);
      imageContainer.setBackground(Utils.createBackgroundImage("image/slotBackground.png", null));
      imageContainer.setPrefSize(280, 280);
      hBoxReels.getChildren().add(imageContainer);
    }
    this.setElement(hBoxReels);
  }

  //Wegen Testzwecken
  private void createTestButton() {
    Button button = new Button();
    button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      if (!isApplicationRunning) {
        isApplicationRunning = true;
        if (this.WON_SCORE.isVisible) {
          this.WON_SCORE.isVisible = false;
          this.WON_SCORE.getWonScoreHbox().setVisible(this.WON_SCORE.isVisible);
        }
        Utils.mainMediaPlayer.pause();
        this.GAME_COUNTER.setValueToGameCounter(1, false);
        this.GAME_COUNTER.setValueToAmountLabel(String.valueOf(this.GAME_COUNTER.getValueFromGameCounter()));
        this.CONTROLLER.spinReel(this.reels, this.SCORE, this.GAME_COUNTER);
      }
    });
    button.setText("Ich bin ein Knopf");
    HBox boxi = new HBox();
    boxi.getChildren().add(button);
    boxi.setAlignment(Pos.BOTTOM_CENTER);
    this.setElement(boxi);
  }
  //

  private void setGamblerIcon() {
    int i = 0;
    Boolean[] lightningGambler = {true, true, true, false, false, true, true, false, false};
    Map<Insets, Pos> alignMentGamblerIcon = new HashMap<Insets, Pos>() {{
      put(new Insets(0, 0, 200, 200), Pos.BOTTOM_LEFT);
      put(new Insets(0, 0, 0, 200), Pos.CENTER_LEFT);
      put(new Insets(200, 0, 0, 200), Pos.TOP_LEFT);
      put(new Insets(100, 700, 0, 0), Pos.BASELINE_CENTER);
      put(new Insets(50, 0, 0, 0), Pos.BASELINE_CENTER);
      put(new Insets(100, 0, 0, 700), Pos.BASELINE_CENTER);
      put(new Insets(200, 200, 0, 0), Pos.TOP_RIGHT);
      put(new Insets(0, 200, 0, 0), Pos.CENTER_RIGHT);
      put(new Insets(0, 200, 200, 0), Pos.BOTTOM_RIGHT);
    }};

    for (Insets padding : alignMentGamblerIcon.keySet()) {
      int index = new Random().nextInt(Utils.gamblerSymbols.size());
      HBox hBox = Utils.gamblerSymbols.get(index).createGamblerHbox(alignMentGamblerIcon.get(padding));
      if (lightningGambler[i]) {
        hBox.setEffect(new Lighting());
      }
      hBox.setPadding(padding);
      Utils.gamblerElements.add(hBox);
      this.setElement(hBox);
      i++;
    }
    Utils.changeLightingGambler();
  }

  private void configureScene(StackPane layout, Stage stage) {
    stage.setTitle("Slotmachine");
    stage.getIcons().add(new Image("image/slotmachineIcon.png"));
    stage.setFullScreen(true);
    stage.setScene(new Scene(layout, 1000, 600));
    stage.show();
  }
}
