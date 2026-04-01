package Pieces;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public abstract class Piece extends ImageView implements Serializable {
    int Position_X = 0;
    int Position_Y = 0;
    boolean isTeamWhite = true;

    public Piece(boolean IsTeamWhite) {
        isTeamWhite = IsTeamWhite;
    }

    public boolean IsPieceWhite() {
        return isTeamWhite;
    }
}
