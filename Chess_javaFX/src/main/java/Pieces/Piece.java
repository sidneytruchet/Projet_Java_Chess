package Pieces;

public abstract class Piece {

    int Position_X = 0;
    int Position_Y = 0;
    boolean isTeamWhite = true;

    public Piece (boolean IsTeamWhite) {
        isTeamWhite = IsTeamWhite;


    }
}
