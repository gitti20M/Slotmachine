package gui;


import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utils.Utils;

public class GamblerSymbol {
  public final Utils.gamblerIcon id;
  private final String path;

  public GamblerSymbol(Utils.gamblerIcon id, String path) {
    this.id = id;
    this.path = path;
  }

  public final HBox createGamblerHbox(Pos alignment) {
    HBox hBox = new HBox();
    hBox.getChildren().add(this.createGamblerImage());
    hBox.setAlignment(alignment);
    return hBox;
  }

  public final ImageView createGamblerImage() {
    ImageView imageView = new ImageView(this.path);
    imageView.setId(String.valueOf(id));
    return imageView;
  }
}
