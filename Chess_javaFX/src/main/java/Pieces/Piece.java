package Pieces;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    int Position_X = 0;
    int Position_Y = 0;
    boolean isTeamWhite = true;


    public Piece (boolean IsTeamWhite) {
        isTeamWhite = IsTeamWhite;


    }

    public boolean IsPieceWhite() {
        return isTeamWhite;
    }
}
