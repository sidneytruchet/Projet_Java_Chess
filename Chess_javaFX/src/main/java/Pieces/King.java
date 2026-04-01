package Pieces;

import javafx.scene.image.Image;

public class King extends Piece {
    public King(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_king_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_king_black.png", 60.0, 60.0, true, true));
        }
    }
}
