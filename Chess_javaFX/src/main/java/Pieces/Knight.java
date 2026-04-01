package Pieces;

import javafx.scene.image.Image;

public class Knight extends Piece {
    public Knight(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_knight_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_knight_black.png", 60.0, 60.0, true, true));
        }
    }
}
