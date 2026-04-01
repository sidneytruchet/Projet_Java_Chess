package Pieces;

import javafx.scene.image.Image;

public class Rook extends Piece {
    public Rook(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_tower_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_tower_black.png", 60.0, 60.0, true, true));
        }
    }
}
