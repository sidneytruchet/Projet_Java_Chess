package Pieces;

import javafx.scene.image.Image;

public class Pawn extends Piece {

    public Pawn(boolean IsTeamWhite) {
        super(IsTeamWhite);

        if (IsTeamWhite) {
            this.setImage(new Image("White Pieces/spr_pawn_white.png", 60.0, 60.0, true, true));
        }
        else {
            this.setImage(new Image("Black Pieces/spr_pawn_black.png", 60.0, 60.0, true, true));
        }

//        this.getOnDragDetected();

//        this.getondr
    }

    public void Upgrade() {

    }
}
