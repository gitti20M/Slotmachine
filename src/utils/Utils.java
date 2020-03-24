package utils;

import com.sun.istack.internal.Nullable;
import gui.GamblerSymbol;
import javafx.application.Platform;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Utils {
  public static MediaPlayer mainMediaPlayer = null;
  public static List<HBox> gamblerElements = new ArrayList<>();
  public static List<GamblerSymbol> gamblerSymbols = new ArrayList<>(Arrays.asList(
      new GamblerSymbol(gamblerIcon.casinoChips, "image/casinoChips.png"),
      new GamblerSymbol(gamblerIcon.cubeOne, "image/cubeOne.png"),
      new GamblerSymbol(gamblerIcon.cubeTwo, "image/cubeTwo.png"),
      new GamblerSymbol(gamblerIcon.cubeThree, "image/cubeThree.png"),
      new GamblerSymbol(gamblerIcon.cubeFour, "image/cubeFour.png"),
      new GamblerSymbol(gamblerIcon.cubeFive, "image/cubeFive.png"),
      new GamblerSymbol(gamblerIcon.cubeSix, "image/cubeSix.png"),
      new GamblerSymbol(gamblerIcon.symbolJackpot, "image/Slotmachineicon.png"),
      new GamblerSymbol(gamblerIcon.symbolPike, "image/symbolPike.png"),
      new GamblerSymbol(gamblerIcon.symbolSpade, "image/symbolSpade.png")

  ));

  public enum gamblerIcon {
    casinoChips,
    cubeOne,
    cubeTwo,
    cubeThree,
    cubeFour,
    cubeFive,
    cubeSix,
    symbolPike,
    symbolSpade,
    symbolJackpot
  }

  public static Background createBackgroundImage(String url, @Nullable BackgroundSize size) {
    return new Background(
        new BackgroundImage(
            new Image(url),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            size
        )
    );
  }

  public static void changeLightingGambler() {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          for (HBox hBox : gamblerElements) {
            if (hBox.getEffect() != null) {
              hBox.setEffect(null);
            } else {
              hBox.setEffect(new Lighting());
            }
          }
        });
      }
    };
    new Timer().schedule(timerTask, 200, 2000);
  }

  public static void createBackgroundMedia(String resource) {
    URL mediaResource = Utils.class.getResource(resource);
    Media media = new Media(mediaResource.toString());
    mainMediaPlayer = new MediaPlayer(media);
    mainMediaPlayer.setOnEndOfMedia(() -> {
      mainMediaPlayer.seek(Duration.minutes(0));
      mainMediaPlayer.play();
    });
  }
}
