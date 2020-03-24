package gui;

import javafx.scene.image.ImageView;

public class Symbol {
  private final Reel.icon ID;
  private final int VALUE;
  private final ImageView IMAGE;

  public Symbol(String path, int value, Reel.icon ID) {
    this.VALUE = value;
    this.ID = ID;
    this.IMAGE = new ImageView(path);
    this.IMAGE.setId(ID.toString());
    this.IMAGE.setFitWidth(250);
    this.IMAGE.setFitHeight(250);
  }

  public final int getValue() {
    return this.VALUE;
  }

  public final ImageView getView() {
    return this.IMAGE;
  }

  public final Reel.icon getID() {
    return this.ID;
  }
}
