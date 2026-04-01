package Pieces;

import javafx.scene.image.Image;

public class Queen extends Piece {
    public Queen(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_queen_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_queen_black.png", 60.0, 60.0, true, true));
        }
    }
}
