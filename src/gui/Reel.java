package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Reel {
  public Label currentImageLabel = new Label();
  static List<Integer> iconsAfterSpinningValues = new ArrayList<>();
  static List<icon> iconsAfterSpinning = new ArrayList<>();

  private final Random RANDOM = new Random();
  private List<Symbol> SYMBOLS = new ArrayList<>(Arrays.asList(
      new Symbol("image/bell.png", 9, icon.BELL),
      new Symbol("image/cherry.png", 7, icon.CHERRY),
      new Symbol("image/lemon.png", 6, icon.LEMON),
      new Symbol("image/plum.png", 4, icon.PLUM),
      new Symbol("image/redseven.png", 12, icon.REDSEVEN),
      new Symbol("image/watermelon.png", 3, icon.WATERMELON)
  ));

  enum icon {
    BELL,
    CHERRY,
    LEMON,
    PLUM,
    REDSEVEN,
    WATERMELON,
  }

  public Reel(int id) {
    this.currentImageLabel.setId(String.valueOf(id));
    this.setRandomImage();
  }

  public final void setRandomImage() {
    int index = this.RANDOM.nextInt(this.SYMBOLS.size());
    Symbol symbol = this.SYMBOLS.get(index);
    ImageView newImage = this.SYMBOLS.get(index).getView();
    newImage.setId(symbol.getID().toString());
    this.currentImageLabel.setGraphic(newImage);
  }

  public final void checkIcon(Label scoreLabel) {
    ImageView img = (ImageView) scoreLabel.getGraphic();
    for (Symbol symbol : this.SYMBOLS) {
      if (icon.valueOf(img.getId()) == icon.valueOf(symbol.getView().getId())) {
        iconsAfterSpinning.add(icon.valueOf(img.getId()));
        iconsAfterSpinningValues.add(symbol.getValue());
      }
    }
  }
}
