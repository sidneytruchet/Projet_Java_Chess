package Pieces;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class Bishop extends Piece {
    public Bishop(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_bishop_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_bishop_black.png", 60.0, 60.0, true, true));
        }
    }
}
