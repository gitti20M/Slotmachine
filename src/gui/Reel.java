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
  private List<Symbol> RANDOM_SYMBOLS = new ArrayList<>();
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
    this.createRandomReels();
    this.setRandomImage();
  }

  public final void clearRandomReels() {
    this.RANDOM_SYMBOLS.clear();
  }

  public final void createRandomReels() {
    for (Symbol symbol : this.SYMBOLS) {
      switch (symbol.getID()) {
        case BELL:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 0);
          break;
        case CHERRY:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 1);
          break;
        case LEMON:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 2);
          break;
        case PLUM:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 3);
          break;
        case REDSEVEN:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 4);
          break;
        case WATERMELON:
          this.setRandomSymbolsToList(this.calcRandomNumber(), 5);
          break;
      }
    }
  }

  private int calcRandomNumber() {return this.RANDOM.nextInt(10 - 1 + 1) + 1;}

  private void setRandomSymbolsToList(int amount, int index) {
    while (amount > 0) {
      this.RANDOM_SYMBOLS.add(this.SYMBOLS.get(index));
      amount--;
    }
  }

  public final void setRandomImage() {
    int index = this.RANDOM.nextInt(this.RANDOM_SYMBOLS.size());
    Symbol symbol = this.RANDOM_SYMBOLS.get(index);
    ImageView newImage = this.RANDOM_SYMBOLS.get(index).getView();
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
